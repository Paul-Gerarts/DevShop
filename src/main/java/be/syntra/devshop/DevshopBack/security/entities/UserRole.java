package be.syntra.devshop.DevshopBack.security.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "userrole")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userrole_id")
    private Long userRoleId;

    @Column(unique = true)
    private String name;
}
