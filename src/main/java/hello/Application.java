package hello;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication adds:
// @Configuration: tag class as source of bean definitions
// @EnableAutoConfiguration: tells Spring Boot to start adding beans based on settings
// @EnableWebMvc (automatically added by Spring Boot when it sees spring-webmvc)
// @ComponentScan: tells Spring to look for other components, configurations, services 
//                 in the package so it finds the controllers
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}