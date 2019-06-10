package sample;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String title;
    private boolean isIgnoring;
    private List<Node> children;

    public Node(String title) {
        this.title = title;
        children = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIgnoring() {
        return isIgnoring;
    }

    public void setIgnoring(boolean ignoring) {
        isIgnoring = ignoring;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void add(Node node) {
        this.children.add(node);
    }
}