package in.depthshow.txnsmsparser.classes;

import java.util.regex.Pattern;

import in.depthshow.txnsmsparser.enums.AccountType;

public class CombinedWord {
    private Pattern regex;
    private String word;
    private AccountType type;

    public CombinedWord(Pattern regex, String word, AccountType type) {
        this.regex = regex;
        this.word = word;
        this.type = type;
    }

    public Pattern getRegex() {
        return regex;
    }

    public String getWord() {
        return word;
    }

    public AccountType getType() {
        return type;
    }
}
