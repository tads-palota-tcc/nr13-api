package br.com.smartnr.nr13api.core.freemarker;

import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreeMarkerConfig implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FreeMarkerConfigurer) {
            FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;
            configurer.getConfiguration().setObjectWrapper(new Java8ObjectWrapper(freemarker.template.Configuration.getVersion()));
        }
        return bean;
    }
}
