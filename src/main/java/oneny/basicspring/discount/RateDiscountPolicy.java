package oneny.basicspring.discount;

import oneny.basicspring.annotation.MainDiscountPolicy;
import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import org.springframework.stereotype.Component;


@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

  private static final int discountPrice = 10;

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return price * discountPrice / 100;
    } else {
      return 0;
    }
  }
}
