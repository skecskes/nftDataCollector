package io.monax.nft;

import io.monax.nft.config.AppContext;
import io.monax.nft.config.FavouriteNftsConfig;
import io.monax.nft.datasource.OpenSea;
import io.monax.nft.dto.opensea.Asset;
import io.monax.nft.dto.opensea.SingleCollection;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

@Slf4j
public class Ingestion implements Serializable {

    public HashMap<String, String> nfts;

    public Ingestion() {
        FavouriteNftsConfig myFavs = new FavouriteNftsConfig(1);
        nfts = myFavs.nfts;
    }

    public static void main(String[] args) {
        new Ingestion().run(args);
    }

    void run(String[] args) {
        log.info("NFT Ingestion started at {} ", LocalTime.now());
        AppContext app = new AppContext();
        SparkSession spark = app.spark;
        JavaSparkContext jsc = app.javaSparkContext;
        OpenSea opensea = new OpenSea();

        JavaRDD<String> slugsRdd = jsc.parallelize(new ArrayList<>(nfts.keySet()));
        val collectionsRdd = slugsRdd.map(opensea::loadSingleCollection);
        JavaRDD<Asset> assetsRdd = collectionsRdd.flatMap(new FlatMapFunction<SingleCollection, Asset>() {
            @Override
            public Iterator<Asset> call(SingleCollection collection) {
                int collectionSize = (int) Float.parseFloat(collection.stats.get("total_supply"));
                List<Asset> assets = opensea.loadAssets(collection.primary_asset_contracts[0].address, collection.slug, collectionSize);
                return assets.iterator();
            }
        });
        JavaRDD<Row> assetData = assetsRdd.map(Schemas::mapAsset);
        Dataset<Row> assetsDf = spark.createDataFrame(assetData, Schemas.getAssetSchema());
//        assetsDf.write().mode("overwrite").saveAsTable("parquet");
        assetsDf.write().format("csv").option("header", "true").mode("overwrite").save("spark-warehouse/ingestion");

        app.stop();
        log.info("NFT Ingestion completed at {} ", LocalTime.now());
    }





}
