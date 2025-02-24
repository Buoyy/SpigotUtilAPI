package com.github.buoyy.api.economy;

/**
 * Represents a transaction in-game.
 * Useful for knowing information of the performed transaction
 * like amount handled, currency handled and error message (if any).
 */
@SuppressWarnings("unused")
public class Transaction {
    public final int amount;
    public final int balance;
    public final CurrencyType type;
    public final String message;
    public final TransactionResult result;

    /**
     * The constructor for this class.
     * @param amount The amount added or subtracted
     * @param type The currency type manipulated
     * @param message The error message (usually empty if
     *                there was no error)
     * @param result The TransactionResult (maybe SUCCESS or FAILURE)
     */
    public Transaction(int amount, CurrencyType type, int balance, String message, TransactionResult result) {
        this.amount = amount;
        this.type = type;
        this.message = message;
        this.balance = balance;
        this.result = result;
    }

    public boolean isSuccessful() {
        return this.result == TransactionResult.SUCCESS;
    }
    public enum TransactionResult {
        SUCCESS, FAILURE
    }
}
