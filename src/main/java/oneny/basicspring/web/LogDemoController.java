package oneny.basicspring.web;

import jakarta.inject.Provider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import oneny.basicspring.common.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

  /**
   * myLogger은 생존 주기가 사용자의 요청이 들어와서 나갈 때까지인데 HTTP request가 들어오기 전에 스프링 컨테이너한테 달라고 한 것이랑 같아 에러가 발생한다.
   */
//  private final ObjectProvider<MyLogger> myLoggerProvider;
  private final LogDemoService logDemoService;
  private final MyLogger myLogger;

  @RequestMapping("log-demo")
  @ResponseBody
  public String logDemo(HttpServletRequest request) {
//    MyLogger myLogger = myLoggerProvider.getObject();
    String requestURL = request.getRequestURL().toString();
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    logDemoService.logic("testId");

    return "OK";
  }
}
