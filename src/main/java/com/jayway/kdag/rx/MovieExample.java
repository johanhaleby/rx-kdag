package com.jayway.kdag.rx;

import com.jayway.restassured.path.xml.XmlPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.util.functions.Func1;
import rx.util.functions.Func2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static com.jayway.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import static org.apache.http.nio.client.methods.HttpAsyncMethods.create;

/**
 * Hello world!
 */
public class MovieExample {

    private static final String FILM_CRAVE_TOP_100_MOVIES_LIST = "http://www.filmcrave.com/list_top_movie_100.php";
    private static final String IMDB_TOP_250_MOVIES_LIST = "http://www.imdb.com/chart/top";


    public static void main(String[] args) {
        final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

        // TODO Implement
    }

    private static HttpAsyncRequestProducer get(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept-Language", "en-US");
        return create(httpGet);
    }
}
