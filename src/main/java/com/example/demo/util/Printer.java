package com.example.demo.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Printer {
    private final String SEPARATOR_CHARACTER = "-";
    private final int SEPARATOR_NUMBER_OF_REPETITION = 80;
    private final String SEPARATOR = StringUtils.repeat(SEPARATOR_CHARACTER, SEPARATOR_NUMBER_OF_REPETITION);

    private StopWatch stopWatch;

    NumberFormat percent2DigitsFormat = NumberFormat.getPercentInstance();

    static {
        percent2DigitsFormat.setMaximumFractionDigits(2);
    }

    public void printBegin(String testName) {
        stopWatch = new StopWatch();
        stopWatch.start();

        print("\n");
        print(SEPARATOR);
        print("BEGIN >>>>> " + testName);
    }

    public void printEnd(String testName) {
        stopWatch.stop();
        stopWatch.prettyPrint();

        print(MessageFormat.format("END >>>>> {0} | {1} miliseconds", testName, stopWatch.getTotalTimeMillis()));
        print(SEPARATOR);
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void printSuccess(List<Boolean> results) {
        BigDecimal totalSuccessResults = BigDecimal.valueOf(results.stream().filter(r -> r == true).collect(Collectors.toList()).size());
        BigDecimal totalResults = BigDecimal.valueOf(results.size());

        BigDecimal resultPercentage = BigDecimal.valueOf(totalSuccessResults.doubleValue() / totalResults.doubleValue());
        print(MessageFormat.format("Success: {0}", percent2DigitsFormat.format(resultPercentage.doubleValue())));
    }

    public void printFail(List<Boolean> results) {
        BigDecimal totalFailResults = BigDecimal.valueOf(results.stream().filter(r -> r == false).collect(Collectors.toList()).size());
        BigDecimal totalResults = BigDecimal.valueOf(results.size());

        BigDecimal resultPercentage = BigDecimal.valueOf(totalFailResults.doubleValue() / totalResults.doubleValue());
        print(MessageFormat.format("Fail: {0}", percent2DigitsFormat.format(resultPercentage.doubleValue())));
    }
}
