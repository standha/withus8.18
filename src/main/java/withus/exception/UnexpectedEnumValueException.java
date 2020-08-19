package withus.exception;

public class UnexpectedEnumValueException extends RuntimeException {
	public UnexpectedEnumValueException(Enum<?> value, Class<?> type) {
		super(String.format("Unexpected enum value \"%s\" for type \"%s\".", value, type));
	}
}
