package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ErrorPageController {
    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception"; //예외 404 같은 경우는 exception 이 발생한 것은 아니다.
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type"; //예외 타입 404 같은 경우는 exception 이 발생한 것은 아니다.
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message"; //오류 메세지
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri"; //클라이언트 요청URI
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name"; //오류가 발생한 서블릿 이름
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code"; //HTTP 상태 코드

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex= ", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}
/**
 * DispatcherType
 * REQUEST : 클라이언트 요청
 * ERROR : 오류 요청
 * FORWARD : MVC 에서 다른 서블릿이나 JSP 결과를 포함할때 RequestDispatcher.forward(request, response);
 * INCLUDE : 서블릿에서 다른 서블릿이나 JSP 의 결과를 포함할때 RequestDispatcher.include(request, response);
 * ASYNC : 서블릿 비동기 호출
 */