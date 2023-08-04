package com.fruit.mall.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션 생성 위치
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 유지 기간
public @interface Login {
}
