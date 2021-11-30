package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.TimestampLocalDateConverter;
import cz.vsb.vea.project.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserMapper implements RowMapper<User> {

    @Autowired
    TimestampLocalDateConverter timestampLocalDateConverter = new TimestampLocalDateConverter();

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                timestampLocalDateConverter.convert(resultSet.getTimestamp("day_of_birth")),
                resultSet.getString("password"),
                null
        );
    }
}
