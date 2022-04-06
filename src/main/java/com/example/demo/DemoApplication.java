package com.example.demo;

import com.example.demo.interfaces.Test;
import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final String PACKAGE_PROBLEMS = "com.example.demo.problems";
		final String TEST_METHOD_NAME = "test";

		Reflections reflections = new Reflections(PACKAGE_PROBLEMS);

		Set<Class<? extends Test>> allClasses = reflections.getSubTypesOf(Test.class);

		for (Class problemClass : allClasses) {
			Method methodTest = problemClass.getMethod(TEST_METHOD_NAME, null);
			Constructor<?> constructor = problemClass.getDeclaredConstructor();
			methodTest.invoke(constructor.newInstance(), null);
		}
	}
}
