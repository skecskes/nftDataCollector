package io.monax.nft.dto.opensea;

import java.util.HashMap;

public class SingleCollection {
    public String name;
    public String external_link;
    public String description;
    public String slug;
    public String image_url;
    public Contract[] primary_asset_contracts;
    public HashMap<String, String> stats;
    public String creation_date;
    public String discord_url;

}
