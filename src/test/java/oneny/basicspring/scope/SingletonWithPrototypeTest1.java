package oneny.basicspring.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

  @Test
  void prototypeFind() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

    PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
    prototypeBean1.addCount();
    assertThat(prototypeBean1.getCount()).isEqualTo(1);

    PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
    prototypeBean2.addCount();
    assertThat(prototypeBean2.getCount()).isEqualTo(1);
  }

  /**
   * clientBean이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈이다.
   * 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성이 된 것이지, 사용할 때마다 새로 생성되는 것이 아니다!
   * 프로토타입 빈을 주입 시점에만 새로 생성하는 것이 아니라, 사용할때마다 새로 생성해서 사용하는 것을 원하면 그에 대한 해결방법이 있다.
   */
  @Test
  void singletonClientUsePrototype() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);

    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);
  }

  @Scope("singleton")
  static class ClientBean {

//    private final PrototypeBean prototypeBean; // 생성 시점에 주입 -> 그래서 계속 같은 프로토타입 빈을 사용한다.

//    @Autowired
//    public ClientBean(PrototypeBean prototypeBean) {
//      this.prototypeBean = prototypeBean;
//    }

    /**
     * ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존
     * ObjectProvider: ObjectFactory 상속, 옵션, 스트림 처리 등 편의 기능이 많고, 별도의 라이브러리 필요없음, 하지만 스프링에 의존
     */
    @Autowired
    private Provider<PrototypeBean> prototypeBeanProvider;

    public int logic() {
       // getObject()가 호출되면 그때서야 스프링 컨테이너에서 프로토타입 빈을 찾아서 반환해준다.
      PrototypeBean prototypeBean = prototypeBeanProvider.get();
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

  @Scope("prototype")
  static class PrototypeBean {

    private int count = 0;

    public void addCount() {
      count++;
    }

    public int getCount() {
      return count;
    }

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init " + this);
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }
  }
}
