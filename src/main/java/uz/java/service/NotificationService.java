package uz.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.dto.cache.ApiResponse;
import uz.java.dto.cache.CacheDto;
import uz.java.dto.notification.NotificationResponse;
import uz.java.entity.notification.Notification;
import uz.java.entity.user.User;
import uz.java.exception.GenericNotFoundException;
import uz.java.exception.InvalidDataException;
import uz.java.exception.RedisNotSerializableException;
import uz.java.repository.NotificationRepository;
import uz.java.repository.UserRepository;
import uz.java.util.CachePrefix;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final CacheManagerService cacheManagerService;

    @Transactional(readOnly = true)
    public ApiResponse<List<NotificationResponse>> getMyNotifications(Long userId) {
        Object data = cacheManagerService.get(userId.toString(), CachePrefix.NOTIFICATION);
        if (data != null)
            return (ApiResponse<List<NotificationResponse>>) data;

        List<NotificationResponse> list = notificationRepository.findByUserId(userId)
                .stream().map(this::toResponse).toList();
        try {
            cacheManagerService.put(userId.toString(), CachePrefix.NOTIFICATION, new ApiResponse<>(list));
        } catch (Exception e) {
            throw new RedisNotSerializableException(e.getMessage());
        }
        return new ApiResponse<>(list);
    }

    @Transactional
    public Boolean markAsRead(Long id, Long userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("notification.not.found"));
        if (!notification.getUser().getId().equals(userId))
            throw new InvalidDataException("access.denied");
        notification.setIsRead(true);
        notificationRepository.save(notification);
        cacheManagerService.delete(CachePrefix.NOTIFICATION);
        return true;
    }

    @Transactional
    public Boolean delete(Long id, Long userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("notification.not.found"));
        if (!notification.getUser().getId().equals(userId))
            throw new InvalidDataException("access.denied");
        notification.makeAsDeleted();
        notificationRepository.save(notification);
        cacheManagerService.delete(CachePrefix.NOTIFICATION);
        return true;
    }

    // Boshqa service lardan chaqirish uchun (masalan ariza topshirilganda)
    @Transactional
    public void send(Long userId, String type, String title, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericNotFoundException("user.not.found"));
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setIsRead(false);
        notificationRepository.save(notification);
        cacheManagerService.delete(CachePrefix.NOTIFICATION);
    }

    private NotificationResponse toResponse(Notification n) {
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setType(n.getType());
        r.setTitle(n.getTitle());
        r.setMessage(n.getMessage());
        r.setIsRead(n.getIsRead());
        r.setCreatedAt(n.getCreatedAt());
        return r;
    }
}
