package sample;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_one;

    @FXML
    private TextArea sign_one;

    @FXML
    private TextArea property_4;

    @FXML
    private TextArea property_3;

    @FXML
    private TextArea property_2;

    @FXML
    private TextArea property_1;

    @FXML
    private TextArea sign_two;

    @FXML
    private TextArea property_8;

    @FXML
    private TextArea property_7;

    @FXML
    private TextArea property_6;

    @FXML
    private TextArea property_5;

    @FXML
    private Button button_two;

    @FXML
    private TextArea sign_tree;

    @FXML
    private TextArea property_12;

    @FXML
    private TextArea property_11;

    @FXML
    private TextArea property_10;

    @FXML
    private TextArea property_9;

    @FXML
    private Button button_free;

    @FXML
    private TextArea sign_four;

    @FXML
    private TextArea property_16;

    @FXML
    private TextArea property_15;

    @FXML
    private TextArea property_14;

    @FXML
    private TextArea property_13;

    @FXML
    private Button analisys_button;

    private List<Sign> signs = new ArrayList<>(4);

    @FXML
    private Button button_four;

    @FXML
    public void initialize() {
        for (int i = 0; i < 4; i++) {
            signs.add(new Sign());
        }

        button_one.setOnAction(event -> {
            if (sign_one.getText().isEmpty())
                return;

            signs.get(0).setName(sign_one.getText());

            List<String> attrs = getData(property_1, property_2,
                    property_3, property_4);

            signs.get(0).setProps(attrs);

            System.out.println(signs.get(0));
        });

        button_two.setOnAction(event -> {
            if (sign_two.getText().isEmpty())
                return;

            signs.get(1).setName(sign_two.getText());

            List<String> attrs = getData(property_5, property_6,
                    property_7, property_8);

            signs.get(1).setProps(attrs);

            System.out.println(signs.get(1));
        });

        button_free.setOnAction(event -> {
            if (sign_tree.getText().isEmpty())
                return;

            signs.get(2).setName(sign_tree.getText());

            List<String> attrs = getData(property_9, property_10,
                    property_11, property_12);

            signs.get(2).setProps(attrs);

            System.out.println(signs.get(2));
        });

        button_four.setOnAction(event -> {
            if (sign_four.getText().isEmpty())
                return;

            signs.get(3).setName(sign_four.getText());

            List<String> attrs = getData(property_13, property_14,
                    property_15, property_16);

            signs.get(3).setProps(attrs);

            System.out.println(signs.get(3));
        });
        analisys_button.setOnAction(event -> {
            analisys_button.getScene().getWindow().hide();
             // инициализируем переход на другую страницу
            FXMLLoader loaderanalis = new FXMLLoader();
            loaderanalis.setLocation(getClass().getResource("/sample/analisys.fxml"));
//            загрузка файла
            try {
                loaderanalis.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent rootanalis = loaderanalis.getRoot();

            Stage stageanalis = new Stage();
            stageanalis.setScene(new Scene(rootanalis));

            try {
                ControllerAnalysis controllerAnalysis = loaderanalis.getController();
                controllerAnalysis.setSigns(signs);
            } catch (Exception e) {
                e.printStackTrace();
            }

            stageanalis.show();
            System.out.println("Вы нажали кнопку Анализ!");
        });
    }

    private List<String> getData(TextArea... areas) {
        List<String> strings = new ArrayList<>();
        for (TextArea area : areas) {
            if (area.getText().isEmpty())
                continue;

            strings.add(area.getText());
        }
        return strings;
    }

}