package security.jwt.securityjwt.account.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 用于登录成功以后向前端返回token的
 * @author ASUS
 */
@Data
@Accessors( chain = true )
public class UserLoginVO implements Serializable {
    /**
     * 用户ID
     */
    private Long userID;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像
     */
    private String imgUrl;
    /**
     * 状态
     */
    private Integer enable;
    /**
     * Token（JWT）
     */
    private String token;

}
