//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/727/
package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.text.MessageFormat;
import java.util.*;

public class RemoveDuplicatesFromSortedArray implements Test {
    private final Map<int[], int[]> testCases = new HashMap<>() {{
        put(new int[]{1,1,2}, new int[]{1,2});
        put(new int[]{0,0,1,1,1,2,2,3,3,4}, new int[]{0,1,2,3,4});
    }};

    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (var sentence : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            removeDuplicates(sentence.getKey(), sentence.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int removeDuplicates(int[] duplicatedArray, int[] answerArray, MutableBoolean result) {
        Printer.print(MessageFormat.format("Duplicated Array: {0} | Answer Array: {1}", Arrays.toString(duplicatedArray), Arrays.toString(answerArray)));

        List<Integer> distinctList = new ArrayList<>();

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

        Printer.print(MessageFormat.format("Answer Array: {0} | {1}", Arrays.toString(distinctArray), (Arrays.toString(distinctArray).equals(Arrays.toString(answerArray)))));
        Printer.print(MessageFormat.format("Answer Count: {0} | {1}", distinctArray.length, (distinctArray.length == answerArray.length)));

        result.setValue(distinctArray.length == answerArray.length && (Arrays.toString(distinctArray).equals(Arrays.toString(answerArray))));

        return distinctArray.length;
    }
}
