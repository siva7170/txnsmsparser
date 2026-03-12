package in.depthshow.txnsmsparser.classes;

public class TxnSmsParser {
    private String message;
    public TxnSmsParser(){

    }

    public void parse(String message) {
        this.message=message;
    }

    public TransactionInfo getTransactionInfo() {
        return ParserEngine.getTransactionInfo(message);
    }
}
