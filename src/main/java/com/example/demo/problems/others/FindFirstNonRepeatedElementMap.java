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
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindFirstNonRepeatedElementMap implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<String, Integer> testCases;

    public FindFirstNonRepeatedElementMap() {
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
            findFirstNonRepeatedElementMap(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int findFirstNonRepeatedElementMap(String items, Integer answerIndex, MutableBoolean result) {
        Printer.print(MessageFormat.format("Items: {0} | Index: {1}", items, answerIndex));

        if(items.equals(StringUtils.EMPTY))
        {
            printResults(-1, answerIndex, result);
            return -1;
        }

        if(items.length() == 1)
        {
            printResults(0, answerIndex, result);
            return 0;
        }

        int index = -1;

        //source: https://stackoverflow.com/questions/39529831/how-to-find-first-repeated-and-non-repeated-character-in-a-string-using-java8
        Map<Character, Long> itemsMap = items.chars()
                .mapToObj(i -> (char)i)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));

        Optional<Character> firstNonRepeat = itemsMap.entrySet().stream()
                .filter((e) -> e.getValue() == 1)
                .map(e -> e.getKey())
                .findFirst();

        if(firstNonRepeat.isPresent()) {
            index = items.chars()
                    .mapToObj(i -> (char)i)
                    .collect(Collectors.toList())
                    .indexOf(firstNonRepeat.get());
        }

        printResults(index, answerIndex, result);

        return index;
    }
}
