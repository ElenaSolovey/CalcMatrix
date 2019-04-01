package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    private ComboBox combo;
    @FXML
    private GridPane firstGP;
    @FXML
    private GridPane secondGP;
    @FXML
    private GridPane resGP;
    @FXML
    private TextField textField;
    @FXML
    private Label label;

    @FXML
    public void initialize() {
        combo.getSelectionModel().selectFirst();
    }

    @FXML
    public void onClickMethod(){
        double number = 1;
        Matrix firstMatrix = new Matrix(3);
        Matrix secondMatrix = new Matrix(3);
        for (int i = 0; i < firstMatrix.getSize(); i++)
            for (int j = 0; j < firstMatrix.getSize(); j++) {
                TextField text = (TextField) firstGP.getChildren().get(3 * i + j);
                try {
                    firstMatrix.setIJ(Double.parseDouble(text.getText()), i, j);
                } catch (Exception e) {
                    firstMatrix.setIJ(0, i, j);
                    text.setText("0");
                }
            }
        if (combo.getValue().equals("Вычесть") ||
            combo.getValue().equals("Сложить") ||
            combo.getValue().equals("Умножить")) {
            for (int i = 0; i < firstMatrix.getSize(); i++)
                for (int j = 0; j < firstMatrix.getSize(); j++) {
                    TextField text = (TextField) secondGP.getChildren().get(3 * i + j);
                    try {
                        secondMatrix.setIJ(Double.parseDouble(text.getText()), i, j);
                    } catch (Exception e) {
                        secondMatrix.setIJ(0, i, j);
                        text.setText("0");
                    }
                }
        } else if (combo.getValue().equals("В степень") ||
            combo.getValue().equals("Умножить на число")) {
            try {
                number = Double.parseDouble(textField.getText());
            }
            catch (Exception e) {
                textField.setText("1");
                number = 1;
            }
        }

        Matrix result = new Matrix(3);
        if (combo.getValue().equals("Умножить")){
            result = firstMatrix.mult(secondMatrix);
        } else if (combo.getValue().equals("Сложить")){
            result = firstMatrix.plus(secondMatrix);
        } else if (combo.getValue().equals("Вычесть")){
            result = firstMatrix.minus(secondMatrix);
        } else if (combo.getValue().equals("В степень")){
            result = firstMatrix.power((int)number);
            textField.setText("" + (int)number);
        } else if (combo.getValue().equals("Транспонировать")){
            result = firstMatrix.transpose();
        } else if (combo.getValue().equals("Обратная")){
            result = firstMatrix.inverse();
        } else if (combo.getValue().equals("Умножить на число")){
            result = firstMatrix.mult(number);
        }
        if (combo.getValue().equals("Определитель")){
            label.setText("" + firstMatrix.det());
        } else {
            if (result != null) {
                for (int i = 0; i < result.getSize(); i++)
                    for (int j = 0; j < result.getSize(); j++) {
                        Label label = (Label) resGP.getChildren().get(3 * i + j);
                        label.setText("" + result.getIJ(i, j));
                    }
            } else {
                label.setText("Определитель матрицы равен нулю,\nзначит обратной матрицы не существует");
                label.setVisible(true);
                resGP.setVisible(false);
            }
        }
    }

    @FXML
    public void onClickMethod2(){
        if (combo.getValue().equals("В степень") ||
            combo.getValue().equals("Умножить на число")) {
            secondGP.setVisible(false);
            textField.setVisible(true);
            label.setVisible(false);
            resGP.setVisible(true);
        } else if (combo.getValue().equals("Транспонировать") ||
            combo.getValue().equals("Обратная")){
            secondGP.setVisible(false);
            textField.setVisible(false);
            label.setVisible(false);
            resGP.setVisible(true);
        } else if (combo.getValue().equals("Определитель")) {
            label.setVisible(true);
            resGP.setVisible(false);
            secondGP.setVisible(false);
            textField.setVisible(false);
        } else {
            secondGP.setVisible(true);
            textField.setVisible(false);
            label.setVisible(false);
            resGP.setVisible(true);
        }
    }
}