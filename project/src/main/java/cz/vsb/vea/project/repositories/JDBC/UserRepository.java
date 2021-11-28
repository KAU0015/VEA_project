package cz.vsb.vea.project.repositories.JDBC;

import cz.vsb.vea.project.converters.LocalDateTimestampConverter;
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

    @Autowired
    LocalDateTimestampConverter localDateTimestampConverter;

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
                sqlCreateTable = "CREATE TABLE user(id INTEGER NOT NULL AUTO_INCREMENT," +
                        " username varchar(255) not null, " +
                        " first_name varchar(255) not null, " +
                        " last_name varchar(255) not null, " +
                        " day_of_birth TIMESTAMP, " +
                        " password varchar (128) not null," +
                        " CONSTRAINT User_PK PRIMARY KEY (id))";
            } else {
                throw new RuntimeException("Unsupported database type");
            }
            jdbcTemplate.update(sqlCreateTable);
            System.out.println("User table created");
        } catch (DataAccessException e) {
            System.out.println("Table already exists.");
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
            jdbcTemplate.update("insert into user (username, first_name, last_name, day_of_birth, password) values (?, ?, ?, ?, ?)",
                    u.getUsername(), u.getFirstName(), u.getLastName(), localDateTimestampConverter.convert(u.getDayOfBirth()), u.getPassword()
                    );
        } else {
          /*  jdbcTemplate.update("update user set firstName=?, lastName=?, dayOfBirth=? where id=?",
                    person.getName(), person.getSurname(),  person.getShipId()!=0?person.getShipId():null, person.getId());*/
            jdbcTemplate.update("update user set first_name=?, last_name=?, day_of_birth=? where id=?",
                    new Object[]{u.getFirstName(), u.getLastName(), localDateTimestampConverter.convert(u.getDayOfBirth()), u.getId()}
            );
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
