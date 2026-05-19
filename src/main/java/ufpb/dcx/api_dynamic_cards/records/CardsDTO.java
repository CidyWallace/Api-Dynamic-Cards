package ufpb.dcx.api_dynamic_cards.records;

import java.time.LocalDateTime;
import java.util.List;

public record CardsDTO(
        String id,
        String title,
        LocalDateTime updateAt,
        boolean deleted,
        List<SubItemDTO> subItems
) {
}
