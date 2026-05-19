package ufpb.dcx.api_dynamic_cards.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ufpb.dcx.api_dynamic_cards.models.Card;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByUpdateAtAfter(LocalDateTime updateAtAfter);
}
