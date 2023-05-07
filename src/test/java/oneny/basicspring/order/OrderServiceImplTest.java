package oneny.basicspring.order;

import oneny.basicspring.discount.FixDiscountPolicy;
import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberRepository;
import oneny.basicspring.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

  @Test
  void createOrder() {
    // Null Point Exception 에러가 발생한다.
    // memberRepository, discountPolicy 모두 의존관계 주입이 누락되었기 때문이다.
    // 생성자 주입이였다면 바로 컴파일 오류를 발견하고 주입 데이터를 누락한 것을 확인할 수 있다.(IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.)
//    OrderServiceImpl orderService = new OrderServiceImpl();

    MemberRepository memberRepository = new MemoryMemberRepository();
    memberRepository.save(new Member(1L, "name", Grade.VIP));

    OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
    Order order = orderService.createOrder(1L, "itemA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
  }
}
