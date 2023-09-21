package com.fruit.mall.notifications.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class NotificationsResDto {
    private Long notificationsId;
    private String notificationsMessage;
    private int createdHours;

    @Builder
    public NotificationsResDto(Long notificationsId, String notificationsMessage, int createdHours) {
        this.notificationsId = notificationsId;
        this.notificationsMessage = notificationsMessage;
        this.createdHours = createdHours;
    }
}
