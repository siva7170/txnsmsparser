package in.depthshow.txnsmsparser.classes;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import in.depthshow.txnsmsparser.enums.AccountType;

public class TxnSmsUtils {
    public static String trimLeadingAndTrailingChars(String str) {
        char first = str.charAt(0);
        char last = str.charAt(str.length() - 1);
        String finalStr = Character.isDigit(last) ? str : str.substring(0, str.length() - 1);
        finalStr = Character.isDigit(first) ? finalStr : finalStr.substring(1);
        return finalStr;
    }

    public static String extractBondedAccountNo(String accountNo) {
        String strippedAccountNo = accountNo.replace("ac", "");
        return strippedAccountNo.chars().allMatch(Character::isDigit) ? strippedAccountNo : "";
    }

    public static String[] getProcessedMessage(Object message) {
        String[] processedMessage;

        if (message instanceof String) {
            processedMessage = processMessage((String) message);
        } else {
            processedMessage = (String[]) message;
        }

        return processedMessage;
    }

    public static String padCurrencyValue(String val) {
        String[] parts = val.split("\\.");
        String lhs = parts[0];
        String rhs = (parts.length > 1) ? parts[1] : "";
        return lhs + "." + String.format("%-2s", rhs).replace(' ', '0');
    }

//    public static String[] processMessage(String message, Map<Pattern, String> parser) {
//        // Convert to lower case
//        String messageStr = message.toLowerCase();
//
//        for (Map.Entry<Pattern, String> entry : parser.entrySet()) {
//            messageStr = entry.getKey().matcher(messageStr).replaceAll(entry.getValue());
//        }
//
//        // Combine words (assuming combinedWords is a List of Word objects with regex and word)
////        List<CombinedWord> combinedWords = new ArrayList<>(); // Initialize with actual data
//        List<CombinedWord> combinedWords = Arrays.asList(
//                new CombinedWord(Pattern.compile("credit\\s+card", Pattern.CASE_INSENSITIVE), "c_card", AccountType.CARD),
//                new CombinedWord(Pattern.compile("amazon\\s+pay", Pattern.CASE_INSENSITIVE), "amazon_pay", AccountType.WALLET),
//                new CombinedWord(Pattern.compile("uni\\s+card", Pattern.CASE_INSENSITIVE), "uni_card", AccountType.CARD),
//                new CombinedWord(Pattern.compile("niyo\\s+card", Pattern.CASE_INSENSITIVE), "niyo", AccountType.ACCOUNT),
//                new CombinedWord(Pattern.compile("slice\\s+card", Pattern.CASE_INSENSITIVE), "slice_card", AccountType.CARD)
//        );
//        for (CombinedWord word : combinedWords) {
//            messageStr = word.getRegex().matcher(messageStr).replaceAll(word.getWord());
//        }
//
//        return Arrays.stream(messageStr.split(" "))
//                .filter(str -> !str.isEmpty())
//                .toArray(String[]::new);
//    }

    public static String[] processMessage(String message) {
        String messageStr = message.toLowerCase();
        messageStr = messageStr.replaceAll("!", "");
        messageStr = messageStr.replaceAll(":", " ");
        messageStr = messageStr.replaceAll("/", "");
        messageStr = messageStr.replaceAll("=", " ");
        messageStr = messageStr.replaceAll("[{}]", " ");
        messageStr = messageStr.replaceAll("\\n", " ");
        messageStr = messageStr.replaceAll("\\r", " ");
        messageStr = messageStr.replaceAll("ending ", "");
        messageStr = messageStr.replaceAll("x|[*]", "");
        messageStr = messageStr.replaceAll("is ", "");
        messageStr = messageStr.replaceAll("with ", "");
        messageStr = messageStr.replaceAll("no. ", "");
        messageStr = messageStr.replaceAll("\\bac\\b|\\bacct\\b|\\baccount\\b", "ac");
        messageStr = messageStr.replaceAll("rs(?=\\w)", "rs. ");
        messageStr = messageStr.replaceAll("rs ", "rs. ");
        messageStr = messageStr.replaceAll("inr(?=\\w)", "rs. ");
        messageStr = messageStr.replaceAll("inr ", "rs. ");
        messageStr = messageStr.replaceAll("rs. ", "rs.");
        messageStr = messageStr.replaceAll("rs\\.(?=\\w)", "rs. ");
        messageStr = messageStr.replaceAll("debited", " debited ");
        messageStr = messageStr.replaceAll("credited", " credited ");

        List<CombinedWord> combinedWords = Arrays.asList(
                new CombinedWord(Pattern.compile("credit\\s+card", Pattern.CASE_INSENSITIVE), "c_card", AccountType.CARD),
                new CombinedWord(Pattern.compile("amazon\\s+pay", Pattern.CASE_INSENSITIVE), "amazon_pay", AccountType.WALLET),
                new CombinedWord(Pattern.compile("uni\\s+card", Pattern.CASE_INSENSITIVE), "uni_card", AccountType.CARD),
                new CombinedWord(Pattern.compile("niyo\\s+card", Pattern.CASE_INSENSITIVE), "niyo", AccountType.ACCOUNT),
                new CombinedWord(Pattern.compile("slice\\s+card", Pattern.CASE_INSENSITIVE), "slice_card", AccountType.CARD)
        );
        for (CombinedWord word : combinedWords) {
            messageStr = word.getRegex().matcher(messageStr).replaceAll(word.getWord());
        }
        return Arrays.stream(messageStr.split(" "))
                .filter(str -> !str.isEmpty())
                .toArray(String[]::new);
    }


    public static int UtilIndexOf(String[] strarr,String searchStr){
        int indAt=-1;
        for (int i = 0; i < strarr.length; i++) {
            if (strarr[i].equals(searchStr))
            {
                indAt = i;
                break;
            }
        }
        return indAt;
    }
}
