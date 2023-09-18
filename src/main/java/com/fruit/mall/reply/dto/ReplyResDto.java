package com.fruit.mall.reply.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReplyResDto {
    private String comments;
    private Timestamp replyCreatedAt;

    public ReplyResDto(String comments, Timestamp replyCreatedAt) {
        this.comments = comments;
        this.replyCreatedAt = replyCreatedAt;
    }
}
