package oneny.basicspring.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  // WrongMemberServiceImpl과 달리 해당 파일에 MemoryMemberRepository에 대한 코드가 있나?
  // 없음. 오직 MemberRepository에 대한 인터페이스만 존재한다.
  // 이제 MemberServiceImple은 추상화(인터페이스)에만 의존하게 된다. -> DIP를 지키고 있음

  // 자세한 설명
  // 설계 변경으로 MemberServiceImpl은 MemboryMeberRepository를 의존하지 않는다.
  // 단지 MemberRepository 인터페이스에만 의존한다.
  // MemberServiceImpl의 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
  // MemberServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정된다.
  // MemberServiceImpl은 이제부터 "의존관계에 대한 고민은 외부"에 맡기고 "실행에만 집중"하면 된다.
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }

  // 테스트 용도
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
