package pow.jie.test.callregister;

public class CallLogInfo {
    private String name;
    private String number;
    private String date;
    private int type;

    public CallLogInfo(String name, String number, String date, int type) {
        super();
        this.name = name;
        this.number = number;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public int getType() {
        return type;
    }

}