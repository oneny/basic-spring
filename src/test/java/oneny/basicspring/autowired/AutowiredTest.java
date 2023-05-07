package oneny.basicspring.autowired;

import oneny.basicspring.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

  @Test
  void AutowiredOption() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

  }

  /**
   * 자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.
   * "@Autowired(required=false)": 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
   * org.springframework.lang.@Nullable: 자동 주입할 대상이 없으면 null이 입력된다.
   * Optional<>: 자동 주입할 대상이 없으면 Optional.empty가 입력된다.
   */
  static class TestBean {

    // Member는 스프링 빈이 아니다.
    // 호출 안됨
    @Autowired(required = false)
    public void setNoBean1(Member member) {
      System.out.println("setNoBean1 = " + member);
    }

    // null 호출
    @Autowired
    public void setNoBean2(@Nullable Member member) {
      System.out.println("setNoBean1 = " + member);
    }

    // OPtional.empty 호출
    @Autowired(required = false)
    public void setNoBean1(Optional<Member> member) {
      System.out.println("setNoBean1 = " + member);
    }
  }
}
