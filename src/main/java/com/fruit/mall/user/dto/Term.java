package com.fruit.mall.user.dto;

import lombok.*;

import java.io.Serializable;

@Getter
public class Term {

    private Long term_id_no;
    private Long user_id_no;
    private String term_name;
    private boolean term_flag;

    @Builder
    public Term(Long user_id_no, String term_name, boolean term_flag) {
        this.user_id_no = user_id_no;
        this.term_name = term_name;
        this.term_flag = term_flag;
    }
}
