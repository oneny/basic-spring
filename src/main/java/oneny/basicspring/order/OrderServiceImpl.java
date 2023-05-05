package oneny.basicspring.order;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.discount.FixDiscountPolicy;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberRepository;
import oneny.basicspring.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();


  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
