package com.jayway.kdag.rx;

import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static com.jayway.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieExampleTest {
    
    @Test public void
    file_crave_parsing() {
        // Given
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("filmcrave.html");

        // When
        List<String> topList = new XmlPath(HTML, resourceAsStream).getList("**.findAll { it.a.@href.text().startsWith('movie') }", String.class);

        // Then
        assertThat(topList).hasSize(100);
    }

    @Test public void
    imdb_parsing() {
        // Given
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("imdb.html");

        // When
        List<String> topList = new XmlPath(HTML, resourceAsStream).getList("**.findAll { it.a.@href.text().startsWith('/title/')  }.a*.toString().findAll { !it.isEmpty() && it != 'X' }", String.class);

        // Then
        assertThat(topList).hasSize(250);
    }
}
