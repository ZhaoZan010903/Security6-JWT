package security.jwt.securityjwt.account.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.jwt.securityjwt.account.mapper.AccountMapper;
import security.jwt.securityjwt.account.pojo.entity.Account;

import javax.management.relation.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Slf4j
@Service
public class AccountServiceImpl implements UserDetailsService {

    AccountServiceImpl(){
        log.debug("AccountServiceImpl开始工作");
    }

    @Autowired
    AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Spring Security框架自动调用了UserDetailsService对象，将根据用户名获取用户详情，参数：{}", username);
         /*
        如果用户名为空或者不存在，就抛出一个 UsernameNotFoundException 异常
         */

        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("用户名不能为空");
        }

        Account account = accountMapper.SelectByUserName(username);
        log.debug("根据用户名【{}】从数据库中查询用户详情，查询结果：{}", username, account);

        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }


        List<GrantedAuthority> authorities = new ArrayList<>();


        CustomUserDetails userDetails = new CustomUserDetails(
                account.getUserId(),
                account.getUsername(),
                account.getPassword(),
                account.getImgUrl(),
                account.getEnable() == 1,
                authorities
        );

        log.debug(userDetails.toString());

//        return User.withUserDetails(userDetails).build();
        return userDetails;

//        return User
//                .withUsername(account.getUsername())
//                .password(account.getPassword())
//                .disabled(account.getEnable() == 0)  //如果帐户被禁用，则为 true，否则为 false
//                .build();
    }
}
