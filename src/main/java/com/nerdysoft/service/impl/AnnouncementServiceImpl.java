package com.nerdysoft.service.impl;

import com.nerdysoft.constant.ErrorMessage;
import com.nerdysoft.dto.AnnouncementDto;
import com.nerdysoft.dto.SimilarAnnouncementDto;
import com.nerdysoft.exeption.NotFoundException;
import com.nerdysoft.mapper.announcement.AnnouncementMapper;
import com.nerdysoft.mapper.announcement.SimilarAnnouncementMapper;
import com.nerdysoft.model.Announcement;
import com.nerdysoft.repository.AnnouncementRepository;
import com.nerdysoft.repository.UserRepository;
import com.nerdysoft.service.AnnouncementService;
import com.nerdysoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SimilarAnnouncementMapper similarAnnouncementMapper;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, AnnouncementMapper announcementMapper, UserRepository userRepository, UserService userService, SimilarAnnouncementMapper similarAnnouncementMapper) {
        this.announcementRepository = announcementRepository;
        this.announcementMapper = announcementMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.similarAnnouncementMapper = similarAnnouncementMapper;
    }

    @Override
    public List<AnnouncementDto> getAllAnnouncement() {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream().map(announcementMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public AnnouncementDto getAnnouncementById(Long id) {
        return announcementMapper.convertToDto(announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessage.ANNOUNCEMENT_WITH_ID_NOT_FOUND, id))));
    }

    @Override
    public Announcement createAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = announcementMapper.convertToModel(announcementDto);
        announcement.setCreationDate(new Timestamp(new Date().getTime()));
        announcement.setUser(userRepository.findByEmail(userService.getEmailFromAuthentication()).get());
        return announcementRepository.save(announcement);
    }

    @Override
    public void updateAnnouncement(AnnouncementDto announcementDto) {
        announcementDto.getId();
        Announcement announcement = announcementRepository.findById(announcementDto.getId()).orElseThrow();
        String emailFromDto = announcement.getUser().getEmail();
        String emailFromAuthentication = userService.getEmailFromAuthentication();
        if(emailFromDto.equals(emailFromAuthentication)){
            announcement.setTitle(announcementDto.getTitle());
            announcement.setDescription(announcementDto.getDescription());
            announcement.setCreationDate(new Timestamp(new Date().getTime()));
            announcementRepository.save(announcement);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<AnnouncementDto> getUserAnnouncement() {
        List<Announcement> announcements = announcementRepository.findAllByUserEmail(userService.getEmailFromAuthentication());
        return announcements.stream().map(announcementMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SimilarAnnouncementDto> getSimilarAnnouncement(Long id) {
        List<SimilarAnnouncementDto>  similarAnnouncementDtos = new ArrayList<>();
        Announcement announcement = announcementRepository.getOne(id);
        String title = announcement.getTitle();
        String description = announcement.getDescription();
        Set<String> titleSet = getSet(title);
        Set<String> descriptionSet = getSet(description);
        List<Announcement> announcements = announcementRepository.findAll();
        Iterator<Announcement> iterator = announcements.iterator();
        while (iterator.hasNext()) {
            Announcement next = iterator.next();
            Set<String> set = getSet(next.getTitle());
            Set<String> set1 = getSet(next.getDescription());
            long countOfSameWordsInTitle = countOfSameWords(titleSet, set);
            long countOfSameWordsInDescription = countOfSameWords(descriptionSet, set1);
            if(countOfSameWordsInDescription >= 1 && countOfSameWordsInTitle >= 1) {
                if(next.equals(announcement)) {
                    continue;
                }
                SimilarAnnouncementDto similarAnnouncementDto = similarAnnouncementMapper.convertToDto(next);
                similarAnnouncementDto.setSimilarAnnouncement(countOfSameWordsInDescription + countOfSameWordsInTitle);
                similarAnnouncementDtos.add(similarAnnouncementDto);
            }
        }
        similarAnnouncementDtos.sort(Comparator.comparing(SimilarAnnouncementDto::getSimilarAnnouncement).reversed());
        return similarAnnouncementDtos.stream().limit(3).collect(Collectors.toList());
    }

    @Override
    public void deleteAnnouncement(Long id) {
        boolean equals = announcementRepository.getOne(id).getUser().getEmail().equals(userService.getEmailFromAuthentication());
        if (equals) {
            announcementRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

    private Set<String> getSet(String sentence) {
        Set<String> set = new HashSet<>();
        String[] words = sentence.split("(\\s+)");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[,.!?:;]", "");
            set.add(words[i]);
        }
        return set;
    }

    private <T> long countOfSameWords(Set<T> a, Set<T> b) {
        Set<T> mergedSet = new HashSet<T>();

        mergedSet.addAll(a);
        mergedSet.addAll(b);

        long sizeOfTwoSet = a.size() + b.size();
        long mergedSize = mergedSet.size();

        return sizeOfTwoSet - mergedSize;
    }
}
