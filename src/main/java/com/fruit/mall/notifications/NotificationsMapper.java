package com.fruit.mall.notifications;

import com.fruit.mall.notifications.dto.NotificationsResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationsMapper {
    void insertNotifications(Notifications notifications);

    void deleteOldestNotificationsByUserId(@Param("userIdNo") Long userIdNo, @Param("delCount") int delCount);

    int countNotificationsByUserId(@Param("userIdNo") Long userIdNo);

    List<NotificationsResDto> selectMessagesByUserId(@Param("userIdNo") Long userIdNo);

    void updateRead(@Param("notificationsId") Long notificationsId);
}
