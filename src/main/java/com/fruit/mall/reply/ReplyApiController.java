package com.fruit.mall.reply;

import com.fruit.mall.reply.dto.ReplyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final ReplyService replyService;

    @GetMapping("/reply/{reviewId}")
    public ResponseEntity<ReplyResDto> showReply(@PathVariable Long reviewId) {
        Optional<ReplyResDto> reply = replyService.selectReplyByReviewId(reviewId);
        if (reply.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reply.get());
    }
}
