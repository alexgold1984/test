package com.csv.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${springdoc.swagger-ui.server.url}")
  private String serverUrl;

  @Bean
  public OpenAPI customOpenAPI() {

    return new OpenAPI()
        .info(apiInfo())
        .servers(getApiServers());
  }

  private Info apiInfo() {
    String desc = "Test REST API documentation";
    return new Info()
        .title("Test REST API")
        .description(desc)
        .version("1.0");
  }

  private List<Server> getApiServers() {
    return Collections.singletonList(
        new Server().description("test url").url(serverUrl)
    );
  }
}
