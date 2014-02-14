package com.jayway.kdag.rx;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import rx.Observable;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class SentimentAnalysisExample {
    private static final List<String> POSITIVE = asList(":)", ":D", "yay", "good", "great", "<3", "yes", "nice", "awesome");
    private static final List<String> NEGATIVE = asList(":(", ":/", "no", "argh", "darn", "dammit", "ugly", "workaround", "sad");

    public static void main(String[] args) throws IOException {
        List<String> sentences = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("sentences.txt"));

        Observable.from(sentences).
                map(String::toLowerCase).
                map(sentence -> new ImmutablePair<>(sentence, analyze(POSITIVE, sentence) - analyze(NEGATIVE, sentence))).
                doOnNext(pair -> {
                    if (pair.getRight() < 0) {
                        System.out.printf("Negative: %s\n", pair.getLeft());
                    } else if (pair.getRight() > 0) {
                        System.out.printf("Positive: %s\n", pair.getLeft());
                    } else {
                        System.out.printf("Neutral: %s\n", pair.getLeft());
                    }
                }).subscribe();

    }

    private static long analyze(List<String> match, String sentence) {
        return match.stream().filter(sentence::contains).count();
    }

    private class Sentence {

    }
}
