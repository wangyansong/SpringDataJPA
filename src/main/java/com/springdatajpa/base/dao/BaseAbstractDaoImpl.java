package com.springdatajpa.base.dao;

import com.springdatajpa.base.exception.RRException;
import com.springdatajpa.base.model.BaseAbstractEntity;
import com.springdatajpa.base.repo.JSpringRepository;
import com.springdatajpa.base.utils.JCondition;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/28
 */
public abstract class BaseAbstractDaoImpl<T extends BaseAbstractEntity> implements BaseAbstractDao<T> {

    public T save(T t) {
        setDefaultAttr(t);
        return repo().save(t);
    }

    @Override
    public T edit(T t) {
        LocalDateTime now = LocalDateTime.now();
        t.setModifyTime(now);
        return repo().save(t);
    }

    @Override
    public T editSetDefAttr(T t) {
        LocalDateTime now = LocalDateTime.now();
        t.setModifyTime(now);
        t.setModifyId(getCurrentUserId());
        return repo().save(t);
    }

    private void setDefaultAttr(T t) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew(t)) {
            t.setCreatorId(getCurrentUserId());
            t.setModifyId(getCurrentUserId());
            t.setCreateTime(now);
            t.setModifyTime(now);
            t.setDeleted(false);
        } else {
            t.setModifyTime(now);
            t.setModifyId(getCurrentUserId());
        }
    }

    public boolean isNew(T t) {
        return t.getId() == null;
    }

    public List<T> save(Iterable<T> entities) {
        for (T t : entities) {
            setDefaultAttr(t);
        }
        return repo().saveAll(entities);
    }

    public void delete(T t) {
        repo().delete(t);
    }

    public void delete(Collection<T> collection) {
        repo().deleteAll(collection);
    }

    public void delete(String id) {
        repo().deleteById(id);
    }

    public void delete(List<String> idList) {
        for (String id : idList) {
            delete(id);
        }
    }

    public void logicalDelete(String id) {
        T one = findById(id);
        LocalDateTime now = LocalDateTime.now();
        one.setModifyId(getCurrentUserId());
        one.setDeleted(true);
        one.setModifyTime(now);
    }

    public void logicalDelete(T t) {
        if (isNew(t)) {
            throw new RuntimeException("id must not be null");
        } else {
            t.setDeleted(true);
            save(t);
        }
    }

    public void logicalDelete(Collection<T> collection) {
        for (T t : collection) {
            logicalDelete(t);
        }
    }

    public void logicalDelete(List<String> idList) {
        for (String id : idList) {
            logicalDelete(id);
        }
    }

    public List<T> findAll() {
        return repo().findAll();
    }

    public List<T> findAll(JCondition jCondition) {
        return findAll((Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = buildQuery(jCondition, predicate, root, query, cb);
            return predicate;
        });
    }

    public List<T> findAll(Specification<T> specification) {
        return repo().findAll(specification);
    }

    public T findById(String id) {
        return repo().findById(id).orElse(null);
    }

    public T findDetachById(String id) {
        T one = findById(id);
        try {
            if (one == null) {
                throw new RRException("根据id找不到对应的数据id=[" + id + "]");
            }
            T detach = (T) one.getClass().newInstance();
            BeanUtils.copyProperties(one, detach);
            return detach;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RRException(e);
        }
    }

    public T findOne(JCondition jCondition) {
        Optional<T> one = repo().findOne((Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = buildQuery(jCondition, predicate, root, query, cb);
            return predicate;
        });
        return one.orElse(null);
    }


    public Page<T> page(Pageable pageable) {
        return page(pageable, new WeakHashMap<>());
    }

    public Page<T> page(Pageable pageable, Map<String, Object> param) {
        JCondition con = JCondition.ready().equals(param);
        return page(pageable, con);
    }

    public Page<T> page(Pageable pageable, JCondition con) {
        return page((Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = buildQuery(con, predicate, root, query, cb);
            return predicate;
        }, pageable);
    }

    public Page<T> page(Specification<T> specification, Pageable pageable) {
        return repo().findAll(specification, pageable);
    }

    private Predicate buildQuery(JCondition jCondition, Predicate predicate, Root<T> root,
        CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (null != jCondition) {
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (null != jCondition.getEqual() && !jCondition.getEqual().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getEqual().entrySet()) {
                    expressions.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
                }
            }
            if (null != jCondition.getNotEqual() && !jCondition.getNotEqual().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getNotEqual().entrySet()) {
                    expressions.add(cb.notEqual(root.get(entry.getKey()), entry.getValue()));
                }
            }
            if (null != jCondition.getIsNull() && !jCondition.getIsNull().isEmpty()) {
                for (String property : jCondition.getIsNull()) {
                    expressions.add(cb.isNull(root.get(property)));
                }
            }
            if (null != jCondition.getGte() && !jCondition.getGte().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getGte().entrySet()) {
                    expressions.add(
                        cb.greaterThanOrEqualTo(root.get(entry.getKey()), (Comparable) entry.getValue()));
                }
            }
            if (null != jCondition.getGt() && !jCondition.getGt().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getGt().entrySet()) {
                    expressions.add(cb.greaterThan(root.get(entry.getKey()), (Comparable) entry.getValue()));
                }
            }
            if (null != jCondition.getLte() && !jCondition.getLte().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getLte().entrySet()) {
                    expressions
                        .add(cb.lessThanOrEqualTo(root.get(entry.getKey()), (Comparable) entry.getValue()));
                }
            }
            if (null != jCondition.getLt() && !jCondition.getLt().isEmpty()) {
                for (Map.Entry<String, Object> entry : jCondition.getLt().entrySet()) {
                    expressions.add(cb.lessThan(root.get(entry.getKey()), (Comparable) entry.getValue()));
                }
            }

            if (null != jCondition.getLike() && !jCondition.getLike().isEmpty()) {
                for (Map.Entry<String, String> entry : jCondition.getLike().entrySet()) {
                    expressions.add(cb.like(root.get(entry.getKey()), entry.getValue()));
                }
            }

            if (null != jCondition.getOrder() && !jCondition.getOrder().isEmpty()) {
                List<Order> orders = new ArrayList<>();

                for (Map.Entry<String, JCondition.OrderType> entry : jCondition.getOrder().entrySet()) {
                    if (null != entry.getValue()) {
                        if (JCondition.OrderType.desc.equals(entry.getValue())) {
                            Order desc = cb.desc(root.get(entry.getKey()));
                            //query.orderBy(order);
                            orders.add(desc);
                        } else if (JCondition.OrderType.asc.equals(entry.getValue())) {
                            Order asc = cb.asc(root.get(entry.getKey()));
                            orders.add(asc);
                        }
                    }
                }
                query.orderBy(orders);
            }

            if (null != jCondition.getIn() && !jCondition.getIn().isEmpty()) {
                for (Map.Entry<String, List<String>> entry : jCondition.getIn().entrySet()) {
                    List<String> inValueList = entry.getValue();
                    if (null != inValueList) {
                        int size = inValueList.size();
                        if (size > 0) {
                            CriteriaBuilder.In in = cb.in(root.get(entry.getKey()));
                            in.value(inValueList);
                            expressions.add(in);
                        } else {
                            expressions.add(cb.isTrue(cb.literal(Boolean.FALSE)));
                        }

                    }
                }
            }

            if (null != jCondition.getNotIn() && !jCondition.getNotIn().isEmpty()) {
                for (Map.Entry<String, List<Object>> entry : jCondition.getNotIn().entrySet()) {
                    List<Object> notInValueList = entry.getValue();
                    if (null != notInValueList) {
                        int size = notInValueList.size();
                        if (size > 0){
                            CriteriaBuilder.In in = cb.in(root.get(entry.getKey()));
                            in.value(notInValueList);
                            expressions.add(in.not());
                        }else {
                            expressions.add(cb.isTrue(cb.literal(Boolean.FALSE)));
                        }

                    }
                }
            }

        }
        return predicate;
    }

    public String getCurrentUserId() {
        return "currentUser";
    }

    protected abstract JSpringRepository<T> repo();
}
