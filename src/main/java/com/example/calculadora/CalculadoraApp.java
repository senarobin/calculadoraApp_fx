package com.example.calculadora;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraApp extends Application {

    private List<String> historico = new ArrayList<>();

    @Override
    public void start(Stage principalStage) {

        TextField inputPrimeiroNumero = new TextField();
        TextField inputSegundoNumero = new TextField();

        ComboBox<String> operacaoBox = new ComboBox<>();
        operacaoBox.getItems().addAll("+", "-", "*", "/");

        Button calcularBotao = new Button("Calcular");
        Label resultadoLabel = new Label("Resultado: ");
        Button verHistoricoBotao = new Button("Ver Histórico");

        calcularBotao.setOnAction(e -> {
            try {
                double numeroUm = Double.parseDouble(inputPrimeiroNumero.getText());
                double numeroDois = Double.parseDouble(inputSegundoNumero.getText());
                String operacao = operacaoBox.getValue();

                if (operacao == null) {
                    resultadoLabel.setText("Escolha a operação");
                    return;
                }

                double resultado = switch (operacao) {
                    case "+" -> numeroUm + numeroDois;
                    case "-" -> numeroUm - numeroDois;
                    case "*" -> numeroUm * numeroDois;
                    case "/" -> numeroDois != 0 ? numeroUm / numeroDois : Double.NaN;
                    default -> 0;
                };

                String entrada = numeroUm + " " + operacao + " " + resultado;
                historico.add(entrada);

                resultadoLabel.setText("Resultado: " + resultado);
            } catch (NumberFormatException ex) {
                resultadoLabel.setText("Por favor insira os valores válidos");
            }
        });

        verHistoricoBotao.setOnAction(e -> abrirHistorico());

        VBox layout = new VBox(10, inputPrimeiroNumero, inputSegundoNumero, calcularBotao, operacaoBox, resultadoLabel, verHistoricoBotao);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add(getClass().getResource("/com/example/calculadora/styles.css").toExternalForm());

        principalStage.setTitle("Calculadora");
        principalStage.setScene(scene);
        principalStage.show();
    }

    private void abrirHistorico() {
        Stage historicoStage = new Stage();
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(historico);
        Scene scene = new Scene(new VBox(listView), 300, 200);

        historicoStage.setScene(scene);
        historicoStage.setTitle("Histórico da calculadora");
        historicoStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}