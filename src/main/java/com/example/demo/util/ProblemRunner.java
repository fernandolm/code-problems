package com.example.demo.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UtilityClass
public class ProblemRunner {
    private final String TEST_METHOD_NAME = "test";

    public void run(String fullClassName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> problemClass = Class.forName(fullClassName);

        Method methodTest = problemClass.getMethod(TEST_METHOD_NAME, null);
        Constructor<?> constructor = problemClass.getDeclaredConstructor();
        methodTest.invoke(constructor.newInstance(), null);
    }
}
