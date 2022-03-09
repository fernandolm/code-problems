package com.example.demo.problems;

import com.example.demo.interfaces.Test;
import com.example.demo.util.Printer;

import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class WordCounter implements Test {
    private Map<String, Integer> testCases = new HashMap<String, Integer>() {{
        put("We test coders. Give us a try?", 4);
        put("Forget  CVs..Save time . x x", 2);
        put(".", 0);
        put("!", 0);
        put("?", 0);
        put("....", 0);
        put(".!?.!?", 0);
        put(". ? Olá !!!", 1);
        put("   .    ?     t e s t e     !        .      ! !        ", 5);
    }};

    private final List<String> punctuations = new ArrayList<>() {{ add("."); add("?"); add("!"); }};

    @Override
    public void test(){
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList<>();

        for (Map.Entry<String, Integer> sentence : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            countWords(sentence.getKey(), sentence.getValue(), result);
            results.add(result.getValue());
        };

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private long countWords(String sentence, Integer answerTotalWords, MutableBoolean result){
        Printer.print(MessageFormat.format("Sentence: [{0}] | Total: [{1}]", sentence, answerTotalWords));

        long highestTotalWordsPhrase = 0;
        String highestPhrase = "";
        String phrase = "";

        for (int initialSearchPosition = 0; initialSearchPosition < sentence.length(); ) {
            int punctuationPosition = initialSearchPosition;
            int lowestPunctuationPosition = sentence.length();

            for (String punctuation : punctuations) {
                punctuationPosition = sentence.indexOf(punctuation, initialSearchPosition);

                if(punctuationPosition > -1 && punctuationPosition < lowestPunctuationPosition) {
                    lowestPunctuationPosition = punctuationPosition;
                }
            }

            long totalWordsPhrase = 0;

            phrase = sentence.substring(initialSearchPosition, lowestPunctuationPosition);
            initialSearchPosition += lowestPunctuationPosition + 1;

            var words = Arrays.asList(phrase.split(" "));
            totalWordsPhrase = words.stream().filter(w -> StringUtils.isNotBlank(w)).count();

            if(totalWordsPhrase > highestTotalWordsPhrase) {
                highestTotalWordsPhrase = totalWordsPhrase;
                highestPhrase = phrase;
            }
        }

        Printer.print(MessageFormat.format("Answer: Phrase with more words: {0} | Total Words: {1} | {2}",
                highestPhrase,
                highestTotalWordsPhrase,
                (highestTotalWordsPhrase == answerTotalWords)));

        result.setValue(highestTotalWordsPhrase == answerTotalWords);

        return highestTotalWordsPhrase;
    }
}