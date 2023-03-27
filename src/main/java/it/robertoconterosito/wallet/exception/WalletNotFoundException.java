package it.robertoconterosito.wallet.exception;

public class WalletNotFoundException extends Exception {
    public WalletNotFoundException(Long id) {
        super(String.format("Wallet %s not found", id));
    }
}
