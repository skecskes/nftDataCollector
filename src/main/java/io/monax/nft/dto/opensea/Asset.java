package io.monax.nft.dto.opensea;

import java.util.HashMap;
import java.util.List;

public class Asset {
    public int id;
    public String token_id;
    public String name;
    public String image_url;
    public SingleCollection collection;
    public AssetContract asset_contract;
    public String token_metadata;
    public List<HashMap<String, String>> traits;
    public LastSale last_sale;
    public String owner_address;


}
