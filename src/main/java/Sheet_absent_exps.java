import models.entities.Expert;

public class Sheet_absent_exps extends Expert {
        private String reason;

    public Sheet_absent_exps(String reason) {
        this.reason = reason;
    }

    public Sheet_absent_exps(Expert exp,String reason){
        super(exp.getName(),exp.getSex(),exp.getPhoneNumber());
        this.reason = reason;
    }
}
