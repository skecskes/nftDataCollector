package io.monax.nft.dto.opensea;

public class DTOHelper {

    public static Asset anyAsset() {
        Asset asset = new Asset();
        asset.id = 12348;
        asset.image_url = "http://image.url/12348.jpg";
        asset.name = "WAGMI NFT";
        asset.token_id = "1";
        asset.owner_address = "0x644235653452354";

        asset.asset_contract = anyAssetContract();
        asset.collection = anySingleCollection();
        asset.last_sale = anyLastSale();

        return asset;
    }

    public static AssetContract anyAssetContract() {
        AssetContract assetContract = new AssetContract();
        assetContract.address = "0x214534423";
        assetContract.description = "This is cool NFT token";
        assetContract.schema_name = "ERC-721";
        return assetContract;
    }

    public static SingleCollection anySingleCollection() {
        SingleCollection singleCollection = new SingleCollection();
        singleCollection.description = "this describes the entire NFT collection";
        singleCollection.image_url = "http://image.url/wagmi.jpg";
        singleCollection.name = "Bored Wagmi Kayaks";
        singleCollection.slug = "bored-wagmi-kayaks";
        singleCollection.creation_date = "2021-08-21 14:35:21";
        singleCollection.discord_url = "http://discord.gg/asf2345";
        return singleCollection;
    }

    public static LastSale anyLastSale() {
        LastSale lastSale = new LastSale();
        lastSale.auction_type = "successful";
        lastSale.event_timestamp = "2021-09-10 04:59:59";
        lastSale.quantity = 1;
        lastSale.event_type = null;
        lastSale.total_price = "34.12000";
        lastSale.successful = "true";
        return lastSale;
    }
}
