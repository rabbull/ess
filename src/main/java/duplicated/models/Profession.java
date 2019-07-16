package duplicated.models;

import java.util.ArrayList;
import java.util.List;

public class Profession {
    private int level;
    private String code;
    private String name;

    private Profession father;
    private List<Profession> sons;

    public Profession(String code, String name) {
        this.level = 0;
        this.code = code;
        this.name = name;
        this.sons = new ArrayList<>();
    }

    public Profession(String code, String name, Profession father) {
        this(code, name);
        this.level = father.level + 1;
        father.sons.add(this);
        this.father = father;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profession getFather() {
        return father;
    }

    public void setFather(Profession father) {
        this.father = father;
    }

    public List<Profession> getSons() {
        return sons;
    }

    public String[] toStringArray() {
        return new String[]{Integer.toString(level), name, father.name};
    }
}
