package io.monax.nft.config;

import java.util.HashMap;

public class FavouriteNftsConfig {

    public HashMap<String, String> nfts = new HashMap<>();

    public FavouriteNftsConfig() {
        new FavouriteNftsConfig(5);
    }

    public FavouriteNftsConfig(int num) {
        if(num>5) {
            num = 5;
        }
        HashMap<String, String> all = getNftsList();
        int i = 0;
        for(String slug: all.keySet()) {
            if(i < num){
                nfts.put(slug, all.get(slug));
            }
            i++;
        }
    }

    private HashMap<String, String> getNftsList() {
        HashMap<String, String> list = new HashMap<>();
        list.put("parallelalpha", "0x76be3b62873462d2142405439777e971754e8e77");
        list.put("thewickedcraniums", "0x85f740958906b317de6ed79663012859067e745b");
        list.put("cryptopunks", "0xb47e3cd837ddf8e4c57f05d70ab865de6e193bbb");
        list.put("boredapeyachtclub", "0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d");
        list.put("mutant-ape-yacht-club", "0x60e4d786628fea6478f785a6d7e704777c86a7c6");
        return list;
    }
}
