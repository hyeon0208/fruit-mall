package com.fruit.mall.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public void insertLike(Like like) {
        likeRepository.insertLike(like);
    }

    public void deleteLike(Long id) {
        likeRepository.deleteLike(id);
    }

    public int countLikesByUserId(Long id) {
        return likeRepository.countLikesByUserId(id);
    }
}
