package com.ipower.service.config;

import com.ipower.service.core.exception.GlobalErrorController;
import com.ipower.service.core.exception.GlobalExceptionHandler;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

/**
 * 全局异常自动配置
 * @author xl
 * @date 2021/7/22
 */
@Configuration
@AutoConfigureBefore(value = { ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class ExceptionAutoConfiguration {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    //在WebMvcAutoConfiguration初始化之前生成，然后这里实例化了，原来代码中的WebMvcAutoConfiguration就不会实例化了
    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class)
    public GlobalErrorController basicErrorController(ErrorAttributes errorAttributes,
                                                      ServerProperties serverProperties,
                                                      ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
        return new GlobalErrorController(errorAttributes, serverProperties.getError(), errorViewResolversProvider.getIfAvailable());
    }


    /**
     * 快速失败
     * @return
     */
    @Bean
    public Validator validator(AutowireCapableBeanFactory springFactory) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 开启快速失败模式，默认为false
                .failFast(true)
                // 解决 SpringBoot 依赖注入问题
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(springFactory))
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
