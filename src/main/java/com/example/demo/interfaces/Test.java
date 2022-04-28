package com.example.demo.interfaces;

import com.example.demo.util.Printer;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.text.MessageFormat;

public interface Test {
    void test();

    default <T> void printResults(T result, T answer, MutableBoolean isTestOk) {
        final boolean IS_TEST_OK = result.equals(answer);

        Printer.print(MessageFormat.format("Answer: {0} | {1}", result, IS_TEST_OK));

        isTestOk.setValue(IS_TEST_OK);
    }
}
