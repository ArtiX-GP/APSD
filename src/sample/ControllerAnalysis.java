package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerAnalysis {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label_prizn_two;

    @FXML
    private Label label_prizn_three;

    @FXML
    private Label label_sv_one;

    @FXML
    private Label label_sv_two;

    @FXML
    private Label label_sv_three;

    @FXML
    private Button button_analis_yes;

    @FXML
    private Button button_analis_no;

    @FXML
    private Label label_prizn_one;

    @FXML
    private Label label_prizn_four;

    @FXML
    private Label label_sv_four;

    private List<Sign> signs;

    private int positions[] = {0, 0, 0, 0};

    private Node root;

    private int depth = 2;

    private int maxDepth = 2;

    public void setSigns(List<Sign> signs) throws Exception {
        if (signs == null || signs.size() < 2) {
            throw new Exception("Signs doesn't set.");
        }

        this.signs = signs;

        /*this.signs = new ArrayList<>();

        ArrayList<String> props = new ArrayList<>();
        props.add("Кадры");
        props.add("Финансовый отдел");
        props.add("Отдел планирования");
        props.add("Отдел прогнозирования");
        this.signs.add(new Sign("Объект управления", props));

        props = new ArrayList<>();
        props.add("Прогнозирование");
        props.add("Планирование");
        props.add("Набор персонала");
        props.add("Финансирование");
        this.signs.add(new Sign("Цикл управления", props));

        props = new ArrayList<>();
        props.add("Финансист");
        props.add("Кадровый работник");
        props.add("Планировщик");
        props.add("Директор");
        this.signs.add(new Sign("Подразделение", props));*/

        System.out.println("Total number of combinations: " + getCombinations());

        root = getRoot();

        now(depth);

        printTree(root, "");
    }

    private int getCombinations() {
        int res = 1;

        for (int i = 0; i < signs.size(); i++) {
            if (signs.get(i).getProps() != null && signs.get(i).getProps().size() > 0) {
                res *= signs.get(i).getProps().size();
            }
        }

        if (res == 1)
            res = 0;

        return res;
    }

    private Node getRoot() {
        Node root = new Node("Root");

        for (String prop_0 : signs.get(0).getProps()) {
            root.add(new Node(prop_0));
        }

        for (Node rootChild : root.getChildren()) {
            // На каждое свойство первого признака, добавить все свойства второго признака.
            for (String prop_1 : signs.get(1).getProps()) {
                rootChild.add(new Node(prop_1));
            }

            if (isSignCorrect(2)) {
                maxDepth = 3;
                // На каждое свойство второго признака добавляем свойства третьего признака.
                for (Node rootChild2 : rootChild.getChildren()) {
                    for (String prop_2 : signs.get(2).getProps()) {
                        rootChild2.add(new Node(prop_2));
                    }

                    if (isSignCorrect(3)) {
                        maxDepth = 4;
                        // На каждое свойство третьего признака добавляем свойства четвертого признака.
                        for (Node rootChild3 : rootChild2.getChildren()) {
                            for (String prop_3 : signs.get(3).getProps()) {
                                rootChild3.add(new Node(prop_3));
                            }
                        }
                    }
                }
            }
        }

        return root;
    }

    private boolean isSignCorrect(int pos) {
        if (pos < 0 || pos >= signs.size())
            return false;
        return signs.get(pos) != null && signs.get(pos).getProps() != null && signs.get(pos).getProps().size() > 0;
    }

    private int it = 0;

    private void printTree(Node root, String offset) {
        if (root.isIgnoring()) {
            return;
        }

        System.out.println(String.format("%04d", it) + ") " + offset + root.getTitle());

        boolean can = true;

        for (Node rootChild : root.getChildren()) {
            can = false;
            printTree(rootChild, offset + "\t");
        }

        if (can) {
            it++;
        }
    }

    @FXML
    void initialize() {
        button_analis_no.setOnAction(event -> {
            System.out.println("Press NO");
            now(depth, true);
            updatePositions(depth);
            now(depth);
        });

        button_analis_yes.setOnAction(event -> {
            System.out.println("Press Yes");
            updatePositions(depth);
            now(depth);
        });
    }

    private void now(int depth) {
        now(depth, false);
    }

    private void now(int localDepth, boolean ignore) {
        for (int i = 0; i < localDepth; i++) {
            writeSign(i, i);
        }

        Node firstLevel = root.getChildren().get(positions[0]);
        writeProperty(0, firstLevel.getTitle());

        Node secondLevel = firstLevel.getChildren().get(positions[1]);
        if (depth == 2) {
            secondLevel.setIgnoring(ignore);
        }

        if (secondLevel.isIgnoring() && !ignore) {
            System.out.print("Ignored: ");
            System.out.println(secondLevel.getTitle() + " > " + secondLevel.isIgnoring());
            updatePositions(2);
            now(localDepth);
        }
        writeProperty(1, secondLevel.getTitle());

        if (localDepth >= 3) {
            Node thirdLevel = secondLevel.getChildren().get(positions[2]);
            if (thirdLevel.isIgnoring() && !ignore) {
                updatePositions(3);
                now(localDepth);
            }

            if (localDepth == 3) {
                thirdLevel.setIgnoring(ignore);
            }
            writeProperty(2, thirdLevel.getTitle());

            if (localDepth >= 4) {
                Node fourthLevel = thirdLevel.getChildren().get(positions[3]);
                if (fourthLevel.isIgnoring() && !ignore) {
                    updatePositions(4);
                    now(localDepth);
                }

                if (localDepth == 4) {
                    fourthLevel.setIgnoring(ignore);
                }
                writeProperty(3, fourthLevel.getTitle());
            }
        }
    }

    private void updatePositions(int localDepth) {
        positions[localDepth - 1]++;

        if (isSignCorrect(3)) {
            if (positions[3] >= signs.get(3).getProps().size()) {
                positions[3] = 0;
                positions[2]++;
            }
        }

        if (isSignCorrect(2)) {
            if (positions[2] >= signs.get(2).getProps().size()) {
                positions[2] = 0;
                positions[1]++;
            }
        }

        if (positions[1] >= signs.get(1).getProps().size()) {
            positions[1] = 0;
            positions[0]++;
        }

        if (positions[0] >= root.getChildren().size()) {
            if (depth == maxDepth) {
                depth = -1;

                // Исключение первых признаков, если все свойства вторых признаков помечены как недопустимые.
                for (Node firstLevel : root.getChildren()) {
                    firstLevel.setIgnoring(true);
                    for (Node secondLevel : firstLevel.getChildren()) {
                        if (!secondLevel.isIgnoring()) {
                            firstLevel.setIgnoring(false);
                            break;
                        }
                    }
                }

                System.out.println("Show result!");
                //printTree(root, "");
                button_analis_no.getScene().getWindow().hide();
                FXMLLoader loaderanalis = new FXMLLoader();
                loaderanalis.setLocation(getClass().getResource("/sample/output.fxml"));
                try {
                    loaderanalis.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent rootanalis = loaderanalis.getRoot();

                Stage stageanalis = new Stage();
                stageanalis.setScene(new Scene(rootanalis));

                try {
                    ControllerOutput controllerAnalysis = loaderanalis.getController();
                    controllerAnalysis.setSigns(signs);
                    controllerAnalysis.setRoot(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                stageanalis.show();

                positions = new int[]{0, 0, 0, 0};
                return;
            } else {
                depth++;
            }

            positions = new int[]{0, 0, 0, 0};
        }

        System.out.println(Arrays.toString(positions));
        now(depth);
    }

    private void writeSigns() {
        writeSign(0, 0);
        writeSign(1, 1);
        writeSign(2, 2);
        writeSign(3, 3);
    }

    private void writeSign(int line, int pos) {
        Label currentArea;
        switch (line) {
            case 0:
                currentArea = label_prizn_one;
                break;
            case 1:
                currentArea = label_prizn_two;
                break;
            case 2:
                currentArea = label_prizn_three;
                break;
            default:
                currentArea = label_prizn_four;
                break;
        }

        if (signs.get(pos) != null && signs.get(pos).getName() != null)
            currentArea.setText(signs.get(pos).getName());
    }

    private void writeProperty(int line, String v) {
        Label currentArea;
        switch (line) {
            case 0:
                currentArea = label_sv_one;
                break;
            case 1:
                currentArea = label_sv_two;
                break;
            case 2:
                currentArea = label_sv_three;
                break;
            default:
                currentArea = label_sv_four;
                break;
        }

        currentArea.setText(v);
    }

    private void writeProperty(int line, int signPos, int propPos) {
        Label currentArea;
        switch (line) {
            case 0:
                currentArea = label_sv_one;
                break;
            case 1:
                currentArea = label_sv_two;
                break;
            case 2:
                currentArea = label_sv_three;
                break;
            default:
                currentArea = label_sv_four;
                break;
        }

        if (signs.get(signPos) != null && signs.get(signPos).getProps() != null &&
                signs.get(signPos).getProps().size() > 0 &&
                signs.get(signPos).getProps().get(propPos) != null) {
            currentArea.setText(signs.get(signPos).getProps().get(propPos));
            System.out.println(signs.get(signPos));
        }
    }

}