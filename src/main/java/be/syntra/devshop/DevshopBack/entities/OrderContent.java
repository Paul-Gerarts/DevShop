package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ordercontent")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordercontent_id")
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            foreignKey = @ForeignKey(name = "ordercontent_product_id"))
    @NotNull
    private Product product;

    @NotNull
    private Integer count;
}
