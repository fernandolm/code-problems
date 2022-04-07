//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/578/
package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ContainsDuplicate implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], Boolean> testCases = new HashMap<>() {{
        put(new int[]{1,2,3,1}, true);
        put(new int[]{1,2,3,4}, false);
        put(new int[]{1,1,1,3,3,4,3,2,4,2}, true);
    }};

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (var testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            containsDuplicate(testCase.getKey(),
                    testCase.getValue(),
                    result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private boolean containsDuplicate(int[] nums, boolean answer, MutableBoolean result) {
        boolean containsDuplicatedValues;

        Printer.print(MessageFormat.format("Array to be verified: {0} | Answer: {1}", Arrays.toString(nums), answer));

        var numbers = Arrays.stream(nums).boxed().collect(Collectors.toList());
        containsDuplicatedValues = numbers.stream().distinct().count() != nums.length;

        final boolean IS_TEST_OK = containsDuplicatedValues == answer;

        Printer.print(MessageFormat.format("Result after passing through the method: {0} | {1}",
                containsDuplicatedValues,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return containsDuplicatedValues;
    }
}
