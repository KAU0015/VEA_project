package cz.vsb.vea.project.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class LocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {

    @Override
    public LocalDateTime convert(Timestamp timestamp) {
        if(timestamp == null)
            return null;
        return timestamp.toLocalDateTime();
    }
}
