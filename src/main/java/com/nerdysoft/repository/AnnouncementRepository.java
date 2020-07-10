package com.nerdysoft.repository;

import com.nerdysoft.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByUserEmail(String email);
}
