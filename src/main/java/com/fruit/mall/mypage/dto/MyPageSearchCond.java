package com.fruit.mall.mypage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MyPageSearchCond {
    private int clickedDuration;
    private String startDate;
    private String endDate;
    private String category;
    private String searchText;
    private int pageNum;
    private int pageSize;
}
