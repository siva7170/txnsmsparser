package in.depthshow.txnsmsparser.enums;

public class IBalance {
    private String available;
    private String outstanding;

    public IBalance(String available, String outstanding) {
        this.available = available;
        this.outstanding = outstanding;
    }

    public String getAvailable() {
        return available;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }
}
