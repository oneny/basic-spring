package oneny.basicspring.member;

public class MemberServiceImpl implements MemberService {

  // 다른 저장소로 변경할 때 OCP 원칙을 잘 준수하고 있을까?
  // DIP를 잘 지키고 있을까?
  // 의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제점이 있음
  // MemberServiceImple이 MemoryRepository(인터페이스)와 MemoryMemberRepository(구현)까지 모두 의존하고 있다.
  // 변경했을 때 문제가 될 수 있다. 한 마디로 DIP를 위반하고 있음.
  private final MemberRepository memberRepository = new MemoryMemberRepository();

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
