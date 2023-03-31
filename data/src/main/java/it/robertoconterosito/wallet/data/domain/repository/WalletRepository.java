package it.robertoconterosito.wallet.data.domain.repository;

import it.robertoconterosito.wallet.data.domain.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
