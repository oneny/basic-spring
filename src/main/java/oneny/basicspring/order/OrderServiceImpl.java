package oneny.basicspring.order;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

  // 해결방안
  // DIP 위반 -> 추상에만 의존하도록 변경(인터페이스에만 의존)
  // 구현체에 대한 문제 -> 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다.
  // 인터페이스에만 의존한다 -> DIP 지키고 있음. ORderServiceImple의 입장에서는 구현체는 모름.
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 설계와 코드를 변경 -> 구현체는???

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
