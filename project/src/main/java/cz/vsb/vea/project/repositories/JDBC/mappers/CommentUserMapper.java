package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.TimestampLocalDateTimeConverter;
import cz.vsb.vea.project.models.Comment;
import cz.vsb.vea.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentUserMapper implements RowMapper<Comment> {

    @Autowired
    TimestampLocalDateTimeConverter timestampLocalDateTimeConverter = new TimestampLocalDateTimeConverter();

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment(resultSet.getLong("id"),
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
                null
        );
        comment.setUserId(resultSet.getLong("u_id"));
        comment.setPostId(resultSet.getLong("post_id"));
        return comment;
    }
}
