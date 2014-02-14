package com.jayway.kdag.rx;

import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import static java.util.Arrays.asList;

public class TwitterExample {
    private static final List<String> POSITIVE = asList(":)", ":D", "yay", "good", "great", "<3", "yes", "nice", "awesome");
    private static final List<String> NEGATIVE = asList(":(", ":/", "no", "argh", "darn", "dammit", "ugly", "workaround", "sad");

    public static void main(String[] args) {
        Configuration twitterConf = new ConfigurationBuilder().setUser(args[0]).setPassword(args[1]).build();
        TwitterStream twitter = new TwitterStreamFactory(twitterConf).getInstance();
        twitter.addListener(new StatusAdapter() {
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }
        });
        twitter.sample();
    }
}
