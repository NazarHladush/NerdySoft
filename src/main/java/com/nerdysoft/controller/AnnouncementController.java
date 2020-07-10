package com.nerdysoft.controller;

import com.nerdysoft.dto.AnnouncementDto;
import com.nerdysoft.dto.SimilarAnnouncementDto;
import com.nerdysoft.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/findAll")
    public List<AnnouncementDto> getAnnouncements() {
        return announcementService.getAllAnnouncement();
    }

    @GetMapping("/user")
    public List<AnnouncementDto> getUserAnnouncements() {
        return announcementService.getUserAnnouncement();
    }

    @GetMapping("/similar/{id}")
    public List<SimilarAnnouncementDto> getSimilarAnnouncement(@PathVariable Long id) {
        return announcementService.getSimilarAnnouncement(id);
    }

    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncement(@PathVariable Long id) {
        return announcementService.getAnnouncementById(id);
    }

    @PostMapping
    public HttpStatus createAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        announcementService.createAnnouncement(announcementDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        announcementService.updateAnnouncement(announcementDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return HttpStatus.OK;
    }
}
