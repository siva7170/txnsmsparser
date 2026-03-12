package in.depthshow.txnsmsparser.classes;

import in.depthshow.txnsmsparser.enums.AccountType;

public class AccountInfo {
    private AccountType type;
    private String number;
    private String name;

    public AccountInfo(AccountType type, String number, String name){
        this.type = type;
        this.number = number;
        this.name = name;
    }

    public AccountInfo(AccountType type,String name){
        this.type = type;
        this.name = name;
    }

    public AccountInfo(AccountType type){
        this.type = type;
        this.name = name;
    }

    public AccountInfo(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public AccountType getType() {
        return type;
    }
}
