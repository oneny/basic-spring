package oneny.basicspring;

import oneny.basicspring.member.MemberRepository;
import oneny.basicspring.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // 설정 정보가 없어도 자동으로 스프링 빈을 등록하도록 하기 위해 사용
        // 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 때문에, AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다.
        // 그래서 excludeFilters를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다. 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하기 위해서 이 방법을 선택.
        // @COnfiguration이 컴포넌트 스캔의 대상이 되는 이유는 @Configuration 소스코드를 열어보면 @Component 에노테이션이 붙어있기 때문이다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
        basePackages = "oneny.basicspring.member" // 탐색할 패키지의 시작 위치를 지정할 수 있다. 여러 개 둘 수도 있다.
        // 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것을 권장한다. 최근 스프링 부트도 이 방법을 기본으로 제공한다.
)
public class AutoAppConfig {
  // 컴포넌트 스캔은 이름 그대로 @Component 어노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
  // 따라서 MemoryMemberRepository, RateDiscountPolicy, MemberServiceImpl, OrderServiceImpl에 @Component 어노테이션을 붙여줘야 한다.

  // 자동 빈 등록 vs 자동 빈 등록 -> ConflictingBeanDefinitionException 예외 발생
  // 자동 빈 등록 vs 수동 빈 등록 -> 자동 빈 등록이 우선권을 가진다.(수동 빈이 자동 빈을 오버라이딩 해버린다.)
    // Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing
  // 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본값을 바꾸었다.
    // 여러 설정들이 꼬여서 정말 잡기 어려운 버그가 발생할 수 있기 때문이다.
  @Bean(name = "memoryMemberRepository")
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
}
