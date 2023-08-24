package com.fruit.mall.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    @Override
    public int countLikesByUserId(Long id) {
        return likeMapper.countLikesByUserId(id);
    }
}
