package com.fruit.mall.like;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikeApiController {
    private final LikeService likeService;

    @DeleteMapping("/like")
    public String likeDisable(@Login SessionUser sessionUser, @RequestBody Like like) {
        likeService.deleteLike(sessionUser.getUserIdNo(), like.getProductId());
        return "success";
    }

    @PostMapping("/like")
    public String likeActivate(@RequestBody Like like) {
        likeService.insertLike(like);
        return "success";
    }
}
