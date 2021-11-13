package io.monax.nft.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FavouriteNftsConfigTest {

    @Test
    public void test_class_creation() {
        FavouriteNftsConfig favouriteNftsConfig = new FavouriteNftsConfig();
        assertInstanceOf(FavouriteNftsConfig.class, favouriteNftsConfig);
    }

    @Test
    public void test_one_nftConfig_returns_one_collection() {
        FavouriteNftsConfig favouriteNftsConfig = new FavouriteNftsConfig(1);
        assertEquals(1, favouriteNftsConfig.nfts.size());
    }

    @Test
    public void test_three_nftConfig_returns_three_collection() {
        FavouriteNftsConfig favouriteNftsConfig = new FavouriteNftsConfig(3);
        assertEquals(3, favouriteNftsConfig.nfts.size());
    }
}
