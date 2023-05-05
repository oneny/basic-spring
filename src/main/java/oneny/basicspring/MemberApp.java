package oneny.basicspring;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberService;
import oneny.basicspring.member.MemberServiceImpl;

public class MemberApp {

  public static void main(String[] args) {
    MemberService memberService = new MemberServiceImpl();
    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("member = " + member.getName());
    System.out.println("findMember = " + findMember.getName());

  }
}
