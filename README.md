# basic-spring

### 단축키

- `command + option + v` - 반환되는 객체를 담을 변수를 생성해 줌 
- `soutv` - System.out.println 단축키
- `command + shift + T` - Crate New Test...
- `command + E` - 과거 히스토리 내역을 볼 수 있음
- `command + option + V` - return 하는 객체를 담을 변수를 자동으로 생성할 수 있음
- `command + option + M` - Extract Method
- `command + O` - 클래스 검색
- `command + shift + enter` - 다음 줄로 커서 이동
- `iter` - for 문 만들어 줌
- `command + option + N` - 인라인으로 합치기

## 주요 정리

### 스프링 컨테이너(섹션 3 - 스프링 핵심 원리 이해2 - 객체 지향 원리 적용)

> 참고   
> 스프링은 빈을 생성하고, 의존관계를 주입하는 단계가 나누어져 있다. 그런데 이렇게 자바코드로 스프링 빈을 등록하면 생성자를 호출하면서 의존관계 주입도 한번에 처리된다.
> 자세한 내용은 의존관계 자동 주입에서 다시 설명

```java
public class OrderApp {

  public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
//    OrderService orderService = appConfig.orderService();

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 20000);

    System.out.println("order = " + order);
  }
}
```

- `ApplicationContext`를 스프링 컨테이너라 한다.
- 기존에는 개발자가 `AppConfig`를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터는 스프링 컨테이너를 통해서 사용한다.
- 스프링 컨테이너는 `@Configuration`이 붙은 `AppConfig`를 설정(구성) 정보로 사용한다. 여기서 `@Bean`이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
  - 이렇게 스프링 컨테이너에 등록된 객체를 **스프링 빈**이라 한다.
- 스프링 빈은 `@Bean`이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. (`memberService`, `orderService`)
- 이전에는 개발자가 필요한 객체를 `AppConfig`를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다.
  - 스프링 빈은 `applicationContext.getBean()` 메서드를 사용해서 찾을 수 있다.
- 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.

### 스프링 빈 설정 메타 정보 - BeanDefinition(섹션 4 - 스프링 컨테이너와 스프링 빈)

- 스프링은 어떻게 다양한 설정 형식(자바 코드, xml 등)을 지원할 수 있을까?
  - **역할과 구현을 개념적으로 나눈 것**이다!
- 스프링 컨테이너는 자바 코드인지, XML인지 몰라도 오직 BeanDefinition만 알면 된다.
  - XML을 읽어서 BeanDefinition을 만들면 된다.
  - 자바 코드를 읽어서 BeanDefinition을 만들면 된다.
  - 즉, 스프링 컨테이너가 설계 자체를 추상화에만 의존하도록 설계했기 때문에 가능하다.
- `BeanDefinition`을 빈 설정 메타정보라 한다.
  - `@Bean`, `<bean>` 당 각각 하나씩 메타 정보가 생성된다.
- 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성한다.

#### 좀 더 깊이있게

<img width="823" alt="스크린샷 2023-05-07 오후 4 00 32" src="https://user-images.githubusercontent.com/97153666/236662856-1334f2ce-0f50-4e6e-b2cc-89cbf64db426.png">

- `AnnotationConfigApplicationContext`는 `AnnotatedBeanDefinitionReader`를 사용해서 `AppConfig.class`를 읽고 `BeanDefinition`을 생성한다.
- 새로운 형식의 설정 정보가 추가되면, `XxxBeanDefinitionReader`를 만ㄷ르어서 `BeanDefinition`을 생성하면 된다.
- `BeanDefinition`의 정보에 대한 자세한 설명은 `oneny.basicspring.beanfind.BeanDefinitionTest`을 확인하자.

### 싱글톤 방식의 주의점(섹션 5 - 싱글톤 컨테이너)

- 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안된다.
- 무상태(stateless)로 설계해야 한다.
  - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
  - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으며 안된다!
  - 가급적 읽기만 가능해야 한다.
  - 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
- **스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다!!!!!**
  - `oneny.basicspring.singleton.StatefulServiceTest` 참고

### @Configuration과 바이트 조작(섹션 5 - 싱글톤 컨테이너)

```java
class ConfigurationSingletonTest {
  
  @Test
  void configurationDeep(){
    ApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);

    // AppConfig도 스프링 빈으로 등록된다.
    AppConfig bean=ac.getBean(AppConfig.class);

    System.out.println("bean = " + bean.getClass());
    // 출력: bean = class oneny.basicspring.AppConfig$$SpringCGLIB$$0
  }
}
```

- AppConfig 스프링 빈을 조회해서 클래 정보를 출력하면 `class oneny.basicspring.AppConfig$$SpringCGLIB$$0`로 출력된다.
  - 만약 AppConfig의 `@Configuration` 어노테이션을 제거하면 순수한 클래스 `class oneny.basicspring.AppConfig`가 출력된다.
  - `xxxCGLIB`가 붙으면 개발자가 만든 클래스가 아닌 스프링이 `CGLIB`라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!

#### AppConfig@CGLIB 예상 코드

```java
@Bean
public MemberRepository memberRepository() {
  
  if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
    return 스프링 컨테이너에서 찾아서 반환;
  } else { // 스프링 컨테이너에 없으면
    기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
    return 반환
  }
}
```

- `@Bean`이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고,
  - 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
- 덕분에 싱글톤이 보장되는 것이다.

#### AppConfig에 @Configuration을 적용하지 않으면?

- 순수한 클래스 `class oneny.basicspring.AppConfig`가 반환되어 출력된다.
- `@Configuration`을 적용하지 않고 `@Bean`만 적용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.
- 크게 고민할 것없이 스프링 설정 정보는 항상 `@Configuration`을 사용하자.


### 컴포넌트 스캔과 자동 의존관계 주입 동작 방식

#### @ComponentScan

```java
@Component
public class MemberServiceImpl implements MemberService {}
```

- `@ComponentScan`은 `@Component`가 붙은 모든 클래스를 스프링 빈으로 등록한다.
- 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
  - 빈 이름 기본 전략: MemberServiceImpl 클래스 -> memberServiceImpl
  - 빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2") 이런식으로 이름을 부여하면 된다.

#### @Autowired

```java
@Component
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;

  @Autowired // 자동으로 ac.getBean(MemberRepository.class)로 의존관계를 자동으로 주입해준다고 보면 된다.
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  
  // ...
}
```

- 생성자에 `@Autowired`를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
- 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
  - `getBean(MemberRepository.class)`와 동일하다고 이해하면 된다.

#### 컴포넌트 스캔 대상

- 컴포넌트의 스캔의 용도 뿐만 아니라 다음 어노테이션이 있으면 스프링은 부가 기능을 수행한다.
  - `@Compnent`: 컴포넌트 스캔에서 사용
  - `@Controller`: 스프링 MVC 컨트롤러에서 사용
    - 스프링 MVC 컨트롤러로 인식
  - `@Service`: 스프링 비즈니스 로직에서 사용
    - 특별한 처리는 하지 않지만, 대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나라고 비즈니스 게층을 인식하는데 도움이 된다.
  - `@Repository`: 스프링 데이터 접근 계층에서 사용
    - 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
  - `@Configuration`: 스프링 설정 정보에서 사용
    - 앞에서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
- `@Controller`, `@Service` 등의 내부 소스코드를 살펴보면 `@Component` 어노테이션이 적용되어 있는 것을 확인할 수 있다.


### 자동, 수동의 올바른 실무 운영 기준(섹션 7 - 의존관계 자동 주입)

#### 편리한 자동 기능을 기본으로 사용하자

그러면 어떤 경우에 컴포넌트 스캔과 자동 주입을 사용하고, 어떤 경우에 설정 정보를 통해서 수동으로 빈을 등록하고, 의존관계도 수동으로 주입해야 할까?   
결론부터 이야기하면, 스프링이 나오고 시간이 갈 수록 점점 자동을 선호하는 추세다. 스프링은 @Component 뿐만 아니라 @Controller , @Service , @Repository 처럼 계층에 맞추어 일반적인 애플리케이션 로직을 자동으로 스캔할 수 있도록 지원한다. 거기에 더해서 최근 스프링 부트는 컴포넌트 스캔을 기본으로 사용하고, 스프링 부트의 다양한 스프링 빈들도 조건이 맞으면 자동으로 등록하도록
설계했다.    
설정 정보를 기반으로 애플리케이션을 구성하는 부분과 실제 동작하는 부분을 명확하게 나누는 것이 이상적이지만, 개발자 입장에서 스프링 빈을 하나 등록할 때 @Component 만 넣어주면 끝나는 일을 @Configuration 설정 정보에 가서 @Bean 을 적고, 객체를 생성하고, 주입할 대상을 일일이 적어주는 과정은 상당히 번거롭다.
또 관리할 빈이 많아서 설정 정보가 커지면 설정 정보를 관리하는 것 자체가 부담이 된다. 그리고 결정적으로 자동 빈 등록을 사용해도 OCP, DIP를 지킬 수 있다.

#### 그러면 수동 빈 등록은 언제 사용하면 좋을까?

- 애플리케이션은 크게 업무 로직과 기술 지원 로직으로 나눌 수 있다.    
  - 업무 로직 빈: 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는 리포지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경된다.
  - 기술 지원 빈: 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용된다. 데이터베이스 연결이나, 공통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.
- 업무 로직은 숫자도 매우 많고, 한번 개발해야 하면 컨트롤러, 서비스, 리포지토리 처럼 어느정도 유사한 패턴이 있다. 이런 경우 자동 기능을 적극 사용하는 것이 좋다. 보통 문제가 발생해도 어떤 곳에서 문제가 발생했는지 명확하게 파악하기 쉽다.
- 기술 지원 로직은 업무 로직과 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게 영향을 미친다. 그리고 업무 로직은 문제가 발생했을 때 어디가 문제인지 명확하게 잘 드러나지만, 기술 지원 로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많다. 그래서 이런 기술 지원 로직들은 가급적 수동 빈 등록을 사용해서 명확하게 드러내는 것이 좋다.
애플리케이션에 광범위하게 영향을 미치는 기술 지원 객체는 수동 빈으로 등록해서 딱! 설정 정보에 바로 나타나게 하는 것이 유지보수 하기 좋다.

### 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.(섹션 8 - 빈 생명 주기 콜백)

- 인터페이스(InitializingBean, DisposableBean)
- 설정 정보에 초기화 메서드, 종료 메서드 지정
- `@PostConstruct`, `@PreDestroy` 어노테이션 지원
- `test.oneny.basicspring.lifecycle` 참고

### 빈 스코프

#### 싱글톤

기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프

#### 프로토타입

스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프이다.

- **핵심은 스프링 컨테이너는 프로토타입 빈을 생성하고, 의존관계 주입, 초기화까지만 처리한다**는 것이다.
- 클라이언트에 빈을 반환하고, 이후 스프링 컨테이너는 생성된 프로토타입 빈을 관리하지 않는다.
- 프로토타입 빈을 관리할 책임은 프로토타입 빈을 받은 클라이언트에 있다.
- 그래서 `@PreDestroy`같은 종료 메서드가 호출되지 않는다.
- 필요할 때마다 객체를 항상 새로 생성해야 하는데, 의존관계 주입이 필요하다면 이때 프로토타입 빈을 사용하는데 실무에서는 프로토타입 빈이 잘 활용되지는 않는다.
