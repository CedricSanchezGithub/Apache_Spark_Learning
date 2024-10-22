package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().appName("AnalyseArbres").master("local").getOrCreate();

        Dataset<Row> data = sparkSession.read()
                .format("csv")
                .option("header", "true")
                .option("inferSchema", "true")
                .option("sep", ";")  // Correction : Spécification du séparateur point-virgule
                .option("encoding", "UTF-8")  // Correction : Spécification de l'encodage
                .option("quote", "'")  // Correction : Gestion des guillemets simples
                .load("/home/cedric/IdeaProjects/Datas_Sparks/src/main/resources/les-arbres.csv");

        data.show();
        data.describe().show();

        // Aggregation
        Dataset<Row> nbArbreByArr = data.groupBy("`ARRONDISSEMENT`", "`ESPECE`").count();
        nbArbreByArr.show();

        data.show();
        data.describe().show();

// Aggregation
        Dataset<Row> nbArbresByArr = data.groupBy("arrondissement", "espece").count();
        nbArbresByArr.show();

// Depuis une BDD MySQL
// Les détails de connexion à la base de données
        String jdbcUrl = "jdbc:mysql://5.196.27.67:3306/sakila";
        String table = "actor";
        String user = "wordpress";
        String password = "wppwd";

// Lire les données depuis la base de données
        Dataset<Row> mysqlDF = sparkSession.read()
                .format("jdbc")
                .option("url", jdbcUrl)
//                .option("dbtable", table)
                .option("query", "SELECT * FROM actor WHERE first_name LIKE '%JOHNNY%'")
                .option("user", user)
                .option("password", password)
                .load();

        mysqlDF.show();

        sparkSession.stop();
    }
}
