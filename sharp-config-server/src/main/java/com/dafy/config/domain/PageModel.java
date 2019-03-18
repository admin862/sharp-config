package com.dafy.config.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageModel<T> {

    private int page;

    private int pageSize;

    private long count;

    private List<T> dataList;

}
