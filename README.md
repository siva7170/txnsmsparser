# TxnSmsParser

A library to parse transaction SMS messages

## Gradle 

```gradle
implementation("in.depthshow:txnsmsparser:1.0.1")
```

## Example 1

```java
TxnSmsParser txnSmsParser=new TxnSmsParser();
txnSmsParser.parse("INR 2000 debited from A/c no. XX3423 on 05-02-19 07:27:11 IST at ECS PAY. Avl Bal- INR 2343.23.");
boolean isValid=txnSmsParser.IsValidTxnInfo();
if(isValid){
    TransactionInfo transactionInfo=txnSmsParser.getTransactionInfo();
}
else{
    // Invalid transaction message
}
//
```

## Example 2

```java
TxnSmsParser txnSmsParser=new TxnSmsParser();
txnSmsParser.parse("Morning, Jenna! Just a friendly nudge that it's leg day! We're pumped to see you at the gym at 10 AM sharp. Need to adjust? Reply with SHIFT to 70707. ");
boolean isValid=txnSmsParser.IsValidTxnInfo();
```
