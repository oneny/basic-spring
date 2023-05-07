package oneny.basicspring;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.discount.RateDiscountPolicy;
import oneny.basicspring.member.MemberService;
import oneny.basicspring.member.MemberServiceImpl;
import oneny.basicspring.member.MemoryMemberRepository;
import oneny.basicspring.order.OrderService;
import oneny.basicspring.order.OrderServiceImpl;

/**
 * AppConfig를 보면 역할과 구현 클래스가 한 눈에 들어온다. 애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다.
 * 애플리케이션 "실행 시점(런타임)"에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서
 * 클라이언트와 서버의 실제 의존관계가 연결되는 것을 "의존관계 주입"이라고 한다.
 * 객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결된다.
 * 의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
 * 의존관계 주입을 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.
 * AppConfig처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을 IoC 컨테이너 또는 DI 컨테이너라 한다.
 * 의존관계 주입에 초점을 맞추어 최근에는 주로 DI 컨테이너라 한다.
 * 또는 어셈블리, 오브젝트 팩토리 등으로 불리기도 한다.
 */
public class NoSpringAppConfig {

  // AppConfig의 중복을 제거하고, 역할에 따른 구현으로 리팩터링
  // new MemoryMemberRepository() 부분이 중복 제거되었다. 이제 MemoryMemberRepository를 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
  private MemoryMemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  private DiscountPolicy discountPolicy() {
//    return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

  // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다.
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(
            memberRepository(),
            discountPolicy());
  }
}
