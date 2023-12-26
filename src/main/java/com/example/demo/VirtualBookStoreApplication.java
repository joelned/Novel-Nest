package com.example.demo;

import com.example.demo.Configurations.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class VirtualBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualBookStoreApplication.class, args);
	}

}
