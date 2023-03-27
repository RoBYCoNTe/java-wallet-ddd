package it.robertoconterosito.wallet.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

public abstract class DomainEntity<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {
    public Collection<Object> getPendingEvents() {
        return this.domainEvents();
    }

}
