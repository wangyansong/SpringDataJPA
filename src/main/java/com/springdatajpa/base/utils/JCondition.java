package com.springdatajpa.base.utils;

import java.util.*;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/28
 */
public class JCondition {

  public static final String asc = "asc";
  public static final String desc = "desc";

  public enum OrderType {
    asc("asc"), desc("desc");

    private String alias;

    OrderType(String alis) {
      this.alias = alis;
    }

    public String getAlias() {
      return alias;
    }
  }

  public static final boolean DELETE_NO = false;

  public static JCondition ready() {
    return new JCondition();
  }

  public static JCondition readyDefault() {
    JCondition jCondition = new JCondition();
    jCondition.equal("deleted", DELETE_NO);
    return jCondition;
  }

  private Map<String, Object> equal = new WeakHashMap<>();
  private Map<String, Object> notEqual = new WeakHashMap<>();

  private List<String> isNull = new ArrayList<>();

  private Map<String, String> like = new WeakHashMap<>();

  private Map<String, OrderType> order = new LinkedHashMap<>();

  private Map<String, List<String>> in = new WeakHashMap<>();
  private Map<String, List<Object>> notIn = new WeakHashMap<>();

  private Map<String, Object> gte = new WeakHashMap<>();
  private Map<String, Object> gt = new WeakHashMap<>();

  private Map<String, Object> lte = new WeakHashMap<>();
  private Map<String, Object> lt = new WeakHashMap<>();

  public JCondition isNull(String property) {
    isNull.add(property);
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition notEqualOrigin(String property, Object value) {
    notEqual.put(property, value);
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition notEqual(String property, String value) {
    if (isNotBlank(value)) {
      notEqualOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition notEqual(String property, Object value) {
    if (value != null) {
      notEqualOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition notEquals(Map<String, Object> param) {
    this.notEqual = param;
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition equalOrigin(String property, Object value) {
    equal.put(property, value);
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition equal(String property, String value) {
    if (isNotBlank(value)) {
      equalOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition equal(String property, Object value) {
    if (value != null) {
      equalOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition equals(Map<String, Object> param) {
    this.equal = param;
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition likeOrigin(String property, String value) {
    like.put(property, "%" + value + "%");
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition like(String property, String value) {
    if (isNotBlank(value)) {
      likeOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition likeLeftOrigin(String property, String value) {
    like.put(property, "%" + value);
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition likeLeft(String property, String value) {
    if (isNotBlank(value)) {
      likeLeftOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition likeRightOrigin(String property, String value) {
    like.put(property, value + "%");
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition likeRight(String property, String value) {
    if (isNotBlank(value)) {
      likeRightOrigin(property, value + "%");
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition likes(Map<String, String> map) {
    this.like = map;
    return this;
  }

  public JCondition in(String property, List<String> values) {
    this.in.put(property, Optional.ofNullable(values).orElse(new ArrayList<>()));
    return this;
  }

  public JCondition in(String property, String[] values) {
    Optional<String[]> optional = Optional.ofNullable(values);
    return in(property, Arrays.asList(optional.orElse(new String[]{})));
  }

  public JCondition notIn(String property, List<Object> values) {
    this.notIn.put(property, Optional.ofNullable(values).orElse(new ArrayList<>()));
    return this;
  }

  public JCondition notIn(String property, Object[] values) {
    Optional<Object[]> optional = Optional.ofNullable(values);
    return notIn(property, Arrays.asList(optional.orElse(new Object[]{})));
  }

  /**
   * 没有任何判断
   */
  public JCondition gteOrigin(String property, Object value) {
    gte.put(property, value);
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition gte(String property, Object value) {
    if (value != null) {
      gteOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition gte(String property, String value) {
    if (isNotBlank(value)) {
      gteOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition lteOrigin(String property, Object value) {
    lte.put(property, value);
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition lte(String property, Object value) {
    if (value != null) {
      lteOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition lte(String property, String value) {
    if (isNotBlank(value)) {
      lteOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition gtOrigin(String property, Object value) {
    gt.put(property, value);
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition gt(String property, Object value) {
    if (value != null) {
      gtOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition gt(String property, String value) {
    if (isNotBlank(value)) {
      gtOrigin(property, value);
    }
    return this;
  }

  /**
   * 没有任何判断
   */
  public JCondition ltOrigin(String property, Object value) {
    lt.put(property, value);
    return this;
  }

  /**
   * 包含非空判断
   */
  public JCondition lt(String property, Object value) {
    if (value != null) {
      ltOrigin(property, value);
    }
    return this;
  }

  /**
   * 包含非空,非空白判断
   */
  public JCondition lt(String property, String value) {
    if (isNotBlank(value)) {
      ltOrigin(property, value);
    }
    return this;
  }

  /**
   * @param orderRule : desc or asc
   * {@link JCondition#order(String, OrderType)}
   */
  @Deprecated
  public JCondition order(String property, String orderRule) {
    order.put(property, OrderType.valueOf(orderRule));
    return this;
  }

  /**
   * @param orderTypeRule : desc or asc
   */
  public JCondition order(String property, OrderType orderTypeRule) {
    order.put(property, orderTypeRule);
    return this;
  }

  public JCondition orders(LinkedHashMap<String, OrderType> map) {
    this.order = map;
    return this;
  }

  public Map<String, Object> getEqual() {
    return equal;
  }

  public Map<String, String> getLike() {
    return like;
  }

  public Map<String, OrderType> getOrder() {
    return order;
  }

  public Map<String, List<String>> getIn() {
    return in;
  }

  public Map<String, List<Object>> getNotIn(){
    return notIn;
  }

  public Map<String, Object> getGte() {
    return gte;
  }

  public Map<String, Object> getGt() {
    return gt;
  }

  public Map<String, Object> getLte() {
    return lte;
  }

  public Map<String, Object> getLt() {
    return lt;
  }

  public List<String> getIsNull() {
    return isNull;
  }

  public Map<String, Object> getNotEqual() {
    return notEqual;
  }

  private static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs != null && (strLen = cs.length()) != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }

      return true;
    } else {
      return true;
    }
  }

  private static boolean isNotBlank(CharSequence cs) {
    return !isBlank(cs);
  }
}
