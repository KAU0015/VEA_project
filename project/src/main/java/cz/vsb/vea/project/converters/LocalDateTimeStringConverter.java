package cz.vsb.vea.project.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class LocalDateTimeStringConverter implements Converter<LocalDateTime, String>{

    @Override
    public String convert(LocalDateTime localDateTime) {
        if(localDateTime == null){
            return "No date specified!";
        }
        return  localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:SS"));
    }
}
