package com.fruit.mall.reply;

import com.fruit.mall.reply.dto.ReplyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public Optional<ReplyResDto> selectReplyByReviewId(Long reviewId) {
        return replyRepository.selectReplyByReviewId(reviewId);
    }
}
