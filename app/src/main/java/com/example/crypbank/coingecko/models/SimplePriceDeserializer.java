package com.example.crypbank.coingecko.models;

import android.util.Log;

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
        double precio = responseJson.get("bitcoin").getAsJsonObject().get("eur").getAsDouble();

        Coin bitcoin = new Coin("bitcoin", precio);
        List<Coin> coins = new ArrayList<>();
        coins.add(bitcoin);
        return coins;
    }
}
