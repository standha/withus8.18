package com.bluecore.withus.dto;

public class Result<T> {
	private final Code code;
	private final T data;

	private Result(Code code, T data) {
		this.code = code;
		this.data = data;
	}

	public Code getCode() { return code; }
	public T getData() { return data; }

	public static <T> Builder<T> builder() { return new Builder<>(); }

	public static class Builder<T> {
		private Code code;
		private T data;

		public Builder<T> setCode(Code code) {
			this.code = code;
			return this;
		}

		public Builder<T> setData(T data) {
			this.data = data;
			return this;
		}

		public Result<T> createResult() {
			return new Result<>(code, data);
		}
	}

	public enum Code {
		OK,
		OK_NULL,
		ERROR,
		ERROR_DUPLICATE_ID,
		ERROR_DUPLICATE_CONTACT,
		ERROR_NO_EXIST_CAREGIVER,
		ERROR_DATABASE
	}
}
