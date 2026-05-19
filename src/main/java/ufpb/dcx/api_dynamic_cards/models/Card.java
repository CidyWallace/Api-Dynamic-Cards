package ufpb.dcx.api_dynamic_cards.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    private String id;

    private String titulo;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt ;

    private boolean deleted = false;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubItem> subItems = new ArrayList<>();

    public void AddSubItem(SubItem itens) {
        subItems.add(itens);
        itens.setCard(this);
    }

    public Card() {
    }

    public Card(String titulo, LocalDateTime updateAt, boolean deleted, List<SubItem> subItems) {
        this.titulo = titulo;
        this.updateAt = updateAt;
        this.deleted = deleted;
        this.subItems = subItems;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SubItem> subItems) {
        this.subItems = subItems;
    }
}
