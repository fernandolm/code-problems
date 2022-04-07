/*
This is a demo task.
Write a function:

class Solution { public int solution(int[] A); }

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

Given A = [1, 2, 3], the function should return 4.

Given A = [−1, −3], the function should return 1.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].
Copyright 2009–2022 by Codility Limited. All Rights Reserved. Unauthorized copying, publication or disclosure prohibited.
*/

package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.IntStream;

public class SmallestPositiveInteger implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], Integer> testCases = new HashMap<>() {{
        put(new int[]{1,2,3,4,6}, 5);
        put(new int[]{-1,-3}, 1);
        put(new int[]{1,2,3,4,5}, 6);
    }};

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test(){
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (var testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            getSmallestPositiveInteger(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private Integer getSmallestPositiveInteger(int[] numbers, Integer answer, MutableBoolean result){
        Printer.print(MessageFormat.format("Numbers to be tested: [{0}] | Answer: {1}", Arrays.toString(numbers), answer));

        Integer smallestPositiveInteger;

        IntStream intStream = Arrays.stream(numbers);
        OptionalInt optionalInt = intStream.max();
        final int MAX = optionalInt.getAsInt();

        if(MAX > 0 && !Arrays.stream(numbers).anyMatch(v -> v == MAX - 1)) {
            smallestPositiveInteger = MAX - 1;
        }
        else {
            if(MAX <= 0){
                smallestPositiveInteger = MAX + 1 != 0 ? MAX + 1 : 1;
            }
            else {
                smallestPositiveInteger = MAX + 1;
            }
        }

        final boolean IS_TEST_OK = smallestPositiveInteger == answer;

        Printer.print(MessageFormat.format("Answer: Smallest Positive Integer: {0} | Answer: {1} | {2}",
                smallestPositiveInteger,
                answer,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return smallestPositiveInteger;
    }
}
