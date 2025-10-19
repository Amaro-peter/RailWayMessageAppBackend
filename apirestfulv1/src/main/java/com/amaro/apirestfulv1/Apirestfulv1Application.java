package com.amaro.apirestfulv1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amaro.apirestfulv1.model.User;
import com.amaro.apirestfulv1.repository.UserRepository;

@SpringBootApplication
public class Apirestfulv1Application implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(Apirestfulv1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("userA@gmail.com");
		user.setPassword("password123");
		userRepository.save(user);

		
		User user2 = new User();
		user2.setUsername("userB@gmail.com");
		user2.setPassword("password456");
		userRepository.save(user2);
	}
}
