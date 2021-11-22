package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.models.User;
import cz.vsb.vea.project.repositories.JDBC.mappers.UserMapper;
import cz.vsb.vea.project.repositories.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
public class UserRepository implements UserRepositoryInterface {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert userInsert;

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
                sqlCreateTable = "CREATE TABLE IF NOT EXISTS user(id BIGINT NOT NULL AUTO_INCREMENT," +
                        " username varchar(255) not null, " +
                        " firstName varchar(255) not null, " +
                        " lastName varchar(255) not null, " +
                        " dateOfBirth date, " +
                        " password varchar (128) not null)";
            } else {
                throw new RuntimeException("Unsupported database type");
            }
            jdbcTemplate.update(sqlCreateTable);
        } catch (DataAccessException e) {
            System.out.println("Table already exists.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from user", new UserMapper());
    }

    @Override
    public void save(User u) {
        if (u.getId() == 0) {
            BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(u);
            userInsert.executeAndReturnKey(source);
        } else {
          /*  jdbcTemplate.update("update user set firstName=?, lastName=?, dayOfBirth=? where id=?",
                    person.getName(), person.getSurname(),  person.getShipId()!=0?person.getShipId():null, person.getId());*/
        }
    }

    @Override
    public User find(long id) {
        return jdbcTemplate.queryForObject("select * from user where id=?", new Object[]{id}, new UserMapper());
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query("select * from user where username=?", new Object[]{username}, new UserMapper());
        if(users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public List<User> getAllUsersNoWithId(long id) {
        return jdbcTemplate.query("select * from user where id !=?", new Object[]{id}, new UserMapper());
    }

    @Override
    public List<User> getAllUsersNoWithId(long id, String name) {
        return jdbcTemplate.query("select * from user where id !=? and username like %?%", new Object[]{id, name}, new UserMapper());
    }
}
