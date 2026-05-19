package ufpb.dcx.api_dynamic_cards.controlles;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufpb.dcx.api_dynamic_cards.models.Card;
import ufpb.dcx.api_dynamic_cards.records.CardsDTO;
import ufpb.dcx.api_dynamic_cards.services.CardSyncService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardSyncService cardSyncService;

    public CardController(CardSyncService cardSyncService) {
        this.cardSyncService = cardSyncService;
    }

    @GetMapping("/sync")
    public ResponseEntity<List<CardsDTO>> PullUpdatesToServer(
            @RequestParam("since")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime since
    ) {
        List<CardsDTO> updates = cardSyncService.searchUpdate(since);
        return ResponseEntity.ok(updates);
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> PushUpdatesToServer(
            @RequestBody List<CardsDTO> cardsDTO
    ){
        cardSyncService.ProcessInboundSynchronization(cardsDTO);
        return ResponseEntity.ok().build();
    }
}
