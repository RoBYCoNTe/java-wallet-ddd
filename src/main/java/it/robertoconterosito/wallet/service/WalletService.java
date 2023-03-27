package it.robertoconterosito.wallet.service;

import it.robertoconterosito.wallet.domain.Wallet;
import it.robertoconterosito.wallet.exception.WalletNotFoundException;
import it.robertoconterosito.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(String name) {
        Wallet wallet = new Wallet();
        wallet.setName(name);
        return walletRepository.save(wallet);
    }

    public Wallet get(Long id) throws WalletNotFoundException {
        return walletRepository
            .findById(id)
            .orElseThrow(() -> new WalletNotFoundException(id));
    }

    @Transactional
    public void persist(Wallet wallet) {
        walletRepository.save(wallet);
    }

}
