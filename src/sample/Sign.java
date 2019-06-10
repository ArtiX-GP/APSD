package sample;

import java.util.ArrayList;
import java.util.List;

public class Sign {
    private String name;
    private List<String> props;

    public Sign() {
        this.name = "";
        this.props = new ArrayList<>();
    }

    public Sign(String name, List<String> props) {
        this.name = name;
        this.props = props;
    }

    public Sign(String name) {
        this.name = name;
        this.props = new ArrayList<>();
    }

    public void add(String prop) {
        if (prop == null) {
            return;
        }

        if (this.props != null)
            this.props.add(prop);
        else
            this.props = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(name + "\n");

        if (props != null) {
            for (String s : props) {
                res.append(s).append("\n");
            }
        } else {
            res.append(" props none;");
        }

        return res.toString();
    }
}
