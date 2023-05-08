package oneny.basicspring.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * "@Scope(value = "request")"를 사용해서 request 스코프로 지정
 * 이제 이 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

  private String uuid;
  private String requestURL;

  // requestURL은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받는다.
  public void setRequestURL(String requestURL) {
    this.requestURL = requestURL;
  }

  public void log(String message) {
    System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
  }

  /**
   * 이 빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드를 사용해서 uuid를 생성해서 저장해둔다.
   * 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분할 수 있다.
   */
  @PostConstruct
  public void init() {
    uuid = UUID.randomUUID().toString();
    System.out.println("[" + uuid + "] request scope bean create: " + this);
  }

  @PreDestroy
  public void close() {
    System.out.println("[" + uuid + "] request scope bean close: " + this);
  }
}
