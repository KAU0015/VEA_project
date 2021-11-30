package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.models.Comment;
import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.repositories.JDBC.mappers.*;
import cz.vsb.vea.project.repositories.PostRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(
        value="accessType",
        havingValue = "jdbc",
        matchIfMissing = true)
public class PostRepository implements PostRepositoryInterface {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        try {
            String dbProducerName;
            try (Connection con = jdbcTemplate.getDataSource().getConnection()) {
                DatabaseMetaData metaData = con.getMetaData();
                dbProducerName = metaData.getDatabaseProductName();
            }
            String sqlCreateTable;
            if ("H2".equals(dbProducerName)) {
                sqlCreateTable = "CREATE TABLE post(id INTEGER NOT NULL AUTO_INCREMENT," +
                        " type varchar(31) not null, " +
                        " date TIMESTAMP, " +
                        " content varchar(255) not null, " +
                        " title varchar(255)," +
                        " user_id int REFERENCES user(id)," +
                        " post_id int REFERENCES post(id)," +
                        " CONSTRAINT Post_PK PRIMARY KEY (id))";
            } else {
                throw new RuntimeException("Unsupported database type");
            }
            jdbcTemplate.update(sqlCreateTable);
            System.out.println("Post table created");
        } catch (DataAccessException e) {
            System.out.println("Table already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public Post save(Post p) {
        return null;
    }

    @Override
    public Post find(long id) {
        return jdbcTemplate.queryForObject("select* from post where id = ?", new Object[]{id}, new MainPostMapper());
    }

    @Override
    public List<Post> find10LastPosts(long userId) {
        List<MainPost> mainPosts = jdbcTemplate.query("select * from post where user_id = ? and type = 'mainPost' order by date desc limit 10", new Object[]{userId}, new MainPostMapper());
        List<Comment> comments = jdbcTemplate.query("select * from post where user_id = ? and type = 'comment' order by date desc limit 10", new Object[]{userId}, new CommentMapper());
        List<Post> posts = new ArrayList<>();
        posts.addAll(mainPosts);
        posts.addAll(comments);
        posts.sort(Comparator.comparing(Post::getDate).reversed());
        return posts.stream().limit(10).collect(Collectors.toList());
    }
}
