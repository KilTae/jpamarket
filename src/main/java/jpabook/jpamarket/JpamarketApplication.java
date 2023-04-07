package jpabook.jpamarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpamarketApplication {
	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("adf");
		String data = hello.getData();
		System.out.println("data ="+ data);

		SpringApplication.run(JpamarketApplication.class, args);
	}

}
