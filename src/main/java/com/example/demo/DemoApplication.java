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

//		Integer[] A = {1, 3, 6, 4, 1, 2};
//		//Integer[] A = {0, -1, -3};
//		Arrays.sort(A);
//
//		//System.out.println(Arrays.toString(A));
//
////		List<Integer> listA = Arrays.asList(A)
////				.stream()
////				.distinct()
////				.filter(v -> v > 0)
////				.collect(Collectors.toList());
//
//		List<Integer> listA = Arrays.asList(A);
//
//		Optional<Integer> answer = listA
//			.stream()
//			.reduce((firstElement, secondElement) -> secondElement > firstElement ? secondElement : firstElement)
//			.filter(v -> v > 0);
//
//		if(answer.isPresent()){
//			System.out.println("The answer is " + answer.get());
//		}
//		else {
//			System.out.println("The answer is 1");
//		}
//
////		if (listA.size() == 0) {
////			System.out.println("The answer is 1");
////		}
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
