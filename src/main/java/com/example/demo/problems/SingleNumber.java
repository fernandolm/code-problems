//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/549/
package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.text.MessageFormat;
import java.util.*;

public class SingleNumber implements Test {
    private final Map<int[], Integer> testCases = new HashMap<>() {{
        put(new int[]{2,2,1}, 1);
        put(new int[]{4,1,2,1,2}, 4);
        put(new int[]{1}, 1);
    }};

    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (var sentence : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            singleNumber(sentence.getKey(),
                    sentence.getValue(),
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

        Printer.print(MessageFormat.format("Single Number found: {0} | {1}", singleNumber, (singleNumber == answer)));
        result.setValue(singleNumber == answer);

        return singleNumber;
    }
}
