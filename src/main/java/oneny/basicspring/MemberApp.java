package oneny.basicspring;

import oneny.basicspring.member.Grade;
import oneny.basicspring.member.Member;
import oneny.basicspring.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

  public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();

    // 스프링은 모든 것이 ApplicationContext로 시작한다.(스프링 컨테이너라 생각해도 좋다.)
    // AppConfig.class를 인자로 넘겨주면 AppConfig에 있는 환경설정 정보를 가지고 스프링이 빈으로 등록하고 관리해준다.
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 메서드 이름으로 등록된 빈을 가져오고, 두 번째 인자로 타입을 설정한다.

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("member = " + member.getName());
    System.out.println("findMember = " + findMember.getName());
  }
}
