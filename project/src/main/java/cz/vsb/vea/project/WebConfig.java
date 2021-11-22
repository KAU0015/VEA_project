package cz.vsb.vea.project;

import cz.vsb.vea.project.converters.LocalDateConverter;
import cz.vsb.vea.project.converters.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new LocalDateConverter());
        registry.addConverter(new LocalDateTimeConverter());

    }
}
