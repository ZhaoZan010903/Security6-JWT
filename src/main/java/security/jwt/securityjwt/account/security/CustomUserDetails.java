package security.jwt.securityjwt.account.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author java@tedu.cn
 * @version 1.0
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends User{

    private Long userID;
    private String imgUrl;

    public CustomUserDetails(Long userID,
                             String username,
                             String password,
                             String imgUrl,
                             boolean enabled,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.userID = userID;
        this.imgUrl = imgUrl;
    }
}
