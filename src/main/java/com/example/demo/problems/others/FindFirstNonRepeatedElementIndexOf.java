package com.example.demo.problems.others;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class FindFirstNonRepeatedElementIndexOf implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<String, Integer> testCases;

    public FindFirstNonRepeatedElementIndexOf() {
        testCases = new HashMap();
        testCases.put("", -1);
        testCases.put("1", 0);
        testCases.put("aabcbbd", 3);
        testCases.put("aabcababad", 3);
        testCases.put("111222334445", 11);
        testCases.put("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112", 103);
        testCases.put("12222222222222222222222222222", 0);
        testCases.put("122222222222222222222222222223", 0);
        testCases.put("123", 0);
        testCases.put("122222333", 0);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList();

        for (Map.Entry<String, Integer> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            findFirstNonRepeatedElementIndexOf(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int findFirstNonRepeatedElementIndexOf(String items, Integer answerIndex, MutableBoolean result) {
        Printer.print(MessageFormat.format("Items: {0} | Index: {1}", items, answerIndex));

        int index = -1;

        String itemsCopy = items;

        while(items.length() > 0) {
            char item = items.charAt(0);
            int indexSearch = items.indexOf(item, (1 >= items.length() ? items.length() - 1 : 1));

            if(indexSearch == -1 || items.length() == 1) {
                index = itemsCopy.indexOf(item);
                break;
            }
            else {
                items = items.replaceAll(String.valueOf(item), StringUtils.EMPTY);
            }
        }

        printResults(index, answerIndex, result);

        return index;
    }
}
