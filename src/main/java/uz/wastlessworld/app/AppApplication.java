package uz.wastlessworld.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.wastlessworld.app.model.Result;

@SpringBootApplication
public class AppApplication {

	@Bean
	public Result result(){
		return new Result();
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
