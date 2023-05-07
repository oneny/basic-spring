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
