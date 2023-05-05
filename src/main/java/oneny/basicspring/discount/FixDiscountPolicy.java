package oneny.basicspring.discount;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

  private int discountFixAmount = 1000;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return discountFixAmount;
    }

    return 0;
  }
}
