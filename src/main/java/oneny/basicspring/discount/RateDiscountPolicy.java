package oneny.basicspring.discount;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

  private int discountPrice = 10;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return price * discountPrice / 100;
    } else {
      return 0;
    }
  }
}
