package ufpb.dcx.api_dynamic_cards.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufpb.dcx.api_dynamic_cards.models.Card;
import ufpb.dcx.api_dynamic_cards.models.SubItem;
import ufpb.dcx.api_dynamic_cards.records.CardsDTO;
import ufpb.dcx.api_dynamic_cards.records.SubItemDTO;
import ufpb.dcx.api_dynamic_cards.repositorys.CardRepository;
import ufpb.dcx.api_dynamic_cards.repositorys.SubItensRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CardSyncService {
    private final CardRepository cardRepository;
    private final SubItensRepository subItensRepository;

    public CardSyncService(CardRepository cardRepository, SubItensRepository subItensRepository) {
        this.cardRepository = cardRepository;
        this.subItensRepository = subItensRepository;
    }

    @Transactional
    public void ProcessInboundSynchronization(List<CardsDTO> cardsDTO) {
        for (CardsDTO cardDTO : cardsDTO) {
            Optional<Card> card = cardRepository.findById(cardDTO.id());

            if(card.isEmpty()) {
                SaveCard(cardDTO);
            } else {
                Card cardExist = card.get();
                if(cardDTO.updateAt().isAfter(cardExist.getUpdateAt())) {
                    updateCard(cardExist, cardDTO);
                } else {
                    syncSubItens(cardExist, cardDTO.subItems());
                }
            }
        }
    }

    public List<CardsDTO> searchUpdate(LocalDateTime since){
        List<Card> updatedCards = cardRepository.findByUpdateAtAfter((since));

        return updatedCards.stream()
                .map(this::convertCardToDTO)
                .toList();
    }

    private void syncSubItens(Card cardExist, List<SubItemDTO> itens) {
        if(itens.isEmpty()) return;

        for (SubItemDTO subItemDTO : itens) {
            Optional<SubItem> subItemOpt = subItensRepository.findById(subItemDTO.id());

            if(subItemOpt.isEmpty()) {
                SubItem newItem = converterSubItemDto(subItemDTO);
                cardExist.AddSubItem(newItem);
            } else {
                SubItem subItemExist = subItemOpt.get();

                if(subItemDTO.updateAt().isAfter(subItemExist.getUpdateAt())) {
                    subItemExist.setName(subItemDTO.name());
                    subItemExist.setCounter(subItemDTO.counter());
                    subItemExist.setCurrent(subItemDTO.current());
                    subItemExist.setMarker(subItemDTO.marker());
                    subItemExist.setMax(subItemDTO.max());
                    subItemExist.setDeleted(subItemDTO.deleted());
                    subItemExist.setUpdateAt(subItemDTO.updateAt());
                }
            }
        }

    }

    private void updateCard(Card cardExist, CardsDTO cardDTO) {
        cardExist.setTitle(cardDTO.title());
        cardExist.setUpdateAt(cardDTO.updateAt());
        cardExist.setDeleted(cardDTO.deleted());

        syncSubItens(cardExist, cardDTO.subItems());
        cardRepository.save(cardExist);
    }

    private void SaveCard(CardsDTO card) {
        Card newCard = convertDTOToCard(card);
        cardRepository.save(newCard);
    }

    private CardsDTO convertCardToDTO(Card card){
        return new CardsDTO(
                card.getId(),
                card.getTitle(),
                card.getUpdateAt(),
                card.isDeleted(),
                card.getSubItems().stream().map(item -> new SubItemDTO(
                        item.getId(),
                        item.getName(),
                        item.isCounter(),
                        item.isMarker(),
                        item.getCurrent(),
                        item.getMax(),
                        item.getValue(),
                        item.isDeleted(),
                        item.getUpdateAt()
                )).toList()
        );
    }

    private Card convertDTOToCard(CardsDTO cardsDTO){
        return new Card(
                cardsDTO.id(),
                cardsDTO.title(),
                cardsDTO.updateAt(),
                cardsDTO.deleted(),
                cardsDTO.subItems().stream().map(item -> new SubItem(
                        item.id(),
                        item.name(),
                        item.counter(),
                        item.marker(),
                        item.current(),
                        item.max(),
                        item.value(),
                        item.deleted(),
                        item.updateAt()
                )).toList()
        );
    }

    private SubItem converterSubItemDto(SubItemDTO item) {
        return new SubItem(
                item.id(),
                item.name(),
                item.counter(),
                item.marker(),
                item.current(),
                item.max(),
                item.value(),
                item.deleted(),
                item.updateAt()
        );
    }
}


