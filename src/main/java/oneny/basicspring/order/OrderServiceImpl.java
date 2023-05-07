package oneny.basicspring.order;

import oneny.basicspring.annotation.MainDiscountPolicy;
import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

  // 해결방안
  // DIP 위반 -> 추상에만 의존하도록 변경(인터페이스에만 의존)
  // 구현체에 대한 문제 -> 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다.
  // 인터페이스에만 의존한다 -> DIP 지키고 있음. ORderServiceImple의 입장에서는 구현체는 모름.
  private MemberRepository memberRepository;
  private DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 설계와 코드를 변경 -> 구현체는???

// OrderServiceImpleTest를 위해 주석 처리 -> 확인
//  @Autowired
//  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//    System.out.println("discountPolicy = " + discountPolicy);
//    this.discountPolicy = discountPolicy;
//  }
//
//  @Autowired // 생성자 주입에서와 똑같은 의존관계가 주입된다.
//  public void setMemberRepository(MemberRepository memberRepository) {
//    System.out.println("memberRepository = " + memberRepository);
//    this.memberRepository = memberRepository;
//  }

  // 생성자 하나인 경우 @Autowired 생략 가능
  //  @Autowired // 생성자 주입이 먼저 일어난다. -> scan.AutoAppConfigTest의 basicScan 테스트 메서로 확인
  // @RequiredArgsConstructor가 아래 생성자를 만들어 준다.
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    System.out.println("1. OrderServiceImpl.OrderServiceImpl");
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
