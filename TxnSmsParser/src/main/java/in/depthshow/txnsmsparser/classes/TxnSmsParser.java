package in.depthshow.txnsmsparser.classes;

import java.util.Objects;

public class TxnSmsParser {
    private String message;
    private TransactionInfo txnInfo;
    public TxnSmsParser(){

    }

    public void parse(String message) {
        this.message=message;
        txnInfo=ParserEngine.getTransactionInfo(this.message);
    }

    public TransactionInfo getTransactionInfo() {
        return ParserEngine.getTransactionInfo(message);
    }

    public boolean IsValidTxnInfo(){
        return !Objects.equals(txnInfo.transactionDetails.transactionType, "");
    }
}
