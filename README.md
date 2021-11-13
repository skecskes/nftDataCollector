# Task

- Pick 5 NFT collections of your choice
- Collect social media data and build metric of your choice that could be used in a pricing model
- The test should not take more than 2 hours, anything you cannot do, please write bullet points to explain your next steps
Extra:
- questions to clarify assumptions
- design recommendations
- ideas of metrics/features for pricing model


#Schemas

## OpenSea

### Asset

    id, integer
    token_id , string
    name, string
    last_sale_total_price, string/null
    last_sale_event_type, string/null
    last_sale_auction_type, string/null
    last_sale_event_timestamp, string/null
    last_sale_quantity, int/null
    asset_contract_address, string
    asset_contract_created_date, string
    asset_contract_name, string
    asset_contract_schema_name, string
    collection_creation_date, string/null
    collection_discord_url, string
    collection_name, string
    collection_slug, string
    owner_address, string

This resource is created by `Ingestion.java` and result can be found in `spark-warehouse/ingestion` folder. Unfortunately I still didn't receive my apikey for opensea and requests are failing due to rate limiter. I was able to pull data from one collection only.


### Collection

    created_date, string
    description, string
    discord_url, string
    external_url, string
    name, string
    slug, string
    stats_count, int
    stats_num_owners, int
    stats_average_price, float
    stats_market_cap, float
    stats_total_sales, int
    stats_total_volume, float
    primary_asset_contracts_address, string
    primary_asset_contracts_symbol, string
    primary_asset_contracts_opensea_version, string

Parts of this schema are ingested through ingestion process into `Asset` schema. If needed, we could pull more details from json responses.


## Twitter

    author_id, string
    created_at, string
    id, string
    lang, string
    text, string

Twitter search is done with SocialEnrichment.java and that was last thing I managed to deliver in 2 hours timeframe. I can also see that tweets search returned a lot of irrelevant bot activity tweets. That would need to be filtered down and query improved.
Example of tweets csv should be in `spark-warehouse/tweets` folder.
    
TODO:
- list of retweet users
- list of like users
Once we get the list of tweets above we should make another call to Twitter api to get more details about each tweet (likes, retweets). That should be fairly easy, but I run out of time.

### Transaction

- token_id
- minted on OS or not
- collection name
- collection id
- list of transactions
- last price data
- list of auctions

TODO: Run out of time to implement transactions, the transactions could be pulled down from opensea (https://docs.opensea.io/reference/retrieving-orders), or alternatively we could use third party API that reads transactions from etherum network directly, because we know contract and id of token.

#Use cases

- auction price vs last transaction price
- average of last 3
- timelapse between last and last 3(closeness metric)


# Insight into current state 

- monax is building pipeline for the analytics that will power part of their product


# Design

I created two spark applications, first will ingest data about nft tokens and second will pull data from twitter about coresponding nft collections. We could pull more data about transactions from opensea or other 3rp party api to be able to run som models on data.
Outcome csv should be in `spark-warehouse` folders.