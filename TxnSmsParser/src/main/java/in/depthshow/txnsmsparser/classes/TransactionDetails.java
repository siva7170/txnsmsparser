package in.depthshow.txnsmsparser.classes;

public class TransactionDetails {
    public String transactionType;
    public String transactionAmount;

    public TransactionDetails(String transactionType, String transactionAmount){
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }
}
