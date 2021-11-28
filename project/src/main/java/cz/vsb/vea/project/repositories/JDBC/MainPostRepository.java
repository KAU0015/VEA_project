package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.repositories.MainPostRepositoryInterface;
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
public class MainPostRepository implements MainPostRepositoryInterface {

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
                sqlCreateTable = "CREATE TABLE main_post(id INTEGER NOT NULL AUTO_INCREMENT," +
                        " date TIMESTAMP, " +
                        " content varchar(255) not null, " +
                        " user_id int not null REFERENCES user(id), " +
                        " title varchar(255) not null," +
                        " CONSTRAINT MainPost_PK PRIMARY KEY (id))";
            } else {
                throw new RuntimeException("Unsupported database type");
            }
            jdbcTemplate.update(sqlCreateTable);
            System.out.println("MainPost table created");
        } catch (DataAccessException e) {
            System.out.println("Table already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MainPost> getAllMainPosts() {
        return null;
    }

    @Override
    public MainPost save(MainPost mp) {
        return null;
    }

    @Override
    public MainPost find(long id) {
        return null;
    }
}
