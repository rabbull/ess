import models.entities.Expert;

public class Sheet_absent_exps extends Expert {
        private String reason;

    public Sheet_absent_exps(String reason) {
        this.reason = reason;
    }

    public Sheet_absent_exps(String name, boolean sex, String phoneNumber, String reason) {
        super(name, sex, phoneNumber);
        this.reason = reason;
    }
}
