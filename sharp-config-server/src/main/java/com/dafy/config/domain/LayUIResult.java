package com.dafy.config.domain;

import lombok.Data;

@Data
public class LayUIResult {

    private int code;

    private String msg;

    private long count;

    Object data;

}
