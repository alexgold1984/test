package com.csv.config;

import com.csv.parser.web.interceptors.MultipartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MultipartConfig implements WebMvcConfigurer {

  private final MultipartInterceptor multipartInterceptor;

  public MultipartConfig(MultipartInterceptor multipartInterceptor) {
    this.multipartInterceptor = multipartInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(multipartInterceptor)
        .addPathPatterns("/api/upload");
  }
}
