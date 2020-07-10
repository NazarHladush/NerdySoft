package com.nerdysoft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimilarAnnouncementDto {
    Long id;

    private String title;

    private String description;

    Timestamp creationDate;

    private Long userId;

    private long similarAnnouncement;
}
