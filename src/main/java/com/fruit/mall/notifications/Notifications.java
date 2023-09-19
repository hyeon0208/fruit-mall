package com.fruit.mall.notifications;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
public class Notifications {
    private Long notificationsId;
    private Long userIdNo;
    private String message;
    private Timestamp notificationsCreatedAt;
    private Timestamp readAt;
    private String type;

    @Builder
    public Notifications(Long userIdNo, String message, String type) {
        this.userIdNo = userIdNo;
        this.message = message;
        this.type = type;
    }
}
