package security.jwt.securityjwt.account.service;

import security.jwt.securityjwt.account.pojo.param.UserLoginInfoParam;
import security.jwt.securityjwt.account.pojo.vo.AccountMeVO;
import security.jwt.securityjwt.account.pojo.vo.UserLoginVO;

public interface IUserService  {

    /**
     * 登录
     * @param userLoginInfoParam
     * @return
     */
    UserLoginVO login(UserLoginInfoParam userLoginInfoParam);

    /**
     * 登录后获取个人信息
     * @param userID
     * @return
     */
    AccountMeVO me(Long userID);
}
