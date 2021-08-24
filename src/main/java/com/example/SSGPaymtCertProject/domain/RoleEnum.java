package com.example.SSGPaymtCertProject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 2021. 08. 11
 * @author kwon-yong-il
 *         <h2>유저 등급(역할) 정의 Enum 클래스</h2>
 *         <ul>
 *         <li>ROLE_ADMIN - 관리자</li>
 *         <li>ROLE_USER - 회원</li>
 *         </ul>
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

    private String role;

}