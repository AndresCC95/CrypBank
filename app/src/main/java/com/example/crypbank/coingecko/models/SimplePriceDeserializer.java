package com.example.crypbank.coingecko.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Clase para deserializar los datos recogidos en la API
public class SimplePriceDeserializer implements JsonDeserializer<List<Coin>> {

    //Metodo para la deserializacion de datos, pasandolos a una lista de coin
    @Override
    public List<Coin> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject responseJson = json.getAsJsonObject();
        List<Coin> coins = new ArrayList<>();

        //Asignacion del nombre y precio de cada moneda crypto
        for (String coinName : Coin.COIN_NAMES) {
            double precio = responseJson.get(coinName).getAsJsonObject().get(Coin.CURRENCIES[0]).getAsDouble();
            Coin coin = new Coin(coinName, precio);
            coins.add(coin);
        }
        return coins;
    }
}
