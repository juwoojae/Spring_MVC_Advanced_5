package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 에러페이지 연결
 */
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404"); //
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");//런타임예외가 발생하면 errorPageEx 에서 지정한 /error-page/500 가 호출

        factory.addErrorPages(errorPage404, errorPage500 ,errorPageEx);
    }
}

/**
 * 1. 예외 발생 흐름
 * WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
 * 2. sendError
 *  WAS(sendError 호출 기록 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러
 * (response.sendError())
 * 1.RuntimeException 예외가 WAS 까지 전달되면, WAS 는 오류 페이지 정보를 확인한다. (WebServerCustomizer)
 *
 * 1. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
 *  2. WAS `/error-page/500` 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error-page/
 *  500) -> View
 *
 * 그래서 오류가 발생하면 필터가 2번 호출된다. 결국 클라이언트로 부터 발생한 정상요청인지, 이니면 오류페이지를 출력하기 위한
 * 내부 요청인지 구분할수 있어야 한다. 그래서 서블릿은 DispatcherType 이라는 정보를 제공한다. ㅡ
 *
 * 2. 오류 정보 추가
 * WAS 는 오류 페이지가 발생하면 페이지를 찾아서 호출함과 동시에 request 에 attribute 에 오류를 추가해서 넘겨존다
 *
 */