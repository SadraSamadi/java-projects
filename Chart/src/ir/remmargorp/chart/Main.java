package ir.remmargorp.chart;

import javafx.application.Application;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("Day", 0, 21, 1);
        NumberAxis yAxis = new NumberAxis("SP", 0, 30, 1);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setStyle("-fx-background-color: white");
        Scene scene = new Scene(lineChart);
        primaryStage.setScene(scene);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        Printer printer = printerJob.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(
                Paper.A3,
                PageOrientation.PORTRAIT,
                Printer.MarginType.HARDWARE_MINIMUM
        );
        lineChart.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
        if (printerJob.showPrintDialog(primaryStage.getOwner()) && printerJob.printPage(pageLayout, lineChart))
            printerJob.endJob();
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
