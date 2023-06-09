package oneny.basicspring.beanfind;

import oneny.basicspring.member.MemberRepository;
import oneny.basicspring.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 타입으로 조회 시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생한다. 이때는 빈 이름을 지정해야 한다.
 * "ac.getBeanOfType()"을 사용하면 해당 타입의 모든 빈을 조회할 수 있다.
 */
public class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

  @Test
  @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
  void findBeanByTypeDuplicate() {
    assertThatThrownBy(() -> ac.getBean(MemberRepository.class))
            .isInstanceOf(NoUniqueBeanDefinitionException.class);
  }

  @Test
  @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
  void findBeanByName() {
    MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
    assertThat(memberRepository).isInstanceOf(MemberRepository.class);
  }

  @Test
  @DisplayName("특정 타입으로 모두 조회하기")
  void findAllBeanByType() {
    Map<String, MemberRepository> beanOfType = ac.getBeansOfType(MemberRepository.class);

    for (String key : beanOfType.keySet()) {
      System.out.println("key = " + key + " value = " + beanOfType.get(key));
    }
    System.out.println("beanOfType = " + beanOfType);
    assertThat(beanOfType.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }
  }
}
