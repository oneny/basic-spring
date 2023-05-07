package oneny.basicspring;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.discount.RateDiscountPolicy;
import oneny.basicspring.member.MemberService;
import oneny.basicspring.member.MemberServiceImpl;
import oneny.basicspring.member.MemoryMemberRepository;
import oneny.basicspring.order.OrderService;
import oneny.basicspring.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // AppConfig에 설정을 구성한다는 의미
public class AppConfig {

  // 각 메서드에 @Bean 어노테이션을 붙이면 스프링 컨테이너에 스프링 빈으로 등록된다.
  @Bean
  public MemoryMemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository");
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

  @Bean
  public MemberService memberService() {
    System.out.println("call AppConfig.memberService");
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService");
    return new OrderServiceImpl(
            memberRepository(),
            discountPolicy());
  }
}
