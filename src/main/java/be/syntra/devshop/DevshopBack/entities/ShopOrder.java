package be.syntra.devshop.DevshopBack.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shoporder")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoporder_id")
    private Long id;

    @NotNull
    @Column(name = "shoporder_creation_date_time")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime shopOrderCreationDateTime;

    @Column(name = "finalized_shoporder")
    private boolean finalizedShopOrder;

    @Column(name = "paid_user_order")
    private boolean paidShopOrder;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JoinTable(
            name = "SHOPORDER_ORDERCONTENT",
            joinColumns = {@JoinColumn(name = "shoporder_id", referencedColumnName = "shoporder_id")},
            inverseJoinColumns = {@JoinColumn(name = "ordercontent_id", referencedColumnName = "ordercontent_id")},
            foreignKey = @ForeignKey(name = "shoporder_ordercontent_fk"))
    private List<OrderContent> orderContents;

    @Override
    public String toString() {
        return "ShopOrder{" +
                "id=" + id +
                ", shopOrderCreationDateTime=" + shopOrderCreationDateTime +
                '}';
    }
}
