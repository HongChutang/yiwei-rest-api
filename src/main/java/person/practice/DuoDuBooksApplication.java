package person.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import person.practice.filter.BearerAuthorizeFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DuoDuBooksApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DuoDuBooksApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DuoDuBooksApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilterRegistrationBean(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        BearerAuthorizeFilter httpBearerFilter = new BearerAuthorizeFilter();
		registrationBean.setFilter(httpBearerFilter);
		List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/user/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
}
