package com.springdatajpa.base.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Describe:
 * @Author: Summer
 * @Date: 2020/10/28
 */
@Data
@MappedSuperclass
public abstract class BaseAbstractEntity implements BaseEntity {
    /**
     * id */
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "uuid"
    )
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid2"
    )
    @Column(
            name = "`ID`",
            nullable = false
    )
    private String id;

    /**
     * 乐观锁 */
    @Version
    @Column(
            name = "`VERSION`",
            nullable = false
    )
    private Integer version;

    /**
     * 创建人 */
    @Column(
            name = "`CREATOR_ID`",
            nullable = false
    )
    private String creatorId;

    /**
     * 创建 */
    @Column(
            name = "`CREATE_TIME`",
            nullable = false
    )
    private LocalDateTime createTime;

    /**
     * 修改人 */
    @Column(
            name = "`MODIFY_ID`",
            nullable = false
    )
    private String modifyId;

    /**
     * 修改日期 */
    @Column(
            name = "`MODIFY_TIME`",
            nullable = false
    )
    private LocalDateTime modifyTime;

    /**
     * 逻辑删除标记 */
    @Column(
            name = "`DELETED`",
            nullable = false
    )
    private Boolean deleted;

}
