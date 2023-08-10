package security.jwt.securityjwt.account.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import security.jwt.securityjwt.account.mapper.AccountMapper;
import security.jwt.securityjwt.account.pojo.param.UserLoginInfoParam;
import security.jwt.securityjwt.account.pojo.vo.AccountMeVO;
import security.jwt.securityjwt.account.security.CustomUserDetails;
import security.jwt.securityjwt.account.pojo.vo.UserLoginVO;
import security.jwt.securityjwt.account.service.IUserService;
import security.jwt.securityjwt.common.utils.jwt.JwtUtils;


@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserLoginVO login(UserLoginInfoParam userLoginInfoParam) {

        log.debug("开始处理【用户登录】的业务，参数：{}", userLoginInfoParam);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userLoginInfoParam.getUsername(), userLoginInfoParam.getPassword());
        log.debug("准备调用AuthenticationManager的认证方法，判断此用户名、密码是否可以成功登录……");
        Authentication authenticateResult = authenticationManager.authenticate(authentication);
        log.debug("验证用户登录成功，返回的认证结果：{}", authenticateResult);

        Object principal = authenticateResult.getPrincipal();
        log.debug("从认证结果中获取当事人：{}", principal);
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        String Token = JwtUtils.CreateJWT(userDetails);

        UserLoginVO userLoginVO = new UserLoginVO().setToken(Token);



        return userLoginVO;
    }

    /**
     * 登录后获取个人信息
     *
     * @param userID
     * @return
     */
    @Override
    public AccountMeVO me(Long userID) {
        return accountMapper.SelectByMe(userID);

    }
}
