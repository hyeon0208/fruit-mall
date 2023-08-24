package com.fruit.mall.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository implements LikeMapper {
    private final LikeMapper likeMapper;

    @Override
    public void insertLike(Like like) {
        likeMapper.insertLike(like);
    }

    @Override
    public void deleteLike(Long id) {
        likeMapper.deleteLike(id);
    }
}
