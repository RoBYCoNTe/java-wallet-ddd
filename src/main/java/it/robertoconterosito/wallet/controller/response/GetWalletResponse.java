package it.robertoconterosito.wallet.controller.response;

import it.robertoconterosito.wallet.domain.Money;
import it.robertoconterosito.wallet.domain.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetWalletResponse extends BaseResponse {
    private Long id;

    private String name;

    private Money balance;

    public GetWalletResponse(Wallet wallet) {
        this.id = wallet.getId();
        this.name = wallet.getName();
        this.balance = wallet.getBalance();
    }
}
