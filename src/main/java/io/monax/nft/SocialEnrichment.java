package io.monax.nft;

import io.monax.nft.config.AppContext;
import io.monax.nft.config.FavouriteNftsConfig;
import io.monax.nft.datasource.Twitter;
import io.monax.nft.dto.twitter.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class SocialEnrichment implements Serializable {

    public HashMap<String, String> nfts;

    public SocialEnrichment() {
        FavouriteNftsConfig conf = new FavouriteNftsConfig(1);
        nfts = conf.nfts;
    }

    public static void main(String[] args) {
        new SocialEnrichment().run(args);
    }

    void run(String[] args) {
        log.info("Social Enrichment started at {} ", LocalTime.now());

        AppContext app = new AppContext();
        SparkSession spark = app.spark;
        JavaSparkContext jsc = app.javaSparkContext;
        Twitter twitter = new Twitter("AAAAAAAAAAAAAAAAAAAAAMH4VgEAAAAAIxCajP9KSKOccQqENKvDE71ZLMk%3Dg5jSN5IFDxclln1UCQ19Xz79K8ac9vXLrTI3JslRdT72qc3IYU");

        JavaRDD<String> slugsRdd = jsc.parallelize(new ArrayList<>(nfts.keySet()));
        JavaRDD<SearchResult> tweetsRdd = slugsRdd.flatMap(new FlatMapFunction<String, SearchResult>() {
            @Override
            public Iterator<SearchResult> call(String slug) {
                int numberOfTweets = 5000;
                List<SearchResult> assets = twitter.searchTweets(slug, numberOfTweets);
                return assets.iterator();
            }
        });

        JavaRDD<Row> searchResultsRdd = tweetsRdd.map(Schemas::mapTweet);
        Dataset<Row> searchResultsDf = spark.createDataFrame(searchResultsRdd, Schemas.getTweetSchema());

        searchResultsDf.write().format("csv").option("header", "true").mode("overwrite").save("spark-warehouse/tweets");

        app.stop();
        log.info("NFT Ingestion completed at {} ", LocalTime.now());
    }

}
