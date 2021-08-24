package com.example.SSGPaymtCertProject.repository;

import com.example.SSGPaymtCertProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @since 2021. 08. 12
 * @author kwon-yong-il
 *         <h2>유저 레파지토리</h2>
 *         <p>
 *         아래 Doc에 정의된 규칙에 맞는 메서드를 선언하면 JPA가 구현 클래스를 자동으로 만들어 준다.
 *         </p>
 *         <p>
 *         참고 Doc(아래 링크 바로 연결 가능)
 *         https://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html
 *         </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param mbrLoginId 로그인ID
     * @return User
     *         <p>
     *         로그인 ID를 이용해 User를 찾는다. (Doc에 정의된 규칙에 맞는 메서드 필요에 따라 추가 선언)
     *         </p>
     */
    User findByMbrLoginId(String mbrLoginId);

}

