package com.ipower.cloud.enhancecore.envaware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * @Description  默认配置加载，该类在ConfigFileApplicationListener中反射加载，加载默认配置
 * 设置一个自己专用的properties环境
 * @Author xl
 * @Date 2020/4/3
 */
public class MarsSpringCloudDefaulEnvPropertySource implements EnvironmentPostProcessor,Ordered {

    private final String enhanceActuatorFileName = "enhanceActuatorSPA.properties";
    private final String enhanceEurekaClientFileName = "enhanceEurekaClient.properties";
    private final String enhanceFeignFileName = "enhanceFeign.properties";
    private final String enhanceHystrixFileName = "enhanceHystrix.properties";
    private final String enhanceRibbonFileName = "enhanceRibbon.properties";

    private final String propertySourceName = "marsCloudOptimizeDefault";

    private PropertySourceLoader propertiesLoader;

    /**
     * 此方法会进入两次，一次是bootstrapContext初始化的时候，一个是ApplicationContext初始优化的时候
     * 这里需要考虑一下，是否需要做过滤，在bootstrapContext初始化的时候不进行设置
     * */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        //如果是bootstrap启动的则直接返回
        if(environment.getPropertySources().contains("bootstrap")){
            return;
        }

        try {

            this.propertiesLoader = new PropertiesPropertySourceLoader();

            Properties properties = new Properties();
            properties.load(new ClassPathResource(enhanceActuatorFileName).getInputStream());
            properties.load(new ClassPathResource(enhanceEurekaClientFileName).getInputStream());
            properties.load(new ClassPathResource(enhanceFeignFileName).getInputStream());
            properties.load(new ClassPathResource(enhanceHystrixFileName).getInputStream());
            properties.load(new ClassPathResource(enhanceRibbonFileName).getInputStream());

            //第一个参数自定义
            PropertiesPropertySource propertySource = new PropertiesPropertySource(propertySourceName, properties);
            environment.getPropertySources().addLast(propertySource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //放到ConfigFileApplicationListener这个properties属性读取的后面加载自定义配置，作为最低优先级
    @Override
    public int getOrder() {
        return ConfigFileApplicationListener.DEFAULT_ORDER+2;
    }
}
