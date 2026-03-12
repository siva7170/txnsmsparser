package in.depthshow.txnsmsparser.interfaces;

public interface ITransactionInfo {
    IAccountInfo getAccount();
    String getTransactionAmount();
    String getBalance();
    String getTransactionType();
}
