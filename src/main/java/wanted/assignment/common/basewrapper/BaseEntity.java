package wanted.assignment.common.basewrapper;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import wanted.assignment.common.generator.TimeGenerator;

@Getter
public class BaseEntity<T> {

	private final T data;
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	@Builder
	private BaseEntity(T data, Timestamp createdAt, Timestamp updatedAt) {
		this.data = data;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static <T> BaseEntity<T> createData(T data) {
		Timestamp currentDateTime = TimeGenerator.getCurrentDateTime();

		return BaseEntity.<T>builder()
			.data(data)
			.createdAt(currentDateTime)
			.updatedAt(currentDateTime)
			.build();
	}

	public static <T> BaseEntity<T> updateData(T data) {
		Timestamp currentDateTime = TimeGenerator.getCurrentDateTime();

		return BaseEntity.<T>builder()
			.data(data)
			.updatedAt(currentDateTime)
			.build();
	}
}
