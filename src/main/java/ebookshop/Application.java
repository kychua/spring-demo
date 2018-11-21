package ebookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// @SpringBootApplication adds:
//     @Configuration: tag class as source of bean definitions
//     @EnableAutoConfiguration: tells Spring Boot to start adding beans based on settings
//     @EnableWebMvc
//     @ComponentScan: tells Spring to look for other components, configurations, services
//                     in the package so it finds the controllers
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
// extending SpringBootServletInitializer (which implements WebApplicationInitializer)
// is needed only for WAR deployment.

	// for generating .war files
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	// used for JAR deployment
    public static void main(String[] args) {
        // bootstraps and launches Spring application, including:
        //   * creating an appropriate ApplicationContext instance
        //   * loading singleton beans
        // see: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/SpringApplication.html
        SpringApplication.run(Application.class, args);
    }

}