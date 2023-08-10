package security.jwt.securityjwt.account.controller;


import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.jwt.securityjwt.account.pojo.param.UserLoginInfoParam;
import security.jwt.securityjwt.account.pojo.vo.AccountMeVO;
import security.jwt.securityjwt.account.pojo.vo.UserLoginVO;
import security.jwt.securityjwt.account.service.IUserService;
import security.jwt.securityjwt.common.security.CurrentPrincipal;
import security.jwt.securityjwt.common.web.response.RestBean;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Validated

public class AccountController {
    @Autowired
    private IUserService userService;


    AccountController(){
        log.debug("创建控制器: AccountController");
    }

    @PostMapping("/login")
    public RestBean<UserLoginVO> login(UserLoginInfoParam userLoginInfoParam){
        UserLoginVO userLoginResultVO = userService.login(userLoginInfoParam);
        return RestBean.success(userLoginResultVO);
    }

    /**
     * @param principal 当事人信息
     * @return 返回个人信息 AccountMeVO 是一个自定义的类,想返回前端什么东西都可以;
     */
    @GetMapping("/me")
    public RestBean<AccountMeVO> me(@AuthenticationPrincipal @NotNull(message = "未登录!") CurrentPrincipal principal) {
        return RestBean.success(userService.me(principal.getUserID()));
    }


}
