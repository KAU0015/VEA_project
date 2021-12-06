package cz.vsb.vea.project;

import cz.vsb.vea.project.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new TimestampLocalDateConverter());
        registry.addConverter(new TimestampLocalDateTimeConverter());
        registry.addConverter(new LocalDateTimestampConverter());
        registry.addConverter(new LocalDateTimeTimestampConverter());
        registry.addConverter(new LocalDateTimeStringConverter());
    }
}
