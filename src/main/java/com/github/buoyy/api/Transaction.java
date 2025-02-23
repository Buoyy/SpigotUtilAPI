package com.github.buoyy.api;

public class Transaction {
    public final int amount;
    public final CurrencyType type;
    public final String message;
    public final TransactionResult result;

    public Transaction(int amount, CurrencyType type, String message, TransactionResult result) {
        this.amount = amount;
        this.type = type;
        this.message = message;
        this.result = result;
    }

    public boolean isSuccessful() {
        return this.result == TransactionResult.SUCCESS;
    }
    public enum TransactionResult {
        SUCCESS, FAILURE
    }
}
