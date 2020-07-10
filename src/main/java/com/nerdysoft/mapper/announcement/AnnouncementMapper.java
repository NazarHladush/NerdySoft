package com.nerdysoft.mapper.announcement;

import com.nerdysoft.dto.AnnouncementDto;
import com.nerdysoft.mapper.GeneralMapper;
import com.nerdysoft.model.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper implements GeneralMapper<Announcement, AnnouncementDto> {
    @Override
    public AnnouncementDto convertToDto(Announcement model) {
        return AnnouncementDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .creationDate(model.getCreationDate())
                .userId(model.getUser().getId())
                .build();
    }

    @Override
    public Announcement convertToModel(AnnouncementDto dto) {
        return Announcement.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .creationDate(dto.getCreationDate())
                .build();
    }
}
