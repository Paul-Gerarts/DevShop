package be.syntra.devshop.DevshopBack.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotBlank
    private String name;
}
