package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.LocalDateConverter;
import cz.vsb.vea.project.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Autowired
    LocalDateConverter localDateConverter;

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                localDateConverter.convert(resultSet.getTimestamp("dateOfBirth")),
                resultSet.getString("password"),
                null
        );
    }
}
