package ufpb.dcx.api_dynamic_cards.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_subitem")
public class SubItem {

    @Id
    private String id;

    private String name;

    @Column(name = "is_counter")
    private boolean isCounter;

    private boolean marker;

    private int current;
    private int max;
    private double value;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public SubItem() {
    }

    public SubItem(String name, boolean isCounter, boolean marker, int current, int max, LocalDateTime updateAt, boolean deleted, double value,Card card) {
        this.name = name;
        this.isCounter = isCounter;
        this.marker = marker;
        this.current = current;
        this.max = max;
        this.updateAt = updateAt;
        this.deleted = deleted;
        this.value = value;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCounter() {
        return isCounter;
    }

    public void setCounter(boolean counter) {
        isCounter = counter;
    }

    public boolean isMarker() {
        return marker;
    }

    public void setMarker(boolean marker) {
        this.marker = marker;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setCard(Card card) {
    }
}
