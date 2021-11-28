package cz.vsb.vea.project.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;

@Component
public class LocalDateTimestampConverter implements Converter<LocalDate, Timestamp> {
    @Override
    public Timestamp convert(LocalDate localDate) {
        if(localDate == null)
            return null;
        return Timestamp.valueOf(localDate.atStartOfDay());
    }
}
