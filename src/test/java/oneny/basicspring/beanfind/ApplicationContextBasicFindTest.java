package oneny.basicspring.beanfind;

import oneny.basicspring.AppConfig;
import oneny.basicspring.member.MemberService;
import oneny.basicspring.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법
 * ac.getBean(빈이름, 타입)
 * ac.getBean(타입)
 * 조회 대상 스프링 빈이 없으면 예외 발생 -> NoSuchBeanDefinitionException: No bean named 'xxxxx' available
 */
public class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("이름 없이 타입으로만 조회")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class); // 타입이 겹치는 경우가 있어 조심해야 한다.
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("구체 타입으로 조회")
  void findBeanByName2() {
    // 항상 역할과 구현은 구분해야 한다. 그리고 역할에 의존해야지 구현에 의존하면 안된다.
    // 해당 테스트는 구현에 의존한 테스트이기 때문에 좋지 못하다.
    // 구체 타입으로 조회하면 변경 시 유연셩이 떨어진다.
    MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
    assertThat(memberService).isInstanceOf(MemberService.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회X")
  void findBeanByNameX() {
    assertThatThrownBy(() -> ac.getBean("xxxxx", MemberService.class))
            .isInstanceOf(NoSuchBeanDefinitionException.class);
  }
}
