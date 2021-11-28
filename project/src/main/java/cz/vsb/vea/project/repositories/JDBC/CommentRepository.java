package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.models.Comment;
import cz.vsb.vea.project.repositories.CommentRepositoryInterface;
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
import java.util.List;

@Repository
@ConditionalOnProperty(
        value="accessType",
        havingValue = "jdbc",
        matchIfMissing = true)
public class CommentRepository implements CommentRepositoryInterface {

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
                sqlCreateTable = "CREATE TABLE comment(id INTEGER NOT NULL AUTO_INCREMENT," +
                        " date TIMESTAMP, " +
                        " content varchar(255) not null, " +
                        " user_id int not null REFERENCES user(id), " +
                        " post_id int not null REFERENCES post(id), " +
                        " CONSTRAINT Comment_PK PRIMARY KEY (id))";
            } else {
                throw new RuntimeException("Unsupported database type");
            }
            jdbcTemplate.update(sqlCreateTable);
            System.out.println("Comment table created");
        } catch (DataAccessException e) {
            System.out.println("Table already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public Comment save(Comment c) {
        return null;
    }

    @Override
    public Comment find(long id) {
        return null;
    }
}