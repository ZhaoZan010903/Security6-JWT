package security.jwt.securityjwt.core.ex.handler;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import security.jwt.securityjwt.common.ex.ServiceException;
import security.jwt.securityjwt.common.web.response.RestBean;
import security.jwt.securityjwt.common.web.response.ServiceCode;

import java.util.Set;

/**
 * 全局异常处理器
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        log.info("创建全局异常处理器对象：GlobalExceptionHandler");
    }


    /**
     * ServiceException：是一个自定义的业务异常类，通常用于封装业务逻辑中出现的错误，
     * 它可以包含一个错误码和一个错误提示，用于向用户或调用者返回异常信息。
     *
     * @param e
     */
    @ExceptionHandler
    public RestBean<ServiceException> handleServiceException(ServiceException e) {
        log.debug("全局异常处理器开始处理ServiceException");
        return RestBean.Exception(e);
    }

    /**
     * BindException：是一个继承自Exception的异常类，
     * 通常用于表示在绑定请求参数到目标对象时出现的错误，
     * 如类型不匹配、格式不正确、校验失败等。
     * 它可以包含一个或多个FieldError对象，
     * 用于描述具体的绑定错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public RestBean<BindException> handleBindException(BindException e) {
        log.debug("全局异常处理器开始处理BindException");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请求参数格式错误，");
        stringBuilder.append(e.getFieldError().getDefaultMessage());
        stringBuilder.append("！");
        String message = stringBuilder.toString();
        log.warn(message);
        return RestBean.Exception(ServiceCode.ERROR_CONFLICT, message);
    }


    /**
     * ConstraintViolationException：是一个继承自RuntimeException的异常类，
     * 通常用于表示在执行数据库操作时违反了约束条件，
     * 如主键重复、外键不存在、非空字段为空等。
     * 它可以包含一个或多个ConstraintViolation对象，用于描述具体的约束违反
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public RestBean<ConstraintViolationException> handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("全局异常处理器开始处理ConstrainViolationException");
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            stringBuilder.append(constraintViolation.getMessage());
        }

        String message = stringBuilder.toString();
        log.debug(message);
        return RestBean.Exception(ServiceCode.ERROR_UNAUTHORIZED_DISABLED, message);
    }


    /**
     * 用户名或密码错误 处理 UsernameNotFound的错误
     */
    @ExceptionHandler
    public RestBean<AuthenticationException> handlerBadCredentialsException(AuthenticationException e) {
        log.debug("全局异常处理器开始处理AuthenticationException");
        log.debug("异常类型：{}", e.getClass().getName());
        return RestBean.Exception(ServiceCode.ERROR_CONFLICT, e.getMessage());
    }


    /**
     * 账号被禁用,状态码为40910 并提示账号已经被禁止!
     */
    @ExceptionHandler
    public RestBean<DisabledException> handlerDisabledException(DisabledException e){
        log.debug("开始全局异常处理 DisabledException :{}",e.getMessage());
        return RestBean.Exception(ServiceCode.ERROR_USER_DISABLED,ServiceCode.ERROR_USER_DISABLED.getMessage());
    }




    /**
     * Throwable：是Java中所有错误和异常的超类，是一个抽象类，不能直接实例化。
     * 它可以包含一个详细消息、一个原因、一个堆栈跟踪和一些被抑制的异常。
     * 它有两个直接子类：Error和Exception。
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public RestBean<Throwable> handleThrowable(Throwable e) {
        log.debug("全局异常处理器开始处理Throwable");
        log.debug("异常跟踪信息如下：", e);
        log.warn("",e);
        String message = "服务器忙，请稍后再试!";
        return RestBean.Exception(ServiceCode.ERROR_UNKNOWN,message);
    }





}
