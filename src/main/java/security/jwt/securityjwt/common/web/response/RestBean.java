package security.jwt.securityjwt.common.web.response;

import lombok.Data;
import security.jwt.securityjwt.common.ex.ServiceException;

/**
 * 封装 RESTful风格的响应数据
 * status  表示状态码
 * success 表示响应是否成功
 * message 表示响应的消息
 * data 表示响应体 可以是任意类型的数据，例如字符串、对象、列表等。
 * @param <T>
 */
@Data
public class RestBean<T> {
    private Integer status;
    private boolean success;
    private String message;
    private T data;

    private RestBean(Integer status, String message,boolean success, T data) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }


    /**
     * 成功
     * @return
     * @param <T>
     */
    public static <T> RestBean<T> success() {
        return new RestBean<>(ServiceCode.OK.getValue(),ServiceCode.OK.getMessage(), true, null);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(ServiceCode.OK.getValue(),ServiceCode.OK.getMessage(),true, data);
    }


    /**
     * 业务异常处理
     * @param e
     * @return
     * @param <T>
     */

    public static <T> RestBean<T> Exception(ServiceException e) {
        return new RestBean<>(e.getServiceCode().getValue(),e.getMessage(), false, null);
    }

    public static <T> RestBean<T> Exception(ServiceCode serviceCode,String message) {
        return new RestBean<>(serviceCode.getValue(),message, false,null);
    }

    /**
     * 失败!
     * @param serviceCode
     * @return
     * @param <T>
     */

    public static <T> RestBean<T> failure(ServiceCode serviceCode) {
        return new RestBean<>(serviceCode.getValue(), serviceCode.getMessage(), false,null);
    }

    public static <T> RestBean<T> failure(ServiceCode serviceCode,T data) {
        return new RestBean<>(serviceCode.getValue(),data.toString(), false,null);
    }
}
