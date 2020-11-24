package com.springdatajpa.base.repo;

import com.springdatajpa.base.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/28
 */
@NoRepositoryBean
public interface JSpringRepository<T extends BaseEntity> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T> {

}
