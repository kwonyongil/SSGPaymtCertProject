package com.example.SSGPaymtCertProject.domain;

import com.example.SSGPaymtCertProject.repository.UserRepository;
import com.example.SSGPaymtCertProject.service.user.UserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @since 2021. 08. 25
 * @author kwon-yong-il
 *         <h2> User 테스트</h2>
 *         <p>@Transactional 기능이 필요하지 않으면 @Transactional(propagation = Propagation.NOT_SUPPORTED) 설정</p>
 */
//@RunWith(SpringRunner.class) => Junit4 사용시 추가, Junit5 사용시 생략.
@SpringBootTest
class UserTest {

    // private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceUnit
    EntityManagerFactory emf;
    /**
     * 픽스처 1, 2
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
                .email("ksgkms23@naver.com")
                .mbrLoginId("ksgkms23")
                .password("kk123123")
                .name("권용일")
                .regpeId("권용일")
                .modpeId("권용일")
                .roleType(RoleEnum.ROLE_ADMIN)
                .build();

        this.kwonUser2 = User.builder()
                .email("ksgkms23@naver.com")
                .mbrLoginId("ksgkms24")
                .password("kk123123")
                .name("권용이")
                .regpeId("권용일")
                .modpeId("권용일")
                .roleType(RoleEnum.ROLE_ADMIN)
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

    @Test
    public void 회원가입_테스트() {
        Long id = userService.join(kwonUser);
        // 저장이 잘 되었다면, Builder 패턴이 잘 적용되었음 또한 알 수 있다.
        // 2.0 버전이후 Spring-Data-Jpa 에서 'findOne()' 이 'findById()' 로 수정됨.
        // 2.0 버전이후 Spring-Data-Jpa 에서 리턴값도 'Optional' 로 수정됨.
        // User selectedUser = userRepository.findOne(id);
        // 자바8 Optional 로 반환한 객체가 null 이라면 .orElseThrow 를 통해 IllegalStateException 을 던진다.
        User selectedUser = userRepository.findById(id).orElseThrow(IllegalStateException::new);

        // 조회가 잘 된다면, 저장이 잘 되었음을 알 수 있다.
        then(selectedUser.getEmail()).isEqualTo(kwonUser.getEmail());
        then(selectedUser.getMbrLoginId()).isEqualTo(kwonUser.getMbrLoginId());
        then(selectedUser.getName()).isEqualTo(kwonUser.getName());

        // kwonUser 픽스쳐는 save 되면 암호화(BCrypt)된 Password 를 갖게되어 DB 에서 조회한 User 와 Password 가 일치한다.
        then(selectedUser.getPassword()).isEqualTo(kwonUser.getPassword());
        // kwonUser2 픽스쳐는 save 안되었으므로 Password 를 암호화(BCrypt)하여 한다.
        // 하지만 salt 값을 사용하는 BCrypt 는 매번 같은 암호화 값이 나오지 않으므로 이때 비교하면 False 다. (assertFalse)
        then(selectedUser.getPassword()).isNotEqualTo(passwordEncoder.encode(kwonUser.getPassword()));
        // .match() 함수를 이용하면 암호화된 값의 salt 값을 이용하여 평문을 같은 salt 로 암호화할 수 있다.
        // 이때 비교하면 같은 salt 로 똑같이 암호화했으므로 True 가 된다. (assertTrue)
        assertTrue(passwordEncoder.matches(kwonUser2.getPassword(), selectedUser.getPassword()));

    }

    // junit 4 예외 테스트 방법
    //@Test(expected = IllegalStateException.class)
    @Test
    public void 중복_회원_예외_테스트() throws Exception {
        // 두 픽스쳐는 loginId가 같아서 IllegalStateException 예외가 발생한다.
        userService.join(kwonUser2);
        // junit 5 예외테스트 Executable executable 인자
        assertThrows(IllegalStateException.class, () -> userService.join(kwonUser3));
    }

    @Test
    public void QueryDSL_Persistence_테스트() {
        // QueryDSL 과 쿼리타입(Q)를 테스트한다.
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction(); // @SpringBootTest 트랜잭션 기능 획득

        try {
            //tx.begin(); //트랜잭션 시작 -> @Transactional 포함
            em.persist(kwonUser);
            em.persist(kwonUser3);

            // queryDSL 3.x 버전
            // JPAQuery query = new JPAQuery(em);
            // QUser qUser = new QUser("m"); // 생성되는 JPQL 의 별칭이 m
            // List<User> users =
            //         query.from(qUser)
            //                 .where(qUser.mbrLoginId.eq("ksgkms24"))
            //                 .orderBy(qUser.mbrLoginId.desc())
            //                 .list(qUser);
            // queryDSL 4.x 버전
            JPAQueryFactory query = new JPAQueryFactory(em);
            QUser qUser = new QUser("m"); // 생성되는 JPQL 의 별칭이 m
            List<User> users =
                    query.select(qUser)
                            .from(qUser)
                            .where(qUser.mbrLoginId.eq("ksgkms24"))
                            .orderBy(qUser.mbrLoginId.desc())
                            .fetch();

            then(users.get(0).getMbrLoginId()).isEqualTo("ksgkms24");
            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }
    }
}