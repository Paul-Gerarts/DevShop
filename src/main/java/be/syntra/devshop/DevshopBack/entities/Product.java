package be.syntra.devshop.DevshopBack.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @Column (name = "Name")
    private final String name;
    @Column (name = "Price")
    private final BigDecimal price;
}
