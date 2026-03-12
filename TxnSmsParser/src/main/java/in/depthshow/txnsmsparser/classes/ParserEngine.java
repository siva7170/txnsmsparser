package in.depthshow.txnsmsparser.classes;

import static in.depthshow.txnsmsparser.classes.Account.getAccount;
import static in.depthshow.txnsmsparser.classes.Balance.getBalance;
import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.UtilIndexOf;
import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.padCurrencyValue;
import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.processMessage;

import java.util.regex.Pattern;

import in.depthshow.txnsmsparser.enums.AccountType;
import in.depthshow.txnsmsparser.enums.IBalance;
import in.depthshow.txnsmsparser.enums.IBalanceKeyWordsType;

public class ParserEngine {
    public static String getTransactionAmount(String[] message) {
        //String[] processedMessage = getProcessedMessage(message, parser);
        int index = UtilIndexOf(message,"rs.");
        if (index == -1) {
            return "";
        }

        String money = message[index + 1];
        money = money.replace(",", "");

        if (Double.isNaN(Double.parseDouble(money))) {
            money = message[index + 2];
            money = (money != null) ? money.replace(",", "") : null;
            if (Double.isNaN(Double.parseDouble(money))) {
                return "";
            }
            return padCurrencyValue(money);
        }

        return padCurrencyValue(money);
    }

    public static String getTransactionType(Object message) {
        String creditPattern = "(?:credited|credit|deposited|added|received|refund)";
        String debitPattern = "(?:debited|debit|deducted|w\\/d)";
        String miscPattern = "(?:payment|spent|paid|used\\sat|charged|transaction\\son|transaction\\sfee|tran|booked|purchased)";

        String messageStr = (message instanceof String) ? (String) message : String.join(" ", (String[]) message);

        if (Pattern.compile(debitPattern, Pattern.CASE_INSENSITIVE).matcher(messageStr).find()) {
            return "debit";
        }
        if (Pattern.compile(creditPattern, Pattern.CASE_INSENSITIVE).matcher(messageStr).find()) {
            return "credit";
        }
        if (Pattern.compile(miscPattern, Pattern.CASE_INSENSITIVE).matcher(messageStr).find()) {
            return "debit";
        }
        return "";
    }

    public static TransactionInfo getTransactionInfo(String message) {
        if (message == null || !(message instanceof String)) {
            return new TransactionInfo(new AccountInfo(AccountType.ACCOUNT),new IBalance("", ""),new TransactionDetails("",""));
        }

        String[] processedMessage = processMessage(message);
        AccountInfo account = getAccount(processedMessage);
        String availableBalance = getBalance(processedMessage, IBalanceKeyWordsType.AVAILABLE);
        String transactionAmount = getTransactionAmount(processedMessage);
        //String bankName = getBankName(sender);
        boolean isValid = (availableBalance != null && !availableBalance.isEmpty() ? 1 : 0) +
                (transactionAmount != null && !transactionAmount.isEmpty() ? 1 : 0) +
                (account.getNumber() != null && !account.getNumber().isEmpty() ? 1 : 0) >= 2;
        String transactionType = isValid ? getTransactionType(processedMessage) : "";
        IBalance balance = new IBalance(availableBalance, null);

        if (account != null && account.getType() == AccountType.CARD) {
            balance.setOutstanding(getBalance(processedMessage, IBalanceKeyWordsType.OUTSTANDING));
        }

        return new TransactionInfo(account, balance, new TransactionDetails(transactionType,transactionAmount));
    }
}
