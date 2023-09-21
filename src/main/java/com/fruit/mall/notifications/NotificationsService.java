package com.fruit.mall.notifications;

import com.fruit.mall.notifications.dto.NotificationsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public void insertNotifications(Notifications notifications) {
        notificationsRepository.insertNotifications(notifications);
    }

    public void deleteOldestNotificationsByUserId(Long userIdNo, int delCount) {
        notificationsRepository.deleteOldestNotificationsByUserId(userIdNo, delCount);
    }

    public int countNotificationsByUserId(Long userIdNo) {
        return notificationsRepository.countNotificationsByUserId(userIdNo);
    }

    public List<NotificationsResDto> selectMessagesByUserId(Long userIdNo) {
        return notificationsRepository.selectMessagesByUserId(userIdNo);
    }

    public void updateRead(Long notificationsId) {
        notificationsRepository.updateRead(notificationsId);
    }

    public Long selectProductIdByNotificationsId(Long notificationsId) {
        return notificationsRepository.selectProductIdByNotificationsId(notificationsId);
    }
}
