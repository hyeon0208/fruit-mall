package com.fruit.mall.like;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fruit.mall.redis.RedisCacheKey.LIKE_COUNT;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @CacheEvict(value = LIKE_COUNT, key = "#like.userIdNo", cacheManager = "cacheManager")
    public void insertLike(Like like) {
        likeRepository.insertLike(like);
    }


    @CacheEvict(value = LIKE_COUNT, key = "#userId", cacheManager = "cacheManager")
    public void deleteLike(Long userId, Long id) {
        likeRepository.deleteLike(userId, id);
    }

    @Cacheable(value = LIKE_COUNT, key = "#id", condition = "#id != null", cacheManager = "cacheManager")
    public int countLikesByUserId(Long id) {
        return likeRepository.countLikesByUserId(id);
    }
}
