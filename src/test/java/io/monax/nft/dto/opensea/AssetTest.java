package io.monax.nft.dto.opensea;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AssetTest {

    @Test
    public void test_asset_creation_returns_asset() {
        Asset testAsset = DTOHelper.anyAsset();

        assertInstanceOf(Asset.class, testAsset);
        assertEquals(testAsset.id, 12348);
        assertEquals(testAsset.name, "WAGMI NFT");
        assertEquals(testAsset.collection.creation_date, "2021-08-21 14:35:21");
        assertEquals(testAsset.last_sale.total_price, "34.12000");
        assertEquals(testAsset.last_sale.quantity, 1);
    }
}
