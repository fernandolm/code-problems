//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/727/
package com.example.demo.problems.leetcode;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class RemoveDuplicatesFromSortedArray implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], int[]> testCases = new HashMap() {{
        put(new int[]{1,1,2}, new int[]{1,2});
        put(new int[]{0,0,1,1,1,2,2,3,3,4}, new int[]{0,1,2,3,4});
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

        for (Map.Entry<int[], int[]> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            removeDuplicates(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int removeDuplicates(int[] duplicatedArray, int[] answerArray, MutableBoolean result) {
        Printer.print(MessageFormat.format("Duplicated Array: {0} | Answer Array: {1}", Arrays.toString(duplicatedArray), Arrays.toString(answerArray)));

        List<Integer> distinctList = new ArrayList();

        for (int counter = 0 ; counter < duplicatedArray.length;) {
            int finalCounter = counter;
            long totalSameValueItems = Arrays.stream(duplicatedArray).filter(v -> v == duplicatedArray[finalCounter]).count();

            distinctList.add(duplicatedArray[finalCounter]);

            counter += totalSameValueItems;
        }

//        int[] distinctArray = ArrayUtils.toPrimitive(distinctList.toArray(new Integer[0]));
//        for leet code
        int[] distinctArray = new int[distinctList.size()];
        int counterDistinctArray = 0;

        for(int number : distinctList) {
            distinctArray[counterDistinctArray++] = number;
        }

        final boolean IS_TEST_OK = distinctArray.length == answerArray.length && (Arrays.toString(distinctArray).equals(Arrays.toString(answerArray)));

        Printer.print(MessageFormat.format("Answer Array: {0} | {1}",
                Arrays.toString(distinctArray),
                (Arrays.toString(distinctArray).equals(Arrays.toString(answerArray)))));
        Printer.print(MessageFormat.format("Answer Count: {0} | {1}",
                distinctArray.length,
                (distinctArray.length == answerArray.length)));

        result.setValue(IS_TEST_OK);

        return distinctArray.length;
    }
}
