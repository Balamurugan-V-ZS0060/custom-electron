package com.zuci.electron.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@SpringBootApplication
//@EnableCaching
public class Application implements CommandLineRunner {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final RedisUserRepository userRepository;

	@Autowired
	public Application(RedisUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {

		//Populating embedded database here
		LOG.info("Saving users. Current user count is {}.", userRepository.count());
		RedisUser shubham = new RedisUser("Shubham", 2000);
		RedisUser pankaj = new RedisUser("Pankaj", 29000);
		RedisUser lewis = new RedisUser("Lewis", 550);

		userRepository.save(shubham);
		userRepository.save(pankaj);
		userRepository.save(lewis);
		LOG.info("Done saving users. Data: {}.", userRepository.findAll());
	}
}
