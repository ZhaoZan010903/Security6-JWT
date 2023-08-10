package security.jwt.securityjwt.account.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录的参数类
 *
 */
@Data
public class UserLoginInfoParam implements Serializable {

    /**
     * 用户名
     */
    @NotNull(message = "请提交用户名")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "用户名必须是4~15长度的字符组成，且不允许使用标点符号")
    @ApiModelProperty(value = "用户名", required = true, example = "root")
    private String username;

    /**
     * 密码（原文）
     */
    @NotNull(message = "请提交密码")
    @Pattern(regexp = "^.{4,15}$", message = "密码必须是4~15长度的字符组成")
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;

}
