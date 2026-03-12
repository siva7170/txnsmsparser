package in.depthshow.txnsmsparser.classes;

import in.depthshow.txnsmsparser.enums.IBalance;

public class TransactionInfo {
    private AccountInfo account;
    private IBalance balance;
    public TransactionDetails transactionDetails;

    public TransactionInfo(AccountInfo account, IBalance balance, TransactionDetails transactionDetails) {
        this.account = account;
        this.balance = balance;
        this.transactionDetails = transactionDetails;
    }
}
