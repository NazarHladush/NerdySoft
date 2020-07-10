package com.nerdysoft.mapper.announcement;

import com.nerdysoft.dto.SimilarAnnouncementDto;
import com.nerdysoft.mapper.GeneralMapper;
import com.nerdysoft.model.Announcement;
import org.springframework.stereotype.Component;

@Component
public class SimilarAnnouncementMapper implements GeneralMapper<Announcement, SimilarAnnouncementDto> {
    @Override
    public SimilarAnnouncementDto convertToDto(Announcement model) {
        return SimilarAnnouncementDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .creationDate(model.getCreationDate())
                .userId(model.getUser().getId())
                .build();
    }

    @Override
    public Announcement convertToModel(SimilarAnnouncementDto dto) {
        return null;
    }
}
