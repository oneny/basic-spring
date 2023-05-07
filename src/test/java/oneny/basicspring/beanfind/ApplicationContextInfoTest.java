package oneny.basicspring.beanfind;

import oneny.basicspring.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 모든 빈 출력하기
 * ac.getBeanDefinitionNames(): 스프링에 등록된 모든 빈 이름을 조회한다.
 * ac.getBean(): 빈 이름으로 빈 객체(인스턴스)를 조회한다.
 *
 * 애플리케이션 빈 출력하기
 * 스프링 내부에서 사용하는 빈을 제외하고, 내가 등록한 빈만 출력하기 위해서는 getRole()을 이용하여 구분할 수 있다.
 * ROLE_APPLICATION: 일반적으로 사용자가 정의한 빈
 * ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
 */
class ApplicationContextInfoTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAll() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + " object = " + bean);
    }
  }

  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationAll() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);// 빈에 대한 정보들을 가져올 수 있음

      // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
      // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + " object = " + bean);
      }
    }
  }
}
