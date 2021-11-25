public enum ConditionOfTheObject {
    COMMAND("1","3");

    private String s;
    private String i;

    ConditionOfTheObject(String s,String i) {
        this.i = i;
        this.s = s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public String getI() {
        return i;
    }
}
