package org.appfuse.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * This class interacts with Spring's JDBC framework to save and
 * retrieve User objects.
 *
 * @author Matt Raible
 */
public class UserDaoJdbc extends JdbcDaoSupport implements UserDao {

    private DataFieldMaxValueIncrementer incrementer;
    
    public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
        this.incrementer = incrementer;
    }
    
    public List getUsers() {
        return new UsersQuery(getDataSource()).execute();
    }
    
    public User getUser(Long id) {       
        List users = new UserQuery(getDataSource()).execute(new Object[]{id});
        if (users.isEmpty()) {
            throw new ObjectRetrievalFailureException(User.class, id);  
        }
        return (User) users.get(0);
    }
    
    public void saveUser(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO app_user (id, first_name, last_name) ";
                   sql += "values (?, ?, ?)";
            SqlUpdate su = new SqlUpdate(getDataSource(), sql);
            su.declareParameter(new SqlParameter("id", Types.BIGINT));
            su.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
            su.declareParameter(new SqlParameter("last_name", Types.VARCHAR));
            su.compile();
            
            Long id = new Long(incrementer.nextLongValue());
            user.setId(id);
            
            Object[] params = new Object[] 
                {user.getId(), user.getFirstName(), user.getLastName()};
            su.update(params);
            
            /* You can use this instead of the incrementer if you're using a
               JDBC 3.0-compliant driver that supports getGeneratedKeys()
            KeyHolder keys = new GeneratedKeyHolder();
            su.setReturnGeneratedKeys(true);
            su.update(params, keys);
            user.setId(new Long(keys.getKey().longValue())); */        

            if (logger.isDebugEnabled()) {
                logger.info("user's id is: " + user.getId());
            }
        } else {
            getJdbcTemplate().update("UPDATE app_user SET first_name = ?, last_name = ? WHERE id = ?",
                new Object[] {user.getFirstName(), user.getLastName(), user.getId()});
        }
    }
    
    public void removeUser(Long id) {
        getJdbcTemplate().update("DELETE FROM app_user WHERE id = ?",
                new Object[] {id});
    }
    
    // ~========================================================================
    // ~ Private MappingSqlQueries
    // ~========================================================================
    
    // Query to get a single User
    class UserQuery extends MappingSqlQuery {
        public UserQuery(DataSource ds) {
            super(ds, "SELECT * FROM app_user WHERE id = ?");
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }
        protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(new Long(rs.getLong("id")));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            return user;
        }
    }
    
    // Query to get a list of User objects
    class UsersQuery extends MappingSqlQuery {
        public UsersQuery(DataSource ds) {
            super(ds, "SELECT * FROM app_user");
            compile();
        }
        protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(new Long(rs.getLong("id")));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            return user;
        }
    }
}

