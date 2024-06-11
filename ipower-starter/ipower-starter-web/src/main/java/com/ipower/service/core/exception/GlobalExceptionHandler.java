package com.ipower.service.core.exception;

import com.ipower.framework.common.core.api.Result;
import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.exception.ExceptionUtil;
import com.ipower.framework.common.core.exception.unchecked.UncheckedException;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.util.JacksonJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理
 * @author kris
 * @date 2021/7/22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 响应失败，并设置状态码、响应消息
     *
     * @param code    状态码
     * @param message 响应消息
     * @param <T>     泛型参数
     * @return Result<T> 统一响应结果
     */
    protected <T> Result<T> failed(int code, String message) {
        return Result.fail(code, message);
    }

    /**
     * 统一异常处理
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param e        异常信息
     * @return Object 响应信息
     */
    @ExceptionHandler({Exception.class})
    protected Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        //控制台打印异常
        log.error("================================ <<< 统一异常处理 >>> ================================");
        log.error("request uri: " + request.getRequestURI());
        log.error("request params: " + JacksonJsonUtil.toJSONString(request.getParameterMap()));
        log.error("exception message: " + e.getMessage(), e);
        log.error("====================================================================================");
        //直接获取异常信息返回
        return failed(ExceptionUtil.getExceptionCode(e), getErrorMessage(e));
    }

    /**
     * 获取异常消息
     *
     * @param e 异常
     * @return String 异常消息
     */
    private String getErrorMessage(Exception e) {
        //参数校验异常
        if (e instanceof MethodArgumentNotValidException) {
            String[] errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            return ArrayUtil.join(errors, " | ");
        }
//        //excel异常
//        if (e instanceof ExcelAnalysisException) {
//            return ExceptionUtil.getSimpleMessage(e);
//        }
        //Unchecked异常
        if (e instanceof UncheckedException) {
            return ExceptionUtil.getSimpleMessage(e);
        }
        //其他情况
        return StringUtil.emptyToDefault(ExceptionUtil.getSimpleMessage(e), "服务器内部异常");
    }

//    /**
//     * 键值重复
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
//    @ExceptionHandler(value = DuplicateKeyException.class)
//    public HttpResult handleDuplicateKeyException(DuplicateKeyException e) {
//        logger.warn(e.getMessage(), e);
//        return HttpResult.failure(ExceptionCommonDefineEnum.DUPLICATE_KEY.getCode(),ExceptionCommonDefineEnum.DUPLICATE_KEY.getMsg());
//    }
//
//    /**
//     * ConstraintViolationException
//     * 这里是补货使用@Validate注解产生的Get请求@RequesParam和@Path上面参数的异常
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    public HttpResult handleConstraintViolationException(ConstraintViolationException e) {
//        logger.debug(e.getMessage(), e);
//
//        Set<ConstraintViolation<?>> set =  e.getConstraintViolations();
//        StringBuilder msg = new StringBuilder();
//        //只返回第一个错误
//        for(ConstraintViolation constraintViolation:set){
//            msg.append(constraintViolation.getMessage());
//            break;
//        }
//
//        return HttpResult.failure(ExceptionCommonDefineEnum.PARAM_ERROR.getCode(), msg.toString());
//
//    }
//
//    /**
//     * ValidationException
//     * @param e
//     * @return
//     */
////    @ExceptionHandler(value = ValidationException.class)
////    public Resp handleValidationException(ValidationException e) {
////
////        return Resp.failure("A1001", e.getCause().getMessage(), e.getCause().getMessage());
////    }
//
//    /**
//     * AccessDeniedException
//     * springsecurety的异常直接抛出
//     * @param e 异常
//     * @return 异常结果
//     */
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public HttpResult handleAccessDeniedException(AccessDeniedException e) {
//        logger.debug(e.getMessage(), e);
//        throw e;
//
////        return Resp.failure(BusinessRespEnum.ACCESS_DENIED);
//    }
//
//    /**
//     * AuthenticationException
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
//    @ExceptionHandler(AuthenticationException.class)
//    public HttpResult handleAuthenticationException(AuthenticationException e) {
//        logger.debug(e.getMessage(), e);
//        throw e;
//
////        return Resp.failure(BusinessRespEnum.ACCESS_DENIED);
//    }
//
//    /**
//     * Controller上一层相关异常
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
//    @ExceptionHandler({
//            NoHandlerFoundException.class,
//            HttpRequestMethodNotSupportedException.class,
//            HttpMediaTypeNotSupportedException.class,
//            HttpMediaTypeNotAcceptableException.class,
//            MissingPathVariableException.class,
//            MissingServletRequestParameterException.class,
//            TypeMismatchException.class,
//            HttpMessageNotReadableException.class,
//            HttpMessageNotWritableException.class,
//            // BindException.class,
//            // MethodArgumentNotValidException.class
//            ServletRequestBindingException.class,
//            ConversionNotSupportedException.class,
//            MissingServletRequestPartException.class,
//            AsyncRequestTimeoutException.class
//    })
//    public HttpResult handleServletException(Exception e) {
//        logger.error(e.getMessage(), e);
//
////        if (ENV_PROD.equals(profile)) {
////            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
////            BusinessException exception = new BusinessException(BusinessRespEnum.SERVER_ERROR);
////            String message = getMessage(exception);
////            return Resp.failure(exception.getRespEnum().getCode(), message);
////        }
////
////        int code = BusinessRespEnum.SERVER_ERROR.getCode();
////        try {
////            ServletRespEnum servletExceptionEnum = ServletRespEnum.valueOf(e.getClass().getSimpleName());
////            code = servletExceptionEnum.getCode();
////        } catch (IllegalArgumentException e1) {
////            logger.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletRespEnum.class.getName());
////        }
//
//        return HttpResult.failure(ExceptionCommonDefineEnum.COMMON_FAIL.getCode(), e.getMessage());
//    }
//
//    /**
//     * 参数绑定异常
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
//    @ExceptionHandler(value = BindException.class)
//    public HttpResult handleBindException(BindException e) {
//
////        logger.error("参数绑定校验异常", e);
//
//        return wrapperBindingResult(e.getBindingResult());
//    }
//
//    /**
//     * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public HttpResult handleValidException(MethodArgumentNotValidException e) {
////        logger.error("参数绑定校验异常", e);
//
//        return wrapperBindingResult(e.getBindingResult());
//    }
//
//    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
//    public HttpResult handleTypeException(MethodArgumentTypeMismatchException e) {
//        return HttpResult.failure(ExceptionCommonDefineEnum.PARAM_ERROR.getCode(), e.getName()+"类型错误");
//    }
//
//    /**
//     * 404异常
//     *
//     * @param e 异常
//     * @return 异常结果
//     */
////    @ExceptionHandler(value = NoHandlerFoundException.class)
//    public HttpResult handleNoHandlerFoundException(NoHandlerFoundException e) {
////        logger.error(e.getMessage(), e);
////        if (ENV_PROD.equals(profile)) {
////            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
////            return Resp.failure(20001, "服务器错误");
////        }
////        if (luxuryCoreProperties.getPrintStackTrace()) {
////            logger.error(e.getMessage(), e);
////        }
//
//        return HttpResult.failure(ExceptionCommonDefineEnum.NO_RESOURCE.getCode(),ExceptionCommonDefineEnum.NO_RESOURCE.getMsg());
//    }
//
//    /**
//     * 未定义异常
//     * 未定义异常这里不转换为http状态200返回
//     * @param e 异常
//     * @return 异常结果
//     */
////    @ExceptionHandler(value = Exception.class)
////    public HttpResult handleException(Exception e) {
//////        logger.error(e.getMessage(), e);
//////        if (ENV_PROD.equals(profile)) {
//////            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
//////            return Resp.failure(20001, "服务器错误");
//////        }
//////        if (ailuSiriusCoreProperties.getPrintStackTrace()) {
////            logger.error(e.getMessage(), e);
//////        }
////
////        return HttpResult.failure(ExceptionCommonDefineEnum.COMMON_FAIL);
////    }
//
//    /**
//     * 包装绑定异常结果
//     *这里是补货@RequestBody来的controller参数里面的对象进行绑定的时候@Validate验证做出
//     * @param bindingResult 绑定结果
//     * @return 异常结果
//     */
//    private HttpResult wrapperBindingResult(BindingResult bindingResult) {
//
//        StringBuilder msg = new StringBuilder();
//        for (int i=0,size=bindingResult.getErrorCount(); i<size; i++) {
//            if (i > 0) {
//                msg.append(",");
//            }
//            ObjectError error = bindingResult.getAllErrors().get(i);
//            msg.append(error.getDefaultMessage());
//
//        }
//
//        return HttpResult.failure(ExceptionCommonDefineEnum.PARAM_ERROR.getCode(), msg.toString());
//    }


}

