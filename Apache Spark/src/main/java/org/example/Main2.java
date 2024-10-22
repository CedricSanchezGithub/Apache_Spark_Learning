package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.spark.sql.functions.*;

public class Main2 {
        public static void main(String[] args) {
            SparkSession sparkSession = SparkSession.builder().appName("AnalyseArbres").master("local").getOrCreate();

            Dataset<Row> data = sparkSession.read()
                    .format("csv")
                    .option("header", "true")
                    .option("inferSchema", "true")
                    .option("sep", ";")
                    .option("encoding", "UTF-8")
                    .option("quote", "'")
                    .load("/home/cedric/IdeaProjects/Datas_Sparks/src/main/resources/les-arbres.csv");

            // Agrégation des données
            Dataset<Row> speciesCount = data.filter(col("ESPECE").isNotNull())
                    .groupBy("ESPECE")
                    .count()
                    .orderBy(desc("count"))
                    .limit(10);

            // Conversion en Java List pour utilisation avec JFreeChart
            List<Row> topSpecies = speciesCount.collectAsList();

            // Création du dataset pour JFreeChart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Row row : topSpecies) {
                String species = row.getString(0);
                Long count = row.getLong(1);
                if (species != null && count != null) {
                    dataset.addValue(count, "Nombre d'arbres", species);
                }
            }
            topSpecies.forEach(System.out::println);


            // Création du graphique
            JFreeChart chart = ChartFactory.createBarChart(
                    "Top 10 des espèces d'arbres",
                    "Espèce",
                    "Nombre",
                    dataset
            );

            // Sauvegarde du graphique en PNG
            try {
                ChartUtils.saveChartAsPNG(new File("top_10_especes_arbres.png"), chart, 800, 600);
                System.out.println("Graphique sauvegardé sous 'top_10_especes_arbres.png'");
            } catch (IOException e) {
                e.printStackTrace();
            }
            sparkSession.stop();
        }
}
