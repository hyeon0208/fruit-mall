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
    private Long reviewId;
    private String notificationsMessage;
    private Timestamp notificationsCreatedAt;
    private Timestamp isRead;

    @Builder
    public Notifications(Long reviewId, String notificationsMessage) {
        this.reviewId = reviewId;
        this.notificationsMessage = notificationsMessage;
    }
}
