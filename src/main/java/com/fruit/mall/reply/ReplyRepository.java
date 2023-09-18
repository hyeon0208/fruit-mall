package com.fruit.mall.reply;

import com.fruit.mall.reply.dto.ReplyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReplyRepository implements ReplyMapper {
    private final ReplyMapper replyMapper;

    @Override
    public Optional<ReplyResDto> selectReplyByReviewId(Long reviewId) {
        return replyMapper.selectReplyByReviewId(reviewId);
    }
}
