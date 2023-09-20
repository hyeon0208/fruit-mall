package com.fruit.mall.like;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;


    @PostMapping("/like/disabled")
    public String likeDisable(@Login SessionUser sessionUser, @RequestBody Like like) {
        likeService.deleteLike(sessionUser.getUserIdNo(), like.getProductId());
        return "success";
    }

    @PostMapping("/like/activate")
    public String likeActivate(@RequestBody Like like) {
        likeService.insertLike(like);
        return "success";
    }
}
