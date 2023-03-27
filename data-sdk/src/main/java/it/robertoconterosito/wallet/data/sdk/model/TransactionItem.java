package it.robertoconterosito.wallet.data.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionItem {
    private Long id;
    private String description;
    private Money amount;
    private TransactionType type;
}
