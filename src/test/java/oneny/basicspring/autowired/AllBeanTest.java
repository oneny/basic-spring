package oneny.basicspring.autowired;

import oneny.basicspring.AutoAppConfig;
import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

  @Test
  void findAllBean() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

    DiscountService discountService = ac.getBean(DiscountService.class);
    Member member = new Member(1L, "userA", Grade.VIP);
    int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

    assertThat(discountService).isInstanceOf(DiscountService.class);
    assertThat(discountPrice).isEqualTo(1000);

    int rateDiscountPrifce = discountService.discount(member, 20000, "rateDiscountPolicy");
    assertThat(rateDiscountPrifce).isEqualTo(2000);
  }

  static class DiscountService {
    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    /**
     * 로직 분석
     * DiscountService는 Map으로 모든 DiscountPolicy를 주입받는다. 이 때, fixDiscountPolicy, rateDiscountPolicy가 주입된다.
     * discount() 메서드는 discountCode로 "fixDiscountPolicy"가 넘어오면 map에서 fixDiscountPolicy 스프링 빈을 찾아서 실행한다.
     * <p>
     * 주입 분석
     * Map<String, DiscountPolicy>: map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
     * List<DiscountPolicy>: DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
     * 만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다.
     */
    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }

    public int discount(Member member, int price, String discountCode) {
      DiscountPolicy discountPolicy = policyMap.get(discountCode);
      return discountPolicy.discount(member, price);
    }
  }
}
