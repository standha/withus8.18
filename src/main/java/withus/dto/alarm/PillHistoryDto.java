package withus.dto.alarm;

public class PillHistoryDto {
	private Long pillId;
	private Boolean finished;

	public PillHistoryDto() { }
	public PillHistoryDto(Long pillId, Boolean finished) {
		this.pillId = pillId;
		this.finished = finished;
	}

	public Long getPillId() { return pillId; }
	public Boolean getFinished() { return finished; }
}
