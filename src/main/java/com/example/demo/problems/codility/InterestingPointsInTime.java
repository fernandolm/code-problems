package com.example.demo.problems.codility;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InterestingPointsInTime implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<Map.Entry<String, String>, Integer> testCases = new HashMap() {{
        put(new SimpleEntry("14:15:00", "14:15:00"), 0);
        put(new SimpleEntry("14:15:00", "13:15:00"), 0);
        put(new SimpleEntry("22:22:22", "22:22:23"), 2);
        put(new SimpleEntry("15:15:00", "15:15:12"), 1);
        put(new SimpleEntry("22:22:22", "23:00:00"), 58);
        put(new SimpleEntry("00:00:00", "23:59:59"), 504);
    }};

    private final String TIME_PATTERN = "HH:mm:ss";
    private final String TIME_SEPARATOR = ":";
    private final Integer MAX_INTERESTING_POINTS_IN_TIME = 2;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test(){
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList();

        for (Map.Entry<Map.Entry<String, String>, Integer> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));

            Map.Entry<String, String> timeRange = testCase.getKey();

            getInterestingPointsInTime(timeRange.getKey(), timeRange.getValue(), testCase.getValue(), result);

            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int getInterestingPointsInTime(String initTime, String finalTime, Integer answer, MutableBoolean result) {
        Printer.print(MessageFormat.format("Init Time/Final Time: {0}/{1} | Answer: {2}", initTime, finalTime, answer));

        log.debug("Logging initTime={} and finalTime={} Test", initTime, finalTime);

        int totalInterestingPointsInTime = 0;

        LocalTime parsedInitTime = LocalTime.parse(initTime, DateTimeFormatter.ofPattern(TIME_PATTERN));
        LocalTime parsedFinalTime = LocalTime.parse(finalTime, DateTimeFormatter.ofPattern(TIME_PATTERN));

        if(parsedInitTime.isAfter(parsedFinalTime)){
            return 0;
        }

        for(LocalTime timeCounter = parsedInitTime; ; timeCounter = timeCounter.plusSeconds(1)) {
            String timeString = timeCounter.format(DateTimeFormatter.ofPattern(TIME_PATTERN)).replace(TIME_SEPARATOR, StringUtils.EMPTY);
            List<String> digits = timeString.codePoints().mapToObj(c -> String.valueOf((char) c)).distinct().collect(Collectors.toList());

            if(digits.size() <= MAX_INTERESTING_POINTS_IN_TIME) {
                totalInterestingPointsInTime++;

                log.debug("timeCounter={} | timeString={} | distinct={} | totalInterestingPointsInTime={}",
                        timeCounter.format(DateTimeFormatter.ofPattern(TIME_PATTERN)),
                        timeString,
                        digits,
                        totalInterestingPointsInTime);
            }

            if(timeCounter.equals(parsedFinalTime)) {
                break;
            }
        }

        final boolean IS_TEST_OK = totalInterestingPointsInTime == answer;

        Printer.print(MessageFormat.format("Answer Total Interesting Points In Time : {0} | {1}",
                totalInterestingPointsInTime,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return totalInterestingPointsInTime;
    }
}
