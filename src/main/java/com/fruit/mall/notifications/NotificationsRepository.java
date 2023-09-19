package com.fruit.mall.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationsRepository implements NotificationsMapper {
    private final NotificationsMapper notificationsMapper;

    @Override
    public void insertNotifications(Notifications notifications) {
        notificationsMapper.insertNotifications(notifications);
    }

    @Override
    public void deleteOldestNotificationsByUserId(Long userIdNo, int delCount) {
        notificationsMapper.deleteOldestNotificationsByUserId(userIdNo, delCount);
    }

    @Override
    public int countNotificationsByUserId(Long userIdNo) {
        return notificationsMapper.countNotificationsByUserId(userIdNo);
    }
}
