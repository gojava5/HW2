import dao.jdbc.JdbcDeveloperDao;
import model.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    private JdbcDeveloperDao jdbcDeveloperDao;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = context.getBean(Main.class);

//        main.getAll();
//        System.out.println("----------------------");
//        main.getDeveloper("Han");
//        System.out.println("----------------------");
//        main.getDeveloper("Bernard", "Lowe");
//        System.out.println("----------------------");
        main.deleteDeveloper("Illia");
    }

    private void deleteDeveloper(String name) {
        Developer developer = jdbcDeveloperDao.read(name).get(0);
        System.out.println(jdbcDeveloperDao.delete(developer));
    }

    private Developer setDeveloper(String name) {
        return jdbcDeveloperDao.read(name).get(0);
    }

    private void updateSkil(Developer developer, String skill) {
        System.out.println(jdbcDeveloperDao.updateSkill(developer, skill));
    }

    private void getDeveloper(String firstName, String lastName) {
        jdbcDeveloperDao.read(firstName,lastName).forEach(System.out::println);
    }

    private void getDeveloper(String firstName) {
        jdbcDeveloperDao.read(firstName).forEach(System.out::println);
    }

    private void getAll () {
        jdbcDeveloperDao.getAll().forEach(System.out::println);
    }

    public void setJdbcDeveloperDao(JdbcDeveloperDao jdbcDeveloperDao) {
        this.jdbcDeveloperDao = jdbcDeveloperDao;
    }
}
