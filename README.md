RxJava lab
===========

The purpose of the lab is to get acquainted with Rx and <a href="https://github.com/Netflix/RxJava">RxJava</a> from Netflix.

Prerequisites
-------------
If you want to run the program locally you need:

1. Java 8 (Optional but recommended. If you want to use Java 7 then change compiler version in the pom.xml file).
2. Maven 3

Exercises
-----------

The exercises can be done in any order but the following order is suggested.

<h4>Sentiment Analyzer</h4>
The purpose is to implement a simple sentiment analyzer that inspects a sequence of sentences and for each one determine if the sentence is positive
or negative by checking if the sequence contains a given set of words. The rules are as follows:

1. If the sentence contains more positive words than negative words then the outcome is positive.
2. If the sentence contains more negative words than positive words then the outcome is negative.
3. If the sentence contains an equal amount of negative words and positive words then the outcome is neutral.
4. If the sentence doesn't contain any positive or negative words then the outcome is neutral.

Print the result to the console (or something fancier if you like)! Start at `com.jayway.kdag.rx.SentimentAnalysisExample` and implement the three sub-tasks:

1. Convert the list of sentences loaded from `sentences.txt` into an Observable then solve the problem in FR style.
2. Implement an Observable that shuffles and delays each sentence by a small amount of time for infinity.
3. Create an Observable that takes live data from a Twitter stream (perhaps using <a href="http://twitter4j.org/">twitter4j</a> and performs sentiment analysis on each tweet. Note that this exercise may take some time so you may want to come back to it at the end if you have enough time.

<h4>Button Combination</h4>
Running the `com.jayway.kdag.rx.ButtonCombinationExample` will open up a simple Swing UI with two buttons (A & B) and two text areas. The purpose of this exercise is to
is to use the <a href="https://github.com/Netflix/RxJava/tree/master/rxjava-contrib/rxjava-swing">RxJava Swing</a> extension to do the following:

1. React to button presses for the two buttons and print the button name on the text area in the south west corner for each button press.
2. Find out if the user has pressed the password sequence of "ABBABA" and if so print "Password matched" in text area in the south east corner.
3. Add so that the password sequence must be entered within 4 seconds otherwise it's no match! Also clear the text area in the south east corner after an appropriate amount of time after a match has been found.

<h4>Top movies</h4>
The purpose here is to download the first 100 movie titles from the <a href="http://www.imdb.com/chart/top">IMDB top 250 movie list</a> and compare them to the movies at
<a href="http://www.filmcrave.com/list_top_movie_100.php">Film Crave</a>. For each top list compare the the movie titles and see which movies that are on the same
position on both lists. Print the movie name and the position of the movie to the console (or something fancier if time allows). You should use the
<a href="https://github.com/Netflix/RxJava/tree/master/rxjava-contrib/rxjava-apache-http">RxJava HttpClient</a> extension when making requests to the website. You may
parse the website in whatever way you find suitable (as long as it's downloaded using the RxJava HttpClient wrapper) but there are examples on how to parse to website
using <a href="https://code.google.com/p/rest-assured/">RestAssured</<a>'s <a href="http://rest-assured.googlecode.com/svn/tags/2.3.0/apidocs/com/jayway/restassured/path/xml/XmlPath.html">XmlPath</a>
in the test `com.jayway.kdag.rx.MovieExampleTest`. To spice things up you should also periodically check the website for changes, you never know when a new film enters the top-list
or if a position change :)

<h4>Wikipedia search</h4>
Implement a simple Swing UI (or something similar) that continuously reads a text field and searches for Wikipedia articles that match the query (note that you obviously can't send a new query on each
key stroke so they need to be buffered a certain amount of time). The found articles should be clickable and if clicked the article should be downloaded and displayed in the UI.

<h4>Rock-Paper-Scissors event analyzer</h4>
Open the <a href="https://github.com/jankronquist/rock-paper-scissors-in-java">rock-paper-scissors</a> game that you created in an earlier lab session. Post all events to a queue or directly to an RxJava observable and perform some cool analysis on the game play in realtime.
If time allows then push the results of your analysis to a websocket (using for example VertX RxJava extension) and present it in a UI (perhaps using <a href="http://d3js.org/">d3</a>).