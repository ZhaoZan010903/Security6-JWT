package security.jwt.securityjwt.common.ex;


import lombok.Getter;
import security.jwt.securityjwt.common.web.response.ServiceCode;

/**
 * 业务异常
 *

 */
public class ServiceException extends RuntimeException {

    @Getter
    private ServiceCode serviceCode;

    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

}
