package wanted.assignment.common.generator;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeGenerator {

	public static Timestamp getCurrentDateTime() {
		return Timestamp.valueOf(LocalDateTime.now());
	}
}
