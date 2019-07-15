package models;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Gender {
    public boolean isMale;

    public Gender(String gender) throws Exception {
        if (gender.equals("女")) {
            isMale = false;
        } else if (gender.equals("男")) {
            isMale = true;
        } else {
            throw new Exception("bad gender");
        }
    }

    @Override
    public String toString() {
        return isMale ? "男" : "女";
    }
}
