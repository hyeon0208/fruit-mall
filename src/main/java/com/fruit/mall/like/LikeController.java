package com.fruit.mall.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;


    @PostMapping("/like/disabled")
    public String likeDisable(@RequestBody Like like) {
        likeService.deleteLike(like.getProductId());
        return "success";
    }

    @PostMapping("/like/activate")
    public String likeActivate(@RequestBody Like like) {
        likeService.insertLike(like);
        return "success";
    }
}
