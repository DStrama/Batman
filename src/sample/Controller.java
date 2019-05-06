
package sample;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {


    @FXML
    private Canvas canvas;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField textField;

    @FXML
    private Label resultLabel;

    private DrawerTask task;

    @FXML
    private void handleRunStart() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

    }
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        double N;
        if (textField.getText().length() > 0) {
            try {
                N = Double.parseDouble(textField.getText());
                if (N > 0) {
                    task = new DrawerTask(gc, N);
                    progressBar.progressProperty().bind(task.progressProperty());
                    task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            resultLabel.setText("Result:"+ Double.toString((Double) task.getValue()));

                        }
                    });
                    new Thread(task).start();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Value is to low!");
                    alert.setHeaderText("Pass correct value!");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Value is valid!");
                alert.setHeaderText("Pass correct value!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Have to pass value");
            alert.setHeaderText("Pass correct value!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSTOP() {
        if (task!= null) {
            task.cancel();
        }
    }

}
