package com.fruit.mall.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;

    public void insertNotifications(Notifications notifications) {
        notificationsRepository.insertNotifications(notifications);
    }


}
