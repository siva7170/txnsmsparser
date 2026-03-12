package in.depthshow.txnsmsparser.enums;

public enum IAccountType {
    CARD("CARD"),
    WALLET("WALLET"),
    ACCOUNT("ACCOUNT");

    private final String value;

    IAccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
