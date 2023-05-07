package oneny.basicspring.singleton;

import oneny.basicspring.AppConfig;
import oneny.basicspring.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 처음에 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때마다 객체를 새로 생성한다.
 * 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! -> 메모리 낭비가 심하다.
 * 해결방안은 해당 객체가 딱 1개가 생성되고, 공유하도록 설계하면 된다. -> 싱글톤 패턴
 */
public class SingletonTest {

  @Test
  @DisplayName("스프링 없는 순수한 DI 컨테이너")
  void pureContainer() {

    AppConfig appConfig = new AppConfig();

    // 1. 조회: 호출할 때마다 객체를 생성
    MemberService memberService1 = appConfig.memberService();

    // 2. 조회: 호출할 때마다 객체를 생성
    MemberService memberService2 = appConfig.memberService();

    // 참조값이 다른 것을 확인
    System.out.println("memberService1 = " + memberService1);
    System.out.println("memberService2 = " + memberService2);

    // memberService1 != memberService2
    assertThat(memberService1).isNotSameAs(memberService2);
  }

  /**
   * 싱글톤 패턴 문제점
   * 1. 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
   * 2. 의존관계상 클라이언트가 구체 클래스에 의존한다. -> DIP를 위반한다.
   * 3. 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
   * 4. 테스트하기 어렵다.
   * 5. 내부 속성을 변경하거나 초기화하기 어렵다.
   * 6. private 생성자로 자식 클래스를 만들기 어렵다.
   * 7. 결론적으로 유연성이 떨어진다.
   * 8. 안티패턴으로 불리기도 한다.
   */
  @Test
  @DisplayName("싱글톤 패턴을 적용한 객체 사용")
  void singletonServiceTest() {

    // 1. 조회: 호출할 때마다 같은 객체를 반환
    SingletonService singletonService1 = SingletonService.getInstance();

    // 1. 조회: 호출할 때마다 같은 객체를 반환
    SingletonService singletonService2 = SingletonService.getInstance();

    // 참조값이 같은 것을 확인
    System.out.println("instance1 = " + singletonService1);
    System.out.println("instance2 = " + singletonService2);

    assertThat(singletonService1).isSameAs(singletonService2);

    singletonService1.login();
  }

  /**
   * 스프링 컨테이너는 싱글톤 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
   * 이러한 기능을 싱글톤 레지스트리라 한다.
   * 스프링 컨테이너의 이런 기능 덕분에 싱글톤 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
   *   - 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
   *   - DIP, OCP, 테스트, private 생성자로부터 자유롭게 싱글톤을 사용할 수 있다.
   * 스프링의 기본 빈 등록 방식은 싱글톤이지만, 요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다. 빈 스코프에서 자세히.
   */
  @Test
  @DisplayName("스프링 컨테이너와 싱글톤")
  void springContainer() {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 1. 조회: 호출할 때마다 객체를 생성
    MemberService memberService1 = ac.getBean("memberService", MemberService.class);

    // 2. 조회: 호출할 때마다 객체를 생성
    MemberService memberService2 = ac.getBean("memberService", MemberService.class);

    // 참조값이 같은 것을 확인
    System.out.println("memberService1 = " + memberService1);
    System.out.println("memberService2 = " + memberService2);

    // memberService1 == memberService2
    assertThat(memberService1).isSameAs(memberService2);
  }
}
