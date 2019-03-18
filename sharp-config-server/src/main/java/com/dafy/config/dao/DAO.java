package com.dafy.config.dao;

import java.util.List;

/**
 * DAO通用接口
 * @author 言枫
 * @time 2016年12月25日
 */
public interface DAO<T> {

    int save(String statement, Object parameter);

    int update(String statement, Object parameter);

    int delete(String statement, Object parameter);

    int count(String statement, Object parameter);

    T getModelBy(String statement, Object parameter);

    List<T> getListBy(String statement, Object parameter);

    long getCountBy(String statement,Object parameter);

//    PageModel<T> getPageDataBy(PageModel<T> page, String statementForList, String statementForCount, Object parameterForList, Object parameterForCount);

}

