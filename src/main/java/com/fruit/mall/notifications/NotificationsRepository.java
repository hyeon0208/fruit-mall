package com.fruit.mall.notifications;

import com.fruit.mall.notifications.dto.NotificationsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<NotificationsResDto> selectMessagesByUserId(Long userIdNo) {
        return notificationsMapper.selectMessagesByUserId(userIdNo);
    }

    @Override
    public void updateRead(Long notificationsId) {
        notificationsMapper.updateRead(notificationsId);
    }

    @Override
    public Long selectProductIdByNotificationsId(Long notificationsId) {
        return notificationsMapper.selectProductIdByNotificationsId(notificationsId);
    }
}
