package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.TimestampLocalDateConverter;
import cz.vsb.vea.project.converters.TimestampLocalDateTimeConverter;
import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPostUserMapper implements RowMapper<MainPost> {

    TimestampLocalDateTimeConverter timestampLocalDateTimeConverter = new TimestampLocalDateTimeConverter();

    @Override
    public MainPost mapRow(ResultSet resultSet, int i) throws SQLException {
        MainPost mainPost = new MainPost(resultSet.getLong("id"),
                timestampLocalDateTimeConverter.convert(resultSet.getTimestamp("date")),
                resultSet.getString("content"),
                new User(resultSet.getLong("u_id"),
                        resultSet.getString("username"),
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                null,
                resultSet.getString("title")
        );
        mainPost.setUserId(resultSet.getLong("u_id"));
        return mainPost;
    }
}
