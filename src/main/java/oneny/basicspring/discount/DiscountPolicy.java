package oneny.basicspring.discount;

import oneny.basicspring.member.Member;

public interface DiscountPolicy {

  /**
   * @return 할인 대상 금
   */
  int discount(Member member, int price);
}
