package models;

public class StringModel extends Model {
    protected String string;

    public StringModel() {}

    public StringModel(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
