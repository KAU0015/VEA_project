package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.LocalDateTimeConverter;
import cz.vsb.vea.project.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

    @Autowired
    LocalDateTimeConverter localDateTimeConverter;

    @Override
    public Post mapRow(ResultSet resultSet, int i) throws SQLException {
        Post post = new Post(resultSet.getLong("id"),
                localDateTimeConverter.convert(resultSet.getTimestamp("date")),
                resultSet.getString("content"),
                null,
                null
        );
        post.setUserId(resultSet.getLong("user_id"));
        return post;
    }
}
