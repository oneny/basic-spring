package oneny.basicspring.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.context.annotation.ComponentScan.Filter;

public class ComponentFilterAppConfigTest {

  @Test
  void filterScan() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
    BeanA beanA = ac.getBean("beanA", BeanA.class);

    // includeFilters에 MyIncludeComponent에 어노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.
    // excludeFilters에 MyExcludeComponent에 어노테이션을 추가해서 BeanB가 스프링 빈에 등록되지 않는다.
    assertAll(
            () -> assertThat(beanA).isNotNull(),
            () -> assertThatThrownBy(() -> ac.getBean("beanB", BeanB.class)).isInstanceOf(NoSuchBeanDefinitionException.class));
  }

  @Configuration
  @ComponentScan(
          includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
          excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
  )
  static class ComponentFilterAppConfig {
  }
}
