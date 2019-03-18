package com.dafy.config.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;

@Repository("daoSupport")
public class DAOSupport<T> implements DAO<T> {

    @Resource(name = "sqlSessionTemplateConfig")
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int save(String statement, Object parameter) {
        return sqlSessionTemplate.insert(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        return sqlSessionTemplate.update(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return sqlSessionTemplate.delete(statement, parameter);
    }

    @Override
    public int count(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(statement,parameter);
    }

    @Override
    public T getModelBy(String statement, Object parameter){
        return sqlSessionTemplate.selectOne(statement, parameter);
    }

    @Override
    public List<T> getListBy(String statement, Object parameter) {
        return sqlSessionTemplate.selectList(statement, parameter);
    }

    @Override
    public long getCountBy(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(statement,parameter);
    }

}
