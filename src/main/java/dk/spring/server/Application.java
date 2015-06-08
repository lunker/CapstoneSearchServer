package dk.spring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		
//		NetWork nw = new NetWork();
//		nw.start();	
		
//		GoogleNetwork gn = new GoogleNetwork();
//		gn.start();
		
//		SetRating sr = new SetRating();
//		sr.start();
		
		
	}
	
}
