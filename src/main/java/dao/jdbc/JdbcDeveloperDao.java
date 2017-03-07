package dao.jdbc;

import dao.AbstractDeveloperDao;
import model.Developer;
import model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperDao implements AbstractDeveloperDao {
    private static final Logger LOGGER= LoggerFactory.getLogger(JdbcDeveloperDao.class);
    private static final String GET_ALL_SQL = "SELECT * FROM developers";
    private static final String GET_BY_FIRST_NAME_SQL = "SELECT * FROM developers WHERE first_name = ?";
    private static final String GET_BY_FULL_NAME_SQL = "SELECT * FROM developers WHERE first_name = ? AND last_name = ?";
    private static final String DELETE_DEVELOPER = "DELETE FROM developers WHERE id = ?";
    private static final String AFFECTED_ROWS = "SELECT ROW_COUNT()";
    private static final String UPDATE_SALARY = "UPDATE developers SET salary = ? WHERE id = ?";
    private static final String UPDATE_SKILL = "INSERT INTO developer_skills VALUES (?,?)";
    private static final String FIND_ID_SKILL = "SELECT id FROM skills WHERE name = ?";

    private DataSource dataSource;

    private Connection getConnection () throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Developer> getAll() {
        try (Connection connection = getConnection()){
            try(Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_SQL)){
                LOGGER.info("Successfully executed GET_ALL_SQL query");
                return getDevelopers(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing GET_ALL_SQL query");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Developer> read(String firstName) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_FIRST_NAME_SQL)) {
                preparedStatement.setString(1, "name");
                try( ResultSet resultSet = preparedStatement.executeQuery()){
                    LOGGER.info("Successfully executed GET_BY_FIRST_NAME_SQL query");
                    return getDevelopers(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing GET_BY_FIRST_NAME_SQL query");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Developer> read(String firstName, String lastName) {
        try(Connection connection = getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_FULL_NAME_SQL)){
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    LOGGER.info("Successfully executed GET_BY_FULL_NAME_SQL query");
                    return getDevelopers(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing GET_BY_FULL_NAME_SQL query");
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(Developer developer) {
        try(Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEVELOPER)) {
                preparedStatement.setInt(1, developer.getId());
                preparedStatement.executeUpdate();
            }
            return checkDeletedRows(connection);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing DELETE_DEVELOPER query");
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        try(Connection connection = getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEVELOPER)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
            return checkDeletedRows(connection);
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing DELETE_DEVELOPER query");
            throw new RuntimeException(e);
        }
    }


    @Override
    public Developer updateSalary(Developer developer, int newSalary) {
        try (Connection connection = getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SALARY)){
                preparedStatement.setInt(1, newSalary);
                preparedStatement.setInt(2, developer.getId());
                preparedStatement.executeUpdate();
                LOGGER.info("Successfully executed UPDATE_SALARY query");
                return developer;
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while executing UPDATE_SALARY query");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Developer updateSkill(Developer developer, String skill) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            Integer skillId = null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ID_SKILL)) {
                preparedStatement.setString(1, skill);
                ResultSet resultSet = preparedStatement.executeQuery(FIND_ID_SKILL);
                resultSet.next();
                skillId = resultSet.getInt("id");
            }
            if (skill != null){
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SKILL)){
                    preparedStatement.setInt(1, developer.getId());
                    preparedStatement.setInt(2, skillId);
                    developer.getSkills().add(new Skill(skillId, skill));
                }
            }
            LOGGER.info("Successfully executed UPDATE_SKILL query");
            return developer;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Exception occurred while executing rollback for UPDATE_SKILL query");
                throw new RuntimeException(e);
            }
            LOGGER.error("Exception occurred while executing UPDATE_SKILL query");
            throw new RuntimeException(e);
        }
        finally {
            if (connection != null){
                try{
                    connection.setAutoCommit(true);
                    connection.close();
                }
                catch (SQLException e){
                    LOGGER.error("Exception occurred while setting AutoCommit to False in UPDATE_SKILL query");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Developer getDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt("id"));
        developer.setFirstName(resultSet.getString("first_name"));
        developer.setLastName(resultSet.getString("last_name"));
        developer.setSalary(resultSet.getInt("salary"));
        return developer;
    }

    private List<Developer> getDevelopers(ResultSet resultSet) throws SQLException {
        List <Developer> developers = new ArrayList<>();
        while (resultSet.next()){
            Developer developer = getDeveloper(resultSet);
            developers.add(developer);
        }
        return developers;
    }

    private boolean checkDeletedRows(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(AFFECTED_ROWS)){
            resultSet.next();
            int count = resultSet.getInt("row_count()");
            if(count > 0) {
                LOGGER.info("Successfully executed DELETE_DEVELOPER query. Deleted: " + count + " rows" );
                return true;
            }
            LOGGER.info("Successfully executed DELETE_DEVELOPER query. Developer not found. No rows deleted." );
            return false;
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
