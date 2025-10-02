package hello.exception.servlet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx(){ //Exception 의 경우 서버 내부에서 처리할 수 없는 오류가 발생한것 으로 생각해서 500 오류 반환
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException { response.sendError(404, "404 오류!");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
/**
 * 웹 애플리케이션에서의 예외 전파
 * WAS(여기까지 전파) <-필터<-서블릿<-인터셉터<-컨트롤러(예외 발생)
 * Exception 의 경우 무조건 500
 * response.sendError(HTTP 상태 코드, 오류 메시지)
 * 예외 자체가 발생하지는 않고, 서블릿 컨테이너에게 오류가 발생한다는것을 전달할수 있다.
 * 이 메서드를 사용하면 http 상태코드와 오류 메세지도 추가할수 있다
 */