package security.jwt.securityjwt.account.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.jwt.securityjwt.common.security.CurrentPrincipal;
import security.jwt.securityjwt.common.web.response.RestBean;

@RestController
@RequestMapping("/test/auth")
public class TestController {

    @PostMapping("/abc")
    public RestBean<CurrentPrincipal> Test(@AuthenticationPrincipal CurrentPrincipal currentPrincipal){
        return RestBean.success(currentPrincipal);
    }
}
