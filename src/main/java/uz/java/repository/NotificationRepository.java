package uz.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.java.entity.notification.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.user.id = ?1 order by n.createdAt desc")
    List<Notification> findByUserId(Long userId);

    @Query("select count(n) from Notification n where n.user.id = ?1 and n.isRead = false")
    Long countUnreadByUserId(Long userId);
}
