package wanted.assignment;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class TestBaseConfig {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void after() {
		jdbcTemplate.execute("delete from member");
		jdbcTemplate.execute("alter table member auto_increment = 1");

		jdbcTemplate.execute("delete from post");
		jdbcTemplate.execute("alter table post auto_increment = 1");

		jdbcTemplate.execute("delete from post_detail");
		jdbcTemplate.execute("alter table post_detail auto_increment = 1");
	}
}
