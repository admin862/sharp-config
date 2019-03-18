package com.dafy.config.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by de on 2017/6/19.
 */
@Data
public class Role {

    private long id;

    private String rolename;

    private String description;

    private Date createDate;

}
