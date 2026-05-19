package ufpb.dcx.api_dynamic_cards.records;

import java.time.LocalDateTime;

public record SubItemDTO(
        String id,
        String name,
        boolean counter,
        boolean marker,
        int current,
        int max,
        double value,
        boolean deleted,
        LocalDateTime updateAt
) {
}
