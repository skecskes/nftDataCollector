package io.monax.nft.dto.twitter;

import java.util.List;

public class SearchResult {
    public String author_id;
    public String created_at;
    public Entities entities;
    public String id;
    public String lang;
    public String in_reply_to_user_id;
    public List<ReferencedTweet> referenced_tweets;
    public String source;
    public String text;
}
