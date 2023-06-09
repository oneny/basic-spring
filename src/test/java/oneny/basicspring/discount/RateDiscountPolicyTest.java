package oneny.basicspring.discount;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RateDiscountPolicyTest {
  RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

  @Test
  @DisplayName("VIP는 10% 할인이 적용되어야 한다")
  public void vip_o() {
    // given
    Member member = new Member(1L, "membertA", Grade.VIP);
    // when
    int discount = discountPolicy.discount(member, 10000);
    // then
    assertThat(discount).isEqualTo(1000);
  }

  @Test
  @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
  public void vip_x() {
    // given
    Member member = new Member(2L, "membertBASIC", Grade.BASIC);
    // when
    int discount = discountPolicy.discount(member, 10000);
    // then
    assertThat(discount).isEqualTo(0);
  }

}
