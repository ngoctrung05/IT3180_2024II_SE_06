package com.IT3180.repository;

import com.IT3180.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
	void deleteByCreatedAtBefore(LocalDateTime dateTime);
}