//https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/564/
package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.text.MessageFormat;
import java.util.*;

public class BuySellStocks implements Test {
    private final Map<int[], Integer> testCases = new HashMap<>() {{
        put(new int[]{1}, 0);
        put(new int[]{1,2}, 1);
        put(new int[]{1,2,3}, 2);
        put(new int[]{7,1,5,3,6,4}, 7);
        put(new int[]{1,2,3,4,5}, 4);
        put(new int[]{7,6,4,3,1}, 0);
    }};


    @Override
    public void test() {
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (var sentence : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            maxProfit(sentence.getKey(), sentence.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private int maxProfit(int[] prices, Integer answerProfit, MutableBoolean result) {
        Printer.print(MessageFormat.format("Prices: {0} | Profit: {1}", Arrays.toString(prices), answerProfit));

        final int NO_VALUE = -1;

        int profit = 0;
        int buy = NO_VALUE;
        int sell = NO_VALUE;

        for(int counter = 0; counter < prices.length; counter++)
        {
            for(int counter2 = counter + 1; counter2 < prices.length; counter2++) {
                if(buy == NO_VALUE && prices[counter] < prices[counter2]) {
                    buy = prices[counter];
                    break;
                }

                if(buy != NO_VALUE && prices[counter] > prices[counter2]) {
                    sell = prices[counter];
                    break;
                }
            }

            if(buy != NO_VALUE && sell != NO_VALUE) {
                profit += sell - buy;
                buy = NO_VALUE;
                sell = NO_VALUE;
            }
        }

        if(buy != NO_VALUE){
            profit += prices[prices.length-1] - buy;
        }

        Printer.print(MessageFormat.format("Answer: Profit: {0} | {1}", profit, (profit == answerProfit)));
        result.setValue(profit == answerProfit);

        return profit;
    }
}
