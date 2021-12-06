package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.TimestampLocalDateConverter;
import cz.vsb.vea.project.converters.TimestampLocalDateTimeConverter;
import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPostMapper implements RowMapper<MainPost> {

    TimestampLocalDateTimeConverter timestampLocalDateTimeConverter = new TimestampLocalDateTimeConverter();

    @Autowired
    TimestampLocalDateConverter timestampLocalDateConverter = new TimestampLocalDateConverter();

    @Override
    public MainPost mapRow(ResultSet resultSet, int i) throws SQLException {
        MainPost mainPost = new MainPost(resultSet.getLong("id"),
                timestampLocalDateTimeConverter.convert(resultSet.getTimestamp("date")),
                resultSet.getString("content"),
                null,
                null,
                resultSet.getString("title")
        );
        mainPost.setUserId(resultSet.getLong("user_id"));
        return mainPost;
    }
}
