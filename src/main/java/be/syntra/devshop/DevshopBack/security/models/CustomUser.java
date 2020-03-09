package be.syntra.devshop.DevshopBack.security.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@Builder
public class CustomUser extends User {

    private Integer userID;

    public CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities,
                      Integer userID) {
        super(username, password, authorities);
        this.userID = userID;
    }
}
