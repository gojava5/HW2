package ua.goit.java.dao.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.dao.DeveloperDAO;
import ua.goit.java.model.Developer;
import ua.goit.java.model.Skill;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alexandrsemenov on 26.02.17.
 */
public class jdbcDeveloperDAO implements DeveloperDAO {

    private javax.sql.DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(jdbcDeveloperDAO.class);

    public boolean deleteById(int id) {
        String SQL = "DELETE FROM developers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate(SQL);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB");
            return false;
        }
    }

    public Developer create(int id, String name, int phone, BigDecimal salary, Collection<Skill> skills) {
        Developer developer = null;
        final String INSERT_SQL = "insert into developers(id, name, phone, salary) values (?, ?, ?, ?)";
        final String INSERT_COMPONENT_SQL = "insert into " +
                "dev_skill (devID, skillID) values (?, ?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            if (id!=0 && name!=null && phone!=0 && salary!=null) {
                try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setInt(3, phone);
                    ps.setBigDecimal(4, salary);
                    ps.executeUpdate();
                }
                if (skills != null) {
                    try (PreparedStatement ps =
                                 connection.prepareStatement(INSERT_COMPONENT_SQL)) {
                        for (Skill skills1 : skills) {
                            ps.setLong(1, skills1.getId());
                            ps.setLong(2, id);
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }
            }
            else LOGGER.error("Not enough parameters to create developer.");

            developer = new Developer();
            developer.setId(id);
            developer.setSkills(skills);
            developer.setName(name);
            developer.setSalary(salary);
            developer.setPhone(phone);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Exception occurred while rollback.");
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("Exception occurred while closing connection.");
                }
            }
        }
        return developer;
    }

    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
