package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class ControllerOutput {

    @FXML
    private TextArea output_text;

    private List<Sign> signs;

    private Node root;

    private String result;

    public void setSigns(List<Sign> signs) {
        this.signs = signs;

        result = "";

        System.out.println("SIZE " + signs.size());

        for (int i = 0; i < signs.size(); i++) {
            if (signs.get(i).getName() != null && !signs.get(i).getName().isEmpty()) {
                result += signs.get(i).getName();
                if (i != signs.size() - 1) {
                    if (signs.get(i + 1).getName() != null && !signs.get(i + 1).getName().isEmpty()) {
                        result += " - ";
                    }
                }
            }
        }

        result += "\n\n";
    }

    private void printTree(Node root, String offset) {
        if (root.isIgnoring()) {
            return;
        }

        if (!root.getTitle().equals("Root")) {
            result += offset + root.getTitle() + "\n";
        }

        for (Node rootChild : root.getChildren()) {
            if (!root.getTitle().equals("Root")) {
                printTree(rootChild, offset + "\t");
            } else {
                printTree(rootChild, offset);
            }
        }
    }

    public void setRoot(Node root) {
        this.root = root;
        printTree(this.root, "");
        output_text.setText(result);
    }

    @FXML
    void initialize() {
    }
}