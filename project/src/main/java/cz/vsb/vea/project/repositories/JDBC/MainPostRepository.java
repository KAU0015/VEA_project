package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.converters.LocalDateTimeTimestampConverter;
import cz.vsb.vea.project.models.Comment;
import cz.vsb.vea.project.models.MainPost;
import cz.vsb.vea.project.models.Post;
import cz.vsb.vea.project.repositories.JDBC.mappers.*;
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

    LocalDateTimeTimestampConverter localDateTimeTimestampConverter = new LocalDateTimeTimestampConverter();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
      /*  try {
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
        }*/
    }

    @Override
    public List<MainPost> getAllMainPosts() {
        return jdbcTemplate.query("select p.id id, p.date date, p.content content, p.title title, u.id u_id, u.username username, u.first_name first_name, u.last_name last_name, u.day_of_birth day_of_birth, u.password password from post p join user u on p.user_id = u.id where p.type = 'mainPost' order by p.date desc", new MainPostUserMapper());
    }

    @Override
    public MainPost save(MainPost mp) {
        if (mp.getId() == 0) {
            jdbcTemplate.update("insert into post (date, content, user_id, title, type) values (?, ?, ?, ?, ?)",
                    localDateTimeTimestampConverter.convert(mp.getDate()), mp.getContent(), mp.getUser().getId(), mp.getTitle(), "mainPost"
            );
        } else {
            jdbcTemplate.update("update post set date=?, content=?, user_id=? title=? where id=?",
                    localDateTimeTimestampConverter.convert(mp.getDate()), mp.getContent(), mp.getUserId(), mp.getTitle(), mp.getId()
            );
        }
        return mp;
    }

    @Override
    public MainPost find(long id) {
        MainPost mainPost = jdbcTemplate.queryForObject("select p.id id, p.date date, p.content content, u.id u_id, u.username username, " +
                "p.title title from post p join user u on u.id = p.user_id where p.id = ?", new Object[]{id}, new MainPostUserMapper());
        getCommentsRecursive(mainPost);
        return mainPost;
    }

    private void getCommentsRecursive(Post post){
        post.setComments(jdbcTemplate.query("select p.id id, p.date date, p.content content, u.id u_id, u.username username, p.post_id post_id " +
                "from post p join user u on u.id = p.user_id where p.post_id = ?", new Object[]{post.getId()}, new CommentUserMapper()));
        for(Comment c : post.getComments()){
            getCommentsRecursive(c);
        }
    }
}
