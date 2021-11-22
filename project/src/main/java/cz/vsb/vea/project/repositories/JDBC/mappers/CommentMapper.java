package cz.vsb.vea.project.repositories.JDBC.mappers;

import cz.vsb.vea.project.converters.LocalDateTimeConverter;
import cz.vsb.vea.project.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Autowired
    LocalDateTimeConverter localDateTimeConverter;

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment(resultSet.getLong("id"),
                localDateTimeConverter.convert(resultSet.getTimestamp("date")),
                resultSet.getString("content"),
                null,
                null,
                null
        );
        comment.setUserId(resultSet.getLong("user_id"));
        comment.setPostId(resultSet.getLong("post_id"));
        return comment;
    }
}
