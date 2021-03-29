package net.xdclass.online_xdclass.config;

import net.xdclass.online_xdclass.interceptor.CorsInterceptor;
import net.xdclass.online_xdclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    //拦截全部路径，这个跨域了，要放在最上面
    registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");

    registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/v1/*/**")
        .excludePathPatterns("/api/v1/pri/user/login", "/api/v1/pri/user/register",
            "/api/v1/pub/video/all");

    WebMvcConfigurer.super.addInterceptors(registry);
  }
}
