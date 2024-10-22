package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.apache.spark.sql.functions.col;

public class Netflix {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .appName("NetflixByCountry")
                .master("local")
                .getOrCreate();

        Dataset<Row> data = sparkSession.read()
                .format("csv")
                .option("header", "true")
                .option("inferSchema", "true")
                .option("sep", ",")
                .option("encoding", "UTF-8")
                .load("/home/cedric/IdeaProjects/Datas_Sparks/Apache Spark/src/main/resources/netflix_titles.csv");

        // Filtrer les données et les convertir en JSON
        Dataset<Row> movies = data.filter(col("title").isNotNull());

        // Convertir les données en JSON
        JSONArray jsonArray = new JSONArray();
        movies.collectAsList().forEach(row -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("movie", Optional.ofNullable(row.getAs("title")).orElse("aucune donnée pour movie"));
            jsonObject.put("country", Optional.ofNullable(row.getAs("country")).orElse("aucune donnée pour country"));
            jsonObject.put("type", Optional.ofNullable(row.getAs("type")).orElse("aucune donnée pour type"));
            jsonObject.put("director", Optional.ofNullable(row.getAs("director")).orElse("aucune donnée pour director"));
            jsonObject.put("cast", Optional.ofNullable(row.getAs("cast")).orElse("aucune donnée pour cast"));
            jsonObject.put("dateAdded", Optional.ofNullable(row.getAs("date_added")).orElse("aucune donnée pour date_added"));
            jsonObject.put("releaseYear", Optional.ofNullable(row.getAs("release_year")).orElse("aucune donnée pour release_year"));
            jsonObject.put("rating", Optional.ofNullable(row.getAs("rating")).orElse("aucune donnée pour rating"));
            jsonObject.put("duration", Optional.ofNullable(row.getAs("duration")).orElse("aucune donnée pour duration"));
            jsonObject.put("listedIn", Optional.ofNullable(row.getAs("listed_in")).orElse("aucune donnée pour listed_in"));
            jsonObject.put("description", Optional.ofNullable(row.getAs("description")).orElse("aucune donnée pour description"));

            jsonArray.put(jsonObject);

        });
        movies.show();

        // Envoyer les données au serveur Spring
        sendPostRequest("http://localhost:8081/netflix/postmovies", jsonArray.toString());
    }

    private static void sendPostRequest(String urlString, String jsonInputString) {
        try {
            HttpURLConnection connection = getHttpURLConnection(urlString, jsonInputString);

            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                System.out.println("Data was successfully sent to the server.");
            } else {
                System.out.println("POST request failed.");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static @NotNull HttpURLConnection getHttpURLConnection(String urlString, String jsonInputString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }
}
