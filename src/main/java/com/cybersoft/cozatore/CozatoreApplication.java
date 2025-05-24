// File main khởi động ứng dụng Spring Boot
package com.cybersoft.cozatore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CozatoreApplication {

	// Hàm main, điểm bắt đầu của ứng dụng
	public static void main(String[] args) {
		SpringApplication.run(CozatoreApplication.class, args);
	}

}
