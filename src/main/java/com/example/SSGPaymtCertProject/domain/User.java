package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.domain.base.BaseEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @since 2021. 08. 18
 * @author kwon-yong-il
 *
 *         <h2>JPA와 LOMBOK 사용</h2>
 *         <h3>1. @NoArgsConstructor(access = AccessLevel.PROTECTED)</h3>
 *         <p>
 *         JPA에서는 프록시를 생성을 위해서 기본 생성자를 반드시 하나 생성해야함.
 *         (이때 필요 접근 권한 protected) public 생성자를 통해서 객체를 직접
 *         생성하면 객체 생성시 안전성을 떨어트린다. (ID때문이라도 JPA에 맡긴다.)
 *         </p>
 *         <h3>2. @AllArgsConstructor 또는 @builder는 Class범위 선언으로 하지 않는다.</h3>
 *         <p>
 *         User Id 생성전략은 데이터베이스의 auto_increment를 의존하고 있다고 가정했을 경우 "Id"를 넘겨받지
 *         않아야한다.
 *         "@CreationTimestamp, @UpdateTimestamp"도 해당 일을 담당.
 *         이 처럼 객채 생성시 받지 않아야 할 데이터들이 클래스 상단 @Builder or @AllArgsConstructor 사용시 발생
 *         </p>
 *         <h3>3. ToStirng exclude 사용</h3>
 *         <p>
 *         User 객체와 userGrps 객체가 양방향 영관관계일 경우 ToString을 호출하면 무한 순환 참조 발생 해당 어노테이션을 이용해서 ToString
 *         항목에서 제외
 *         </p>
 *         <h3>4. onlyExplicitlyIncluded = true </h3>
 *         <p> onlyExplicitlyIncluded = true : EqualsAndHashCode.Include가 있는 필드만 포함 (ToString도 사용가능)</p>
 *         <h3>5. callSuper = false </h3>
 *         <p>부모 필드는 제외</p>
 *         <h3>6. SpringSecurity UserDetail 구현 </h3>
 *         <p>RoleEnum을 정의했으므로 권한을 오버라이딩 하기 위해 getAuthorities 메서드 구현</p>
 */
@Entity
@ToString(exclude = "password")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "USER",
        uniqueConstraints = {@UniqueConstraint(name = "MBR_LOGIN_ID_UNIQUE", columnNames = {"MBR_LOGIN_ID"})})
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "MBR_ID")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "MBR_LOGIN_ID", nullable = false, length = 30)
    private String mbrLoginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @EqualsAndHashCode.Include
    @Column(name = "EMAIL", nullable = false, length = 40)
    private String email;

    @EqualsAndHashCode.Include
    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE", nullable = false)
    private RoleEnum roleType = RoleEnum.ROLE_USER;

    @Builder
    public User(String mbrLoginId, String password, String email, String name,String regpeId, String modpeId, RoleEnum roleType) {
        this.mbrLoginId = mbrLoginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.regpeId = regpeId;
        this.modpeId = modpeId;
        this.roleType = roleType;
    }

    /**
     * 해당 유저의 권한을 리턴
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        String role = this.roleType.getRole();
        auth.add(new SimpleGrantedAuthority(role));
        return auth;
    }

    @Override
    public String getUsername() {
        return this.mbrLoginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
