package io.monax.nft.datasource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.monax.nft.dto.opensea.Asset;
import io.monax.nft.dto.opensea.Collection;
import io.monax.nft.dto.opensea.ListOfAssets;
import io.monax.nft.dto.opensea.SingleCollection;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class OpenSea implements Serializable {

    private final RestClient client;

    public OpenSea() {
        client = new RestClient();
    }

    public SingleCollection loadSingleCollection(String slug) {
        Gson g = new GsonBuilder().serializeNulls().create();
        String requestUrl = "https://api.opensea.io/api/v1/collection/" + slug;
        String collection = client.getContent(requestUrl, null);
        return g.fromJson(collection, Collection.class).collection;
    }

    public List<Asset> loadAssets(String contract, String slug, int count) {
        Gson g = new GsonBuilder().serializeNulls().create();
        int low = 0;
        int high = 50;
        List<Asset> listOfAssets = new ArrayList<>();
        while (low < count) {
            String requestUrl = "https://api.opensea.io/api/v1/assets?asset_contract_address=" + contract + "&order_direction=desc&offset=" + low + "&limit=50&collection=" + slug;
            String list = client.getContent(requestUrl, null);
            if (list.equals("")) {
                continue;
            }
            ListOfAssets loa = g.fromJson(list, ListOfAssets.class);
            if (loa.assets != null) {
                List<Asset> assets = Arrays.asList(loa.assets);
                listOfAssets.addAll(assets);
            }
            low = low + 50;
            high = high + 50;
        }
        log.info("There are " + listOfAssets.size() + " assets for " + slug + " NFT");
        return listOfAssets;
    }
}
