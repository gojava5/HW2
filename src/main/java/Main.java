import dao.jdbc.JdbcDeveloperDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    private JdbcDeveloperDao jdbcDeveloperDao;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = context.getBean(Main.class);

        main.getAll();

    }
    private void getAll () {
        jdbcDeveloperDao.getAll().forEach(System.out::println);
    }

    public void setJdbcDeveloperDao(JdbcDeveloperDao jdbcDeveloperDao) {
        this.jdbcDeveloperDao = jdbcDeveloperDao;
    }
}
