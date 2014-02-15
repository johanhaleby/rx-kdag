package com.jayway.kdag.rx;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class SentimentAnalysisExample {
    private static final List<String> POSITIVE = asList(":)", ":D", "yay", "good", "great", "<3", "yes", "nice", "awesome");
    private static final List<String> NEGATIVE = asList(":(", ":/", "no", "argh", "darn", "dammit", "ugly", "workaround", "sad");

    public static void main(String[] args) throws IOException {
        List<String> sentences = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("sentences.txt"));

        Observable.create(new InfiniteSentenceGenerator(sentences)).
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

    private static class InfiniteSentenceGenerator implements Observable.OnSubscribeFunc<String> {
        private final List<String> sentences;
        private volatile boolean isRunning;

        public InfiniteSentenceGenerator(List<String> sentences) {
            this.sentences = sentences;
            isRunning = true;
        }

        public void stop() {
            isRunning = false;
        }

        @Override
        public Subscription onSubscribe(Observer<? super String> observer) {
            while (isRunning) {
                for (String sentence : shuffle(sentences)) {
                    int i = new Random().nextInt(500);
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException e) {
                        observer.onError(e);
                    }
                    observer.onNext(sentence);
                }
            }
            return Subscriptions.empty();
        }

        private static List<String> shuffle(List<String> sentences) {
            List<String> mutable = new ArrayList<>(sentences);
            Collections.shuffle(mutable);
            return Collections.unmodifiableList(mutable);
        }
    }
}
