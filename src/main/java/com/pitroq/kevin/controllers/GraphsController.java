package com.pitroq.kevin.controllers;


import com.pitroq.kevin.Calculator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GraphsController implements Initializable {
    @FXML
    private LineChart<Double, Double> graph;

    private XYChart.Series<Double, Double> generateSeries(String expression) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        for (int x = -16; x <= 10; x++) {
            double y = Calculator.eval(expression.replace("x", String.valueOf(x)));

            System.out.println(x + ", " + y);
            series.getData().add(new XYChart.Data<>((double) x, y));
        }


        return series;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graph.getData().add(generateSeries("x*x+8*x+17"));
    }
}

