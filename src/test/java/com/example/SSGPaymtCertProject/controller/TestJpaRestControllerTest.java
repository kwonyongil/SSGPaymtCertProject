package com.example.SSGPaymtCertProject.controller;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @since 2021. 08. 25
 * @author kwon-yong-il
 *         <h2>테스트 클래스 템플릿 목적</h2>
 *         <h3>통합 테스트</h3>
 *         <p>단위 테스트와 같이 기능 검증을 위한 것이 아닌 전체적으로 플로우가 제대로 동작하는지 검증
 *         <p>장점: 애플리케이션의 설정, 모든 Bean을 모두 로드하기 때문에 운영환경과 가장 유사한 테스트가 가능</p>
 *         <p>단점: 애플리케이션의 설정, 모든 Bean을 모두 로드하기 때문에 시간이 오래걸리고 무겁다.
 *         <h3>@SpringBootTest</h3>
 *         <p>@SpringBootTest 어노테이션을 통해 스프링부트 어플리케이션 테스트에 필요한 거의 모든 의존성을 제공한다.</p>
 *         <h3>properties</h3>
 *         <p>test/resources/ 폴더에 properties 파일 또는 yml 파일이 없으면 자동으로 main/resources/ 폴더에 있는 properties 또는 yml 파일을 참조, 속성 추가 가능 </p>
 */
//@RunWith(SpringRunner.class) => Junit4 사용시 추가, Junit5 사용시 생략.
@SpringBootTest(
        properties = {
                "spring.config.location=" + "classpath:application-test.yml",
                "propertyTest.value=propertyTest",
                "testValue=test" }
)
public class TestJpaRestControllerTest {
}
