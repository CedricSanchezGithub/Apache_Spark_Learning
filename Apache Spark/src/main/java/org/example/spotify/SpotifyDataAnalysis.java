package org.example.spotify;
import org.apache.spark.sql.Encoders;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import scala.collection.immutable.List;

import java.util.ArrayList;

public class SpotifyDataAnalysis {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .appName("SpotifyDataAnalysis")
                .master("local")
                .getOrCreate();

        String token = "BQDersems5RxAjZq0H_P7FzhFgqljTqoXO7-ukdvXFcBLm-rdSL5tBo6-_DXRtwZZVrKJXqyQ9yN96NXixCEV_gSfphNHQGmSRkNib_Qr8U9YzU3Bj0"; // Remplacez par votre token

        // Appel à l'API Spotify pour obtenir les morceaux likés
        String response = SpotifyLikedSongs.fetchLikedSongs(token);

        if (response != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONArray("items");

            // Parcourir chaque morceau pour ajouter dans un JSONArray
            for (int i = 0; i < items.length(); i++) {
                JSONObject trackInfo = new JSONObject();
                JSONObject track = items.getJSONObject(i).getJSONObject("track");

                trackInfo.put("name", track.getString("name"));
                trackInfo.put("album", track.getJSONObject("album").getString("name"));
                trackInfo.put("artist", track.getJSONArray("artists").getJSONObject(0).getString("name"));
                trackInfo.put("popularity", track.getInt("popularity"));

                jsonArray.put(trackInfo);
            }

            // Conversion en List<String> pour Spark
            ArrayList<String> jsonStringList = new ArrayList<>();  // Utilisez ArrayList<String> ici
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonStringList.add(jsonArray.getJSONObject(i).toString());
            }

            // Création du Dataset à partir de la liste de chaînes JSON
            Dataset<Row> likedSongsData = sparkSession.read().json(sparkSession.createDataset(
                    jsonStringList, Encoders.STRING()
            ));

        // Afficher les morceaux likés
            likedSongsData.show();


            // Afficher les morceaux likés
            likedSongsData.show();
        }

        sparkSession.stop();
    }
}
