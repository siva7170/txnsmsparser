package in.depthshow.txnsmsparser.classes;

public class TMessageType {
    private Object value;

    public TMessageType(String value) {
        this.value = value;
    }

    public TMessageType(String[] value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
