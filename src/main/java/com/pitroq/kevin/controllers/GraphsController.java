package com.pitroq.kevin.controllers;


import com.pitroq.kevin.Calculator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphsController implements Initializable {
    @FXML
    private TextField expressionTextField;
    @FXML
    private LineChart<Double, Double> graph;

    private XYChart.Series<Double, Double> generateSeries(String expression, double xStart, double xEnd, boolean isFirstTime) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double yMin = Calculator.eval(expression.replace("x", String.valueOf(xStart)));
        double xCenter = xStart;

        for (double x = xStart; x <= xEnd; x += 0.25) {
            double y = Calculator.eval(expression.replace("x", String.valueOf(x)));
            series.getData().add(new XYChart.Data<>(x, y));

            if (y < yMin) {
                yMin = y;
                xCenter = x;
            }
        }

        if (xCenter != 0 && isFirstTime) {
            return generateSeries(expression, xCenter+xStart, xCenter+xEnd, false);
        }

        return series;
    }

    @FXML
    private void createGraph() {
        String expression = expressionTextField.getText();

        if (expression.isEmpty()) {
            return;
        }

        XYChart.Series<Double, Double> series = generateSeries(expression, -20, 20, true);
        graph.getData().clear();
        graph.getData().add(series);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graph.setLegendVisible(false);
        graph.setAnimated(false);
        graph.setCreateSymbols(false);
    }
}

