package security.jwt.securityjwt.account.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class Account {
    private Long userId;
    private String username;
    private String nickname;
    private String password;
    private String imgUrl;
    private int enable;
    private String email;
}
