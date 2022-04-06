//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/646/
package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class RotateArray implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], Map.Entry<Integer, int[]>> testCases = new HashMap<>() {{
        put(new int[]{1,2,3,4,5,6,7}, new SimpleEntry(3, new int[]{5,6,7,1,2,3,4}));
        put(new int[]{-1,-100,3,99}, new SimpleEntry(2, new int[]{3,99,-1,-100}));
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

        for (var sentence : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            Map.Entry<Integer, int[]> rotateNumberAndAnswer = sentence.getValue();
            rotateArray(sentence.getKey(),
                    rotateNumberAndAnswer.getKey(),
                    rotateNumberAndAnswer.getValue(),
                    result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private void rotateArray(int[] nums, int numberOfRotations, int[] answerArray, MutableBoolean result) {
        Printer.print(MessageFormat.format("Array to be rotated: {0} | Answer Array: {1}", Arrays.toString(nums), Arrays.toString(answerArray)));

        int[] rotatedArray = Arrays.copyOf(nums, nums.length);

        for(int counter = 1; counter <= numberOfRotations; counter++) {
            rotatedArray[numberOfRotations - counter] = nums[nums.length - counter];
        }

        for(int counter = numberOfRotations; counter < nums.length; counter++) {
            rotatedArray[counter] = nums[Math.abs(numberOfRotations - counter)];
        }

        Printer.print(MessageFormat.format("Rotated Array: {0} | {1}", Arrays.toString(rotatedArray), (Arrays.toString(rotatedArray).equals(Arrays.toString(answerArray)))));

        result.setValue((Arrays.toString(rotatedArray).equals(Arrays.toString(answerArray))));
    }
}
