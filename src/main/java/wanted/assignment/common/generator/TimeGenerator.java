package wanted.assignment.common.generator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeGenerator {

	public static Timestamp getCurrentDateTime() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

	public static Date getTimeInFuture(Integer minute) {
		return Date.from(
			LocalDateTime.now().plusMinutes(minute).atZone(ZoneId.systemDefault()).toInstant());
	}
}
