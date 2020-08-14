package com.bluecore.withus.dto.alarm;

public class PillHistoryDto {
	private Long pillId;
	private Boolean finished;

	public PillHistoryDto() { }
	private PillHistoryDto(Long pillId, Boolean finished) {
		this.pillId = pillId;
		this.finished = finished;
	}

	public Long getPillId() { return pillId; }
	public Boolean getFinished() { return finished; }

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long pillId;
		private Boolean finished;

		public Builder setPillId(Long pillId) {
			this.pillId = pillId;
			return this;
		}
		public Builder setFinished(Boolean finished) {
			this.finished = finished;
			return this;
		}
		public PillHistoryDto createPillHistoryDto() {
			return new PillHistoryDto(pillId, finished);
		}
	}
}
