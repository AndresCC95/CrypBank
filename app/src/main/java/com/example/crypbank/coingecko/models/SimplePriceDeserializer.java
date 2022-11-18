package com.example.crypbank.coingecko.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SimplePriceDeserializer implements JsonDeserializer<List<Coin>> {

    @Override
    public List<Coin> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject responseJson = json.getAsJsonObject();
        List<Coin> coins = new ArrayList<>();

        for (String coinName : Coin.COIN_NAMES) {
            double precio = responseJson.get(coinName).getAsJsonObject().get(Coin.CURRENCIES[0]).getAsDouble();
            Coin coin = new Coin(coinName, precio);
            coins.add(coin);
        }
        return coins;
    }

}
