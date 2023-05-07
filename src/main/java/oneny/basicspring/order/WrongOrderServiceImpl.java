package oneny.basicspring.order;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.discount.RateDiscountPolicy;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberRepository;
import oneny.basicspring.member.MemoryMemberRepository;

public class WrongOrderServiceImpl implements OrderService {

  // OCP(확장에는 열려 있으나 변경에는 닫혀 있어야 한다.)와 DIP(역할에 의존해야 한다는 것(추상화에 의존해야지, 구체화에 의존하면 안된다.)를 위반하고 있다.
  // OrderServiceImple이 DiscountPolicy 인터페이스 뿐만 아니라
  // FixDiscountPolicy인 구체 클래스도 함께 의존하고 있다.
  // 아래 코드를 보면 의존하고 있다. -> DIP 위반(둘 다 의존하고 있음)
  // 그래서 FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 소스코드로 함께 변경해야 한다. -> OCP 위
  private final MemberRepository memberRepository = new MemoryMemberRepository();
  //  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
