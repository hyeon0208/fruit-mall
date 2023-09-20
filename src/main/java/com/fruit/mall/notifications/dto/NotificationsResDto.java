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
    private String message;
    private int createdHours;
    private String type;

    @Builder
    public NotificationsResDto(Long notificationsId, String message, int createdHours, String type) {
        this.notificationsId = notificationsId;
        this.message = message;
        this.createdHours = createdHours;
        this.type = type;
    }
}
