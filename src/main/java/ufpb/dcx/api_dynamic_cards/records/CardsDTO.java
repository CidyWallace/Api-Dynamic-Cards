package ufpb.dcx.api_dynamic_cards.records;

import ufpb.dcx.api_dynamic_cards.models.SubItem;

import java.time.LocalDateTime;
import java.util.List;

public record CardsDTO(
        String id,
        String title,
        LocalDateTime updateAt,
        boolean deletd,
        List<SubItem> itens
) {
}
