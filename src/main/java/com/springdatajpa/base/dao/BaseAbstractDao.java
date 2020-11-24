package com.springdatajpa.base.dao;

import com.springdatajpa.base.model.BaseAbstractEntity;
import com.springdatajpa.base.utils.JCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/28
 */
public interface BaseAbstractDao<T extends BaseAbstractEntity> {

    T save(T t);

    List<T> save(Iterable<T> entities);

    T edit(T t);

    /**
     * 修改对象，会设置默认属性修改时间及修改人员
     * @param t
     * @return
     */
    T editSetDefAttr(T t);

    boolean isNew(T t);

    void delete(T t);

    void delete(Collection<T> collection);

    void delete(String id);

    void delete(List<String> idList);

    void logicalDelete(String id);

    void logicalDelete(T t);

    void logicalDelete(Collection<T> collection);

    void logicalDelete(List<String> idList);

    List<T> findAll();

    List<T> findAll(JCondition jCondition);

    T findById(String id);

    T findDetachById(String id);

    T findOne(JCondition jCondition);

    Page<T> page(Pageable pageable);

    Page<T> page(Pageable pageable, Map<String, Object> param);

    Page<T> page(Pageable pageable, JCondition con);

    String getCurrentUserId();
}
