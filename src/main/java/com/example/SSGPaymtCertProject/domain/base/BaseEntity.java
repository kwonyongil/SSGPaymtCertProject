package com.example.SSGPaymtCertProject.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @since 2021. 08. 04
 * @author kwon-yong-il
 * <h2>테이블에 매핑없이 오로지 기본적인 매핑 정보만 상속해주는 부모 엔티티</h2>
 * <h3> @MappedSuperclass </h3>
 * <p>부모 클래스는 테이블과 매핑하지 않고
 * 부모 클래스를 상속받는 자식 클래스에게 매핑 정보만 제공하고 싶을때 사용</p>
 * <h3> @PrePersist(), @PreUpdate() </h3>
 * <p> persist() 전에 호출, update() 전에 호출
 */

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "REGPE_ID", nullable = false, updatable = false)
    protected String regpeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_DTS", nullable = false, updatable = false)
    private Date regDts;

    @Column(name = "MODPE_ID")
    protected String modpeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOD_DTS")
    private Date modDts;

    @PrePersist
    protected void onCreate() {
        regDts = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        modDts = Timestamp.valueOf(LocalDateTime.now());
    }
}
