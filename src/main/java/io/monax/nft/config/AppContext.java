package io.monax.nft.config;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class AppContext {

    public SparkSession spark;
    public JavaSparkContext javaSparkContext;
    private Boolean isActive = false;

    public AppContext() {
        if(!isActive) {
            createSparkSession();
            createJavaSparkContext();
            isActive = true;
        }
    }

    private void createSparkSession() {
        spark =  SparkSession
                .builder()
                .appName("nft-ingestion")
                .master("local[*]")
                .getOrCreate();
    }

    private void createJavaSparkContext() {
        javaSparkContext = new JavaSparkContext(spark.sparkContext());
    }

    public void stop() {
        if(isActive) {
            spark.stop();
            isActive = false;
        }
    }

}
