package security.jwt.securityjwt.account.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import security.jwt.securityjwt.account.pojo.entity.Account;
import security.jwt.securityjwt.account.pojo.vo.AccountMeVO;

@Repository
public interface AccountMapper extends BaseMapper<Account> {
    Account SelectByUserName(String username);

    AccountMeVO SelectByMe(Long userID);
}
