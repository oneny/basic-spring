package oneny.basicspring;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberService;
import oneny.basicspring.member.MemberServiceImpl;
import oneny.basicspring.order.Order;
import oneny.basicspring.order.OrderService;
import oneny.basicspring.order.OrderServiceImpl;

public class OrderApp {

  public static void main(String[] args) {
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);

    System.out.println("order = " + order);
  }
}
