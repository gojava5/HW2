package dao.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"file:src/main/resources/application-context.xml"})
public class JdbcDeveloperDaoTest {
    private JdbcDeveloperDao jdbcDeveloperDao = new JdbcDeveloperDao();

    @Test
    public void getAll() throws Exception {

        Assert.assertNotNull(jdbcDeveloperDao.getAll());
    }

    @Test
    public void read() throws Exception {

    }

    @Test
    public void read1() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void delete1() throws Exception {

    }

    @Test
    public void updateSalary() throws Exception {

    }

    @Test
    public void updateSkill() throws Exception {

    }

}