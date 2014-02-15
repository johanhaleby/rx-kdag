package com.jayway.kdag.rx;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class SentimentAnalysisExample {
    private static final List<String> POSITIVE = asList(":)", ":D", "yay", "good", "great", "<3", "yes", "nice", "awesome");
    private static final List<String> NEGATIVE = asList(":(", ":/", "no", "argh", "darn", "dammit", "ugly", "workaround", "sad");

    public static void main(String[] args) throws IOException {
        List<String> sentences = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("sentences.txt"));

        // TODO Implement

    }
}
