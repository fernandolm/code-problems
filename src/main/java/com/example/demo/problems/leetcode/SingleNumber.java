//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/549/
package com.example.demo.problems.leetcode;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class SingleNumber implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], Integer> testCases = new HashMap() {{
        put(new int[]{2,2,1}, 1);
        put(new int[]{4,1,2,1,2}, 4);
        put(new int[]{1}, 1);
    }};

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList();

        for (Map.Entry<int[], Integer> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            singleNumber(testCase.getKey(),
                    testCase.getValue(),
                    result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int singleNumber(int[] nums, Integer answer, MutableBoolean result) {
        final int TOTAL_FINDINGS_ALLOWED = 1;
        int singleNumber = nums[0];

        Printer.print(MessageFormat.format("Array: {0} | Answer Single Number: {1}", Arrays.toString(nums), answer));

        for (int number : nums) {
            if(Arrays.stream(nums).filter(n -> n == number).count() == TOTAL_FINDINGS_ALLOWED) {
                singleNumber = number;
                break;
            }
        }

        final boolean IS_TEST_OK = singleNumber == answer;

        Printer.print(MessageFormat.format("Single Number found: {0} | {1}",
                singleNumber,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return singleNumber;
    }
}
