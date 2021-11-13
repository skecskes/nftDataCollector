package io.monax.nft;

import io.monax.nft.dto.opensea.Asset;
import io.monax.nft.dto.twitter.SearchResult;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class Schemas {

    public static StructType getAssetSchema() {
        StructType assetStruct = new StructType();
        assetStruct = assetStruct.add("id", DataTypes.IntegerType, false);
        assetStruct = assetStruct.add("token_id", DataTypes.StringType, false);
        assetStruct = assetStruct.add("name", DataTypes.StringType, false);
        assetStruct = assetStruct.add("last_sale_total_price", DataTypes.StringType, true);
        assetStruct = assetStruct.add("last_sale_event_type", DataTypes.StringType, true);
        assetStruct = assetStruct.add("last_sale_auction_type", DataTypes.StringType, true);
        assetStruct = assetStruct.add("last_sale_event_timestamp", DataTypes.StringType, true);
        assetStruct = assetStruct.add("last_sale_quantity", DataTypes.IntegerType, true);
        assetStruct = assetStruct.add("asset_contract_address", DataTypes.StringType, true);
        assetStruct = assetStruct.add("asset_contract_created_date", DataTypes.StringType, true);
        assetStruct = assetStruct.add("asset_contract_name", DataTypes.StringType, true);
        assetStruct = assetStruct.add("asset_contract_schema_name", DataTypes.StringType, true);
        assetStruct = assetStruct.add("collection_creation_date", DataTypes.StringType, true);
        assetStruct = assetStruct.add("collection_discord_url", DataTypes.StringType, false);
        assetStruct = assetStruct.add("collection_name", DataTypes.StringType, true);
        assetStruct = assetStruct.add("collection_slug", DataTypes.StringType, true);
        assetStruct = assetStruct.add("owner_address", DataTypes.StringType, true);
        return assetStruct;
    }

    public static Row mapAsset(Asset asset) {
        return RowFactory.create(
                asset.id,
                asset.token_id,
                (asset.name == null) ? "": asset.name,
                (asset.last_sale == null) ? null: asset.last_sale.total_price,
                (asset.last_sale == null) ? null: asset.last_sale.event_type,
                (asset.last_sale == null) ? null: asset.last_sale.auction_type,
                (asset.last_sale == null) ? null: asset.last_sale.event_timestamp,
                (asset.last_sale == null) ? null: asset.last_sale.quantity,
                asset.asset_contract.address,
                asset.asset_contract.created_date,
                asset.asset_contract.name,
                asset.asset_contract.schema_name,
                asset.collection.creation_date,
                (asset.collection.discord_url == null) ? "": asset.collection.discord_url,
                asset.collection.name,
                asset.collection.slug,
                asset.owner_address
        );
    }


    public static StructType getTweetSchema() {
        StructType tweetStruct = new StructType();
        tweetStruct = tweetStruct.add("author_id", DataTypes.StringType, false);
        tweetStruct = tweetStruct.add("created_at", DataTypes.StringType, false);
        tweetStruct = tweetStruct.add("id", DataTypes.StringType, false);
        tweetStruct = tweetStruct.add("lang", DataTypes.StringType, false);
        tweetStruct = tweetStruct.add("text", DataTypes.StringType, false);
        return tweetStruct;
    }

    public static Row mapTweet(SearchResult searchResult) {
        return RowFactory.create(
                searchResult.author_id,
                searchResult.created_at,
                searchResult.id,
                searchResult.lang,
                searchResult.text
        );
    }


}
