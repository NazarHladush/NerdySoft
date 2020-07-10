package com.nerdysoft.service;

import com.nerdysoft.dto.AnnouncementDto;
import com.nerdysoft.dto.SimilarAnnouncementDto;
import com.nerdysoft.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementDto> getAllAnnouncement();

    AnnouncementDto getAnnouncementById(Long id);

    Announcement createAnnouncement(AnnouncementDto announcementDto);

    void updateAnnouncement(AnnouncementDto announcementDto);

    List<AnnouncementDto> getUserAnnouncement();

    List<SimilarAnnouncementDto> getSimilarAnnouncement(Long id);

    void deleteAnnouncement(Long id);
}
