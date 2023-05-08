package oneny.basicspring.web;

import lombok.RequiredArgsConstructor;
import oneny.basicspring.common.MyLogger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

  //  private final ObjectProvider<MyLogger> myLoggerProvider;
  private final MyLogger myLogger;

  public void logic(String id) {
//    MyLogger myLogger = myLoggerProvider.getObject();
    myLogger.log("service id = " + id);
  }
}
