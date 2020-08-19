package withus.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

public class LevelEqualOrAboveFilter extends AbstractMatcherFilter<ILoggingEvent> {
	private Level level;

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL;
		}

		Level level = event.getLevel();
		FilterReply filterReply;
		if (level.isGreaterOrEqual(this.level)) {
			filterReply = onMatch;
		} else {
			filterReply = onMismatch;
		}

		return filterReply;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
