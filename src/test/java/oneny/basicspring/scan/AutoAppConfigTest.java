package oneny.basicspring.scan;

import oneny.basicspring.AutoAppConfig;
import oneny.basicspring.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {

  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    /**
     * "@Component"은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록한다.
     * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
     *   - 빈 이름 기본 전략: MemberServiceImpl 클래스 -> memberServiceImpl
     *   - 빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2") 이런식으로 이름을 부여하면 된다.
     */
    MemberService memberService = ac.getBean("memberServiceImpl", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }
}
