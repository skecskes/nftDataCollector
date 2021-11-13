package io.monax.nft.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.monax.nft.dto.twitter.SearchResult;
import io.monax.nft.dto.twitter.SearchResults;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Twitter implements Serializable {

    private RestClient client;

    private String authToken;

    private int resultsBack = 100;

    public Twitter(String auth) {
        client = new RestClient();
        this.authToken = auth;
    }

    // Search for public Tweets created in the last 7 days.
    public SearchResults search(String searchedText, String nextToken) {
        Gson g = new GsonBuilder().serializeNulls().create();
        String requestUrl = "https://api.twitter.com/2/tweets/search/recent?query=" + searchedText + "&max_results="+resultsBack+"&tweet.fields=author_id,created_at,entities,geo,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source";
        if(nextToken != null) {
            requestUrl = requestUrl + "&next_token=" + nextToken;
        }
        String response = client.getContent(requestUrl, authToken);
        return g.fromJson(response, SearchResults.class);
    }

    public List<SearchResult> searchTweets(String searchedText, int numberOfTweets) {
        int i = 0;
        String nextToken = null;
        List<SearchResult> results = new ArrayList<>();
        while(i < numberOfTweets) {
            SearchResults searchResults = search(searchedText, nextToken);
            log.info("Found " + searchResults.data.size() + " tweets about " + searchedText);
            results.addAll(searchResults.data);
            nextToken = searchResults.meta.next_token;
            i = i + resultsBack;
        }
        return results;
    }
}
