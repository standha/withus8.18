package withus.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Result<T>
{
	private final Code code;
	private final T data;

	private Result(Code code, T data)
	{
		this.code = code;
		this.data = data;
	}

	public enum Code
	{
		OK, OK_NULL, ERROR, ERROR_DUPLICATE_ID, ERROR_DUPLICATE_CONTACT, ERROR_NO_EXIST_CAREGIVER, ERROR_DATABASE,
		ERROR_MODIFYING_NULL
	}
}
