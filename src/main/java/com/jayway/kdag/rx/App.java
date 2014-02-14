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
import rx.util.functions.Action1;
import rx.util.functions.Func1;
import rx.util.functions.Func2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static com.jayway.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import static org.apache.http.nio.client.methods.HttpAsyncMethods.create;

/**
 * Hello world!
 */
public class App {

    private static final String FILM_CRAVE_TOP_100_MOVIES_LIST = "http://www.filmcrave.com/list_top_movie_100.php";
    private static final String IMDB_TOP_250_MOVIES_LIST = "http://www.imdb.com/chart/top";


    public static void main(String[] args) {
        final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();

        Observable.from("1", "2");

        Observable<List<String>> filmCrave = ObservableHttp.createRequest(get(FILM_CRAVE_TOP_100_MOVIES_LIST), httpClient).toObservable().
                flatMap(response -> response.getContent().map((Func1<byte[], String>) String::new)).
                takeWhile(line -> !StringUtils.endsWithIgnoreCase(line, "</html>")).
                collect(new StringBuffer(), (stringBuffer, s) -> {
                    stringBuffer.append(s);
                }).
                map(stringBuffer -> new XmlPath(HTML, stringBuffer.toString()).getList("**.findAll { it.a.@href.text().startsWith('movie') }", String.class)).
                map(titlesWithYear -> titlesWithYear.stream().map((String titleWithYear) -> StringUtils.substringBeforeLast(titleWithYear, " ")).collect(Collectors.toList()));

        Observable<List<String>> imdb = ObservableHttp.createRequest(get(IMDB_TOP_250_MOVIES_LIST), httpClient).toObservable().
                flatMap(response -> response.getContent().map((Func1<byte[], String>) String::new)).
                takeWhile(line -> !StringUtils.endsWithIgnoreCase(line, "</html>")).
                collect(new StringBuffer(), (stringBuffer, s) -> {
                    stringBuffer.append(s);
                }).
                map(stringBuffer -> new XmlPath(HTML, stringBuffer.toString()).
                        getList("**.findAll { it.a.@href.text().startsWith('/title/')  }.a*.toString().findAll { !it.isEmpty() && it != 'X' }", String.class));

        Observable.zip(filmCrave, imdb, new Func2<List<String>, List<String>, List<Pair<String, String>>>() {
            @Override
            public List<Pair<String, String>> call(List<String> filmCrave, List<String> imdb) {
                Iterator<String> iterator = imdb.iterator();
                return filmCrave.stream().reduce(new ArrayList<>(), (pairs, filmCraveMovie) -> {
                    pairs.add(new ImmutablePair<>(filmCraveMovie, iterator.next()));
                    return pairs;
                }, (pairs1, pairs2) -> {
                    pairs1.addAll(pairs2);
                    return pairs1;
                }
                );
            }
        }).subscribe(pairs -> {
            System.out.println(pairs);
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    static <T> T find(Collection<T> c1, Collection<T> c2, BiPredicate<T, T> pred) {
        Iterator<T> it = c2.iterator();
        return c1.stream().filter(x -> it.hasNext() && pred.test(x, it.next()))
                .findFirst().orElse(null);
    }

    private static HttpAsyncRequestProducer get(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept-Language", "en-US");
        return create(httpGet);
    }
}
