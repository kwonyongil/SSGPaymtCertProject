package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @since 2021. 08. 25
 * @author kwon-yong-il
 *         <h2>User 테스트 & 테스트 클래스 템플릿 목적</h2>
 *         <h3>@DataJpaTest JPA 관련된 설정만 로드</h3>
 *         <p>1. JPA를 사용하서 데이터를 올바르게 조회, 생성, 수정, 삭제 하는지 등의 테스트</p>
 *         <p>2. @Entity 클래스를 스캔하여 스프링 데이터 JPA 저장소를 구성한다. ( 다른 컴포넌트를 스캔하지 않음 )</p>
 *         <p>3. @Transactional 어노테이션을 포함하고 있기 때문에 따로 선언하지 않아도 됨</p>
 *         <p>@Transactional 기능이 필요하지 않으면 @Transactional(propagation = Propagation.NOT_SUPPORTED) 설정</p>
 *         <p>4. in-memory embedded database에 대한 테스트
 *         <p>real database를 사용하고자 하는 경우@AutoConfigureTestDatabase 사용하면 된다.</p>
 *         <p>@AutoConfigureTestDatabase Default 설정 값은 Any이다. (기본적으로 내장된 데이터소스를 사용한다).</p>
 *         <p>@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)을 지정하면 실제 디비도 사용 가능</p>
 *         <p>5. @ActiveProfiles("test") 등의 프로파일이 설정도 가능 </p>
 */
@DataJpaTest
// @Transactional(propagation = Propagation.NOT_SUPPORTED)
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = {"local"})
class UserRepositoryTest {

    // private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;

    /**
     * 픽스처 1, 2, 3
     */
    User kwonUser;

    User kwonUser2;

    User kwonUser3;

    /**
     * 단위테스트 시작 전 공통적으로 초기화되는 코드 - {@link User} 엔티티를 생성하고 기본 정보를 등록하는 등의 작업 진행
     */
    @BeforeEach
    public void setUp() {
        this.kwonUser = User.builder()
                .mbrLoginId("ksgkms23")
                .password("kk123123")
                .email("ksgkms23@naver.com")
                .name("kwonyongil")
                .regpeId("권용일")
                .modpeId("권용일")
                .roleType(RoleEnum.ROLE_USER)
                .build();

        this.kwonUser2 = User.builder()
                .email("ksgkms23@naver.com")
                .mbrLoginId("ksgkms23")
                .password("kk123123")
                .name("권용이")
                .regpeId("권용일")
                .modpeId("권용일")
                .roleType(RoleEnum.ROLE_USER)
                .build();

        this.kwonUser3 = User.builder()
                .email("ksgkms235@naver.com")
                .mbrLoginId("ksgkms24")
                .password("kk123123")
                .name("권용삼")
                .regpeId("권용일")
                .modpeId("권용일")
                .roleType(RoleEnum.ROLE_ADMIN)
                .build();
    }

    /**
     * 단위테스트 종료 후 공통적으로 적용될 로직 - {@link User} 엔티티를 생성하고 기본 정보를 소거하는 등의 작업 진행 -
     * {@link @Transactional}이 걸려있다면 엔티티는 신경안써도 되겠지만 -
     * {@link org.springframework.test.context.jdbc.Sql} 등을 사용한 경우에는 이에 대한 초기화 용도로 사용
     */
    @AfterEach
    public void tearDown() {
    }

    // Junit5 BDD 사용
    @Test
    public void jUnit5_테스트() {
        then("").isEqualTo("");
    }

    @Test
    public void save_테스트() {
        userRepository.save(kwonUser);
    }

    @Test
    public void findByMbrLoginId_테스트() {
        userRepository.save(kwonUser);
        User user = userRepository.findByMbrLoginId("ksgkms23");

        then(user.getMbrLoginId()).isEqualTo("ksgkms23");
        then(user.getName()).isEqualTo("kwonyongil");
    }
}