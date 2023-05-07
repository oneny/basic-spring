package oneny.basicspring.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLiftCycleTest {

  @Test
  public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    NetworkClient client = ac.getBean(NetworkClient.class);
    ac.close();
  }

  @Configuration
  static class LifeCycleConfig {

    /**
     * "@PostConstruct", "@PreDestroy" 애노테이션 특징
     * - 최신 스프링에서 가장 권장하는 방법이다.
     * - 애노테이션 하나만 붙이면 되므로 매우 편리하다.
     * - 패키지를 잘보면 "javax.annotation.PostConstruct"이다. 스프링에 종속적인 기술이 아니라 JSR-250이라는 자바 표준이다.
     *  - 따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.
     * - 컴포넌트 스캔과 잘 어울린다.
     * - 외부 라이브러리에는 적용하지 못하기 때문에 외부 라이브러리를 초기화, 종료해야 하면 @Bean의 initMethod, destroyMethod를 사용하자.
     *
     */
    @Bean
    public NetworkClient networkClient() {
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("http://hello-spring.dev");
      return networkClient;
    }


    /**
     * 설정 정보 사용 특징
     * - 메서드 이름을 자유롭게 줄 수 있다.
     * - 스프링 빈이 스프링 코드에 의존하지 않는다.
     * - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
     *
     * 종료 메서드 추론
     * - @Bean의 destroyMethod 속성에는 아주 특별한 기능이 있다.
     * - 라이브러리는 대부분 close, destroy 이라는 이름의 종료 메서드를 사용한다.
     * - @Bean의 destroyMethod는 기본값이 "(inferred)"으로 등록되어 있다.
     * - 이 추론 기능은 close, shutdown 라는 이름의 메서드를 자동으로 호출해준다. 이름 그대로 종료 메서드를 추론해서 호출해준다.
     * - 따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
     * - 추론 기능을 사용하기 싫으면 destroyMethod=""처럼 빈 공백을 지정하면 된다.
     */
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public NetworkClient networkClient() {
//      NetworkClient networkClient = new NetworkClient();
//      networkClient.setUrl("http://hello-spring.dev");
//      return networkClient;
//    }
  }
}
