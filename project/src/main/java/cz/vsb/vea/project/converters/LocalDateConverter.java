package cz.vsb.vea.project.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;

@Component
public class LocalDateConverter implements Converter<Timestamp, LocalDate> {
    @Override
    public LocalDate convert(Timestamp timestamp) {
        if(timestamp == null)
            return null;
        return timestamp.toLocalDateTime().toLocalDate();
    }
}
