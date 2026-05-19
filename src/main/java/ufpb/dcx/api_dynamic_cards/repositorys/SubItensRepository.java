package ufpb.dcx.api_dynamic_cards.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ufpb.dcx.api_dynamic_cards.models.SubItem;

public interface SubItensRepository extends JpaRepository<SubItem, String> {
}
