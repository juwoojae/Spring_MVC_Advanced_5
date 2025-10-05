package hello.exception.servlet;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");//오류 페이지 경로
    }

   // @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        //이렇게 2가지 요청을 넣으면 클라이언트 요청은 물론이고, 오류페이지 요청에서도 필터가 호출된다.
        //아무것도 넣지 않았을때 default 값은 요청하나만 있다
       // filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return filterRegistrationBean;
    }
}
/**
 * 기본적으로 컨트롤러에서 예외를 발생하면 WAS 까지 전파해준후(예외 전파)
 * 그리고, WAS 가 해당 오류에 맞는 오류페이지를 재 요청한다.
 * 이 과정에서 필터, 인터셉터가 한번씩 더 호출될수있어서 , 의도와는 다르게 실행이 될수있는데, 이것을 막는법
 * 필터는 DispatchType 으로 중복 호출 제거 ( dispatchType=REQUEST )
 * 인터셉터는 경로 정보로 중복 호출 제거( excludePathPatterns("/error-page/**") )
 */