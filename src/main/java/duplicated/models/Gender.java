<<<<<<< HEAD:src/main/java/models/Gender.java
package models;

//import com.sun.org.apache.xpath.internal.operations.Bool;
=======
package duplicated.models;
>>>>>>> aa5d25abb0ab7dc7576dd52027009cb7a3d49a15:src/main/java/duplicated/models/Gender.java

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
