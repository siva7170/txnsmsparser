package in.depthshow.txnsmsparser.classes;

import static in.depthshow.txnsmsparser.classes.TxnSmsUtils.padCurrencyValue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.depthshow.txnsmsparser.enums.IBalanceKeyWordsType;

public class Balance {
    public static final List<String> availableBalanceKeywords = Arrays.asList(
            "avbl bal",
            "available balance",
            "available limit",
            "available credit limit",
            "avbl. credit limit",
            "limit available",
            "a/c bal",
            "ac bal",
            "available bal",
            "avl bal",
            "updated balance",
            "total balance",
            "new balance",
            "bal",
            "avl lmt",
            "available"
    );
    public static final List<String> outstandingBalanceKeywords = Arrays.asList(
            "outstanding"
    );
    public static String getBalance(String[] message, IBalanceKeyWordsType keyWordType) {
        if (keyWordType == null) {
            keyWordType = IBalanceKeyWordsType.AVAILABLE;
        }
        //List<String> processedMessage = getProcessedMessage(message);
        String messageString = String.join(" ", message);
        int indexOfKeyword = -1;
        String balance = "";
        String[] balanceKeywords = (keyWordType == IBalanceKeyWordsType.AVAILABLE) ? availableBalanceKeywords.toArray(new String[0]) : outstandingBalanceKeywords.toArray(new String[0]);

        for (String word : balanceKeywords) {
            indexOfKeyword = messageString.indexOf(word);
            if (indexOfKeyword != -1) {
                indexOfKeyword += word.length();
                break;
            }
        }

        int index = indexOfKeyword;
        int indexOfRs = -1;
        String nextThreeChars = messageString.substring(index, Math.min(index + 3, messageString.length()));
        index += 3;

        while (index < messageString.length()) {
            nextThreeChars = nextThreeChars.substring(1) + messageString.charAt(index);
            if (nextThreeChars.equals("rs.")) {
                indexOfRs = index + 2;
                break;
            }
            index += 1;
        }

        if (indexOfRs == -1) {
            balance = findNonStandardBalance(messageString,IBalanceKeyWordsType.AVAILABLE);
            return balance != null && !balance.isEmpty() ? padCurrencyValue(balance) : "";
        }

        balance = extractBalance(indexOfRs, messageString, messageString.length());
        return padCurrencyValue(balance);
    }

    public static String extractBalance(int index, String message, int length) {
        StringBuilder balance = new StringBuilder();
        boolean sawNumber = false;
        int invalidCharCount = 0;
        char charAt;
        int start = index;

        while (start < length) {
            charAt = message.charAt(start);
            if (charAt >= '0' && charAt <= '9') {
                sawNumber = true;
                balance.append(charAt);
            } else if (sawNumber) {
                if (charAt == '.') {
                    if (invalidCharCount == 1) {
                        break;
                    } else {
                        balance.append(charAt);
                        invalidCharCount++;
                    }
                } else if (charAt != ',') {
                    break;
                }
            }
            start++;
        }
        return balance.toString();
    }

    public static String findNonStandardBalance(String message, IBalanceKeyWordsType keyWordType) {
        if (keyWordType == null) {
            keyWordType = IBalanceKeyWordsType.AVAILABLE;
        }

        String[] balanceKeywords = (keyWordType == IBalanceKeyWordsType.AVAILABLE) ? availableBalanceKeywords.toArray(new String[0]) : outstandingBalanceKeywords.toArray(new String[0]);
        String balRegex = String.join("|", balanceKeywords).replace("/", "\\/");
        String regexString = String.format("%s\\s*[\\d]+\\.*[\\d]*", balRegex);
        Pattern regex = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(message);

        if (matcher.find()) {
            String[] parts = matcher.group().split(" ");
            String balance = parts[parts.length - 1]; // return only first match
            try {
                return Double.isNaN(Double.parseDouble(balance)) ? "" : balance;
            } catch (NumberFormatException e) {
                return "";
            }
        }
        return "";
    }
}
