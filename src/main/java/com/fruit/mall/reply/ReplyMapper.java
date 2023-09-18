package com.fruit.mall.reply;

import com.fruit.mall.reply.dto.ReplyResDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface ReplyMapper {
    Optional<ReplyResDto> selectReplyByReviewId(@Param("reviewId") Long reviewId);
}
