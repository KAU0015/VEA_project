package cz.vsb.vea.project.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeTimestampConverter implements Converter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convert(LocalDateTime localDateTime) {
        if(localDateTime == null)
            return null;
        return Timestamp.valueOf(localDateTime);
    }
}
