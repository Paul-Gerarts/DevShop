package be.syntra.devshop.DevshopBack.security.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SecurityUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userID;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "userName", unique = true)
    private String userName;

    @Size(min = 1)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "SECURITY_USER_USERROLE",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")},
            foreignKey = @ForeignKey(name = "user_role_fk"))
    private List<UserRole> userRoles;

    public void setAuthorities(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getName()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
