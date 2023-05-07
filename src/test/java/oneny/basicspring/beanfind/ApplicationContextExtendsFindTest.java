package oneny.basicspring.beanfind;

import oneny.basicspring.discount.DiscountPolicy;
import oneny.basicspring.discount.FixDiscountPolicy;
import oneny.basicspring.discount.RateDiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 *  부모 타입으로 조회하면, 자식 타입도 함께 조회한다.
 *  그래서 모든 자바 객체의 최고 부모님 Object 타입으로 조회하면, 모든 스프링 빈을 조회한다.
 */
public class ApplicationContextExtendsFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 중복 오류가 발생한다")
  void findBeanByParentTypeDuplicate() {
    assertThatThrownBy(() -> ac.getBean(DiscountPolicy.class))
            .isInstanceOf(NoUniqueBeanDefinitionException.class);
  }

  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
  void findBeanByParentTypeBeanName() {
    DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
    assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class); // 구현체로 조회하면 유연성이 떨어질 수 있으니 유의!
  }

  @Test
  @DisplayName("부모 타입으로 모두 조회하기")
  void findAllBeanByParentType() {
    Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);

    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value = " + beansOfType.get(key));
    }
  }

  @Test
  @DisplayName("부모 타입으로 모두 조회하기 - Object")
  void findAllBeanByObjectType() {
    // 자바 객체는 모든 것이 Object 타입이기 때문이다.
    // 따라서 스프링 빈에 등록된 모든 빈이 출력된다.
    Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);

    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + " value = " + beansOfType.get(key));
    }
  }

  @Configuration
  static class TestConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
      return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
      return new FixDiscountPolicy();
    }
  }
}
