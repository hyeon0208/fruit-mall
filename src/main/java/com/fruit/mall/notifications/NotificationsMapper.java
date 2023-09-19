package com.fruit.mall.notifications;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationsMapper {
    void insertNotifications(Notifications notifications);

    void deleteOldestNotificationsByUserId(@Param("userIdNo") Long userIdNo, @Param("delCount") int delCount);

    int countNotificationsByUserId(@Param("userIdNo") Long userIdNo);
}
