package in.depthshow.txnsmsparser.classes;

import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.extractBondedAccountNo;
import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.trimLeadingAndTrailingChars;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import in.depthshow.txnsmsparser.enums.AccountType;

public class Account {
    public static final CombinedWord[] combinedWords = {
            new CombinedWord(Pattern.compile("credit\\scard"), "c_card", AccountType.CARD),
            new CombinedWord(Pattern.compile("amazon\\spay"), "amazon_pay", AccountType.WALLET),
            new CombinedWord(Pattern.compile("uni\\scard"), "uni_card", AccountType.CARD),
            new CombinedWord(Pattern.compile("niyo\\scard"), "niyo", AccountType.ACCOUNT),
            new CombinedWord(Pattern.compile("slice\\scard"), "slice_card", AccountType.CARD),
            new CombinedWord(Pattern.compile("one\\s*card"), "one_card", AccountType.CARD),
    };

    public static final List<String> wallets = Arrays.asList("paytm", "simpl", "lazypay", "amazon_pay");
    //public static final String[] wallets = walletsLst.toArray(new String[0]);

    public static AccountInfo getCard(String[] message) {
        String combinedCardName = null;
        int cardIndex = -1;

        for (int i = 0; i < message.length; i++) {
            String word = message[i];
            if (word.equals("card") ||  Arrays.stream(combinedWords).anyMatch(w -> w.getType() == AccountType.CARD && w.getWord().equals(word))) {
                combinedCardName = word;
                cardIndex = i;
                break;
            }
        }

        AccountInfo card = new AccountInfo();
        card.setType(null);

        if (cardIndex != -1) {
            card.setNumber(message[cardIndex + 1]);
            card.setType(AccountType.CARD);

            if (Double.isNaN(Double.parseDouble(card.getNumber()))) {
                return new AccountInfo(combinedCardName != null ? card.getType() : null, combinedCardName);
            }
            return card;
        }

        return new AccountInfo(null, null);
    }

    public static String getBankName(String sender, List<BankKeyword> bankKeywords) {
        String bankName = "";
        for (BankKeyword item : bankKeywords) {
            if (sender.toLowerCase().contains(item.getKey().toLowerCase())) {
                bankName = item.getBankName();
            }
        }
        return bankName;
    }

    public static AccountInfo getAccount(String[] message) {
        //List<String> processedMessage = getProcessedMessage(message, parser);
        int accountIndex = -1;
        AccountInfo account = new AccountInfo();
        account.setType(null);
        account.setNumber("");

        for (int index = 0; index < message.length; index++) {
            String word = message[index];
            if (word.equals("ac")) {
                if (index + 1 < message.length) {
                    String accountNo = trimLeadingAndTrailingChars(message[index + 1]);
                    if (Double.isNaN(Double.valueOf(accountNo))) {
                        continue;
                    } else {
                        accountIndex = index;
                        account.setType(AccountType.ACCOUNT);
                        account.setNumber(accountNo);
                        break;
                    }
                } else {
                    continue;
                }
            } else if (word.contains("ac")) {
                String extractedAccountNo = extractBondedAccountNo(word);
                if (extractedAccountNo.equals("")) {
                    continue;
                } else {
                    accountIndex = index;
                    account.setType(AccountType.ACCOUNT);
                    account.setNumber(extractedAccountNo);
                    break;
                }
            }
        }

        if (accountIndex == -1) {
            account = getCard(message);
        }

        if (account.getType() == null) {
            String wallet = Arrays.stream(message)
                    .filter(word -> wallets.contains(word))
                    .findFirst()
                    .orElse(null);
            if (wallet != null) {
                account.setType(AccountType.WALLET);
                account.setName(wallet);
            }
        }

        if (account.getType() == null) {
            CombinedWord specialAccount = Arrays.stream(combinedWords)
                    .filter(word -> word.getType() == AccountType.ACCOUNT)
                    .filter(w -> Arrays.asList(message).contains(w.getWord()))
                    .findFirst()
                    .orElse(null);
            if (specialAccount != null) {
                account.setType(specialAccount.getType());
                account.setName(specialAccount.getWord());
            }
        }

        return account;
    }

    class BankKeyword {
        private String key;
        private String bankName;

        public BankKeyword(String key, String bankName) {
            this.key = key;
            this.bankName = bankName;
        }

        public String getKey() {
            return key;
        }

        public String getBankName() {
            return bankName;
        }
    }
}
