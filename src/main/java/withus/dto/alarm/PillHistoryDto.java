package withus.dto.alarm;

public class PillHistoryDto {
    private String id;
    private Boolean finished;

    public PillHistoryDto() {
    }

    private PillHistoryDto(String Id, Boolean finished) {
        this.id = Id;
        this.finished = finished;
    }

    public String getId() {
        return id;
    }

    public boolean getFinished() {
        return finished;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private Boolean finished;

        public Builder setPillId(String id) {
            this.id = id;
            return this;
        }

        public Builder setFinished(Boolean finished) {
            this.finished = finished;
            return this;
        }

        public PillHistoryDto createPillHistoryDto() {
            return new PillHistoryDto(id, finished);
        }
    }
}
