package it.robertoconterosito.wallet.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginatedRequest {
    private int page;
    private int size;

    public int getFrom() {
        return (page - 1) * size;
    }

    public int getTo() {
        return page * size;
    }

    public PaginatedRequest changePage(int page) {
        this.setPage(page);
        return this;
    }

    public PaginatedRequest changeSize(int size) {
        this.setSize(size);
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        return map;
    }
}
