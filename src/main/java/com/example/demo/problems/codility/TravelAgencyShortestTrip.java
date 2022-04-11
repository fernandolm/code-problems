/*
You want to spend your next vacation in a foreign country. In the summer you are free for N consecutive days. You have consulted Travel Agency and learned that they are offering a trip to some interesting location in the country every day. For simplicity, each location is identified by a number from 0 to N − 1. Trips are described in a non-empty array A: for each K (0 ≤ K &lt; N), A[K] is the identifier of a location which is the destination of a trip offered on day K. Travel Agency does not have to offer trips to all locations, and can offer more than one trip to some locations.
You want to go on a trip every day during your vacation. Moreover, you want to visit all locations offered by Travel Agency. You may visit the same location more than once, but you want to minimize duplicate visits. The goal is to find the shortest vacation (a range of consecutive days) that will allow you to visit all the locations offered by Travel Agency.
For example, consider array A such that:
    A[0] = 7
    A[1] = 3
    A[2] = 7
    A[3] = 3
    A[4] = 1
    A[5] = 3
    A[6] = 4
    A[7] = 1
Travel Agency offers trips to four different locations (identified by numbers 1, 3, 4 and 7). The shortest vacation starting on day 0 that allows you to visit all these locations ends on day 6 (thus is seven days long). However, a shorter vacation of five days (starting on day 2 and ending on day 6) also permits you to visit all locations. On every vacation shorter than five days, you will have to miss at least one location.
Write a function:
class Solution { public int solution(int[] A); }
that, given a non-empty array A consisting of N integers, returns the length of the shortest vacation that allows you to visit all the offered locations.
For example, given array A shown above, the function should return 5, as explained above.
Given A = [2, 1, 1, 3, 2, 1, 1, 3], the function should return 3. One of the shortest vacations that visits all the places starts on day 3 (counting from 0) and lasts for 3 days.
Given A = [7, 5, 2, 7, 2, 7, 4, 7], the function should return 6. The shortest vacation that visits all the places starts on day 1 (counting from 0) and lasts for 6 days.
Write an efficient algorithm for the following assumptions:
N is an integer within the range [1..100,000];
each element of array A is an integer within the range [0..N - 1].
 */
package com.example.demo.problems.codility;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TravelAgencyShortestTrip implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final Map<int[], Integer> testCases = new HashMap() {{
        put(new int[]{ 7, 3, 7, 3, 1, 3, 4, 1 }, 5);
        put(new int[]{ 2, 1, 1, 3, 2, 1, 1, 3 }, 3);
        put(new int[]{ 7, 5, 2, 7, 2, 7, 4, 7 }, 6);
        put(new int[]{ 1, 1, 1, 1, 1, 1, 1, 1 }, 1);
        put(new int[]{ 1, 2, 1, 1, 1, 1, 1, 1 }, 2);
        put(new int[]{ 1, 2, 1, 1, 1, 1, 1, 10 }, 7);
        put(new int[]{ 1, 1, 1, 1, 1, 1, 1, 10 }, 2);
        put(new int[]{ 1, 1, 1, 1, 7, 1, 1, 10 }, 4);
        put(new int[]{ 1, 1, 1, 1, 7, 1, 8, 10 }, 4);
        put(new int[]{ 1, 1, 1, 1, 1, 1, 8, 10 }, 3);
    }};

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test(){
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList();

        for (Map.Entry<int[], Integer> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            countShortestTrip(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private Integer countShortestTrip(int[] trips, Integer answer, MutableBoolean result){
        Printer.print(MessageFormat.format("Trips: {0} | Answer: {1}", Arrays.toString(trips), answer));

        Integer shortestTrip = trips.length;

        List<Integer> tripsList = Arrays.stream(trips).distinct().boxed().collect(Collectors.toList());
        List<Integer> markedTrips = new LinkedList<>();
        int initCounter = 0;
        int counter = 0;

        Collections.sort(tripsList);

        while(initCounter < trips.length) {
            markedTrips.add(trips[counter]);

            List<Integer> sortedMarkedTrips = markedTrips.stream().distinct().collect(Collectors.toList());
            Collections.sort(sortedMarkedTrips);

            if(sortedMarkedTrips.equals(tripsList)) {
                if(shortestTrip > markedTrips.size()) {
                    shortestTrip = markedTrips.size();
                    counter = ++initCounter;
                    markedTrips.clear();
                    continue;
                }
            }

            counter++;

            if(counter == trips.length) {
                counter = ++initCounter;
                markedTrips.clear();
            }
        }

        final boolean IS_TEST_OK = shortestTrip == answer;

        Printer.print(MessageFormat.format("Shortest Trip: {0} | Answer: {1} | {2}",
                shortestTrip,
                answer,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return shortestTrip;
    }
}
