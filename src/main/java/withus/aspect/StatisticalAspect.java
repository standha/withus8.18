package withus.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticalAspect {
	private static final Logger logger = LoggerFactory.getLogger(StatisticalAspect.class);

	/**
	 * Gets invoked before methods annotated with {@link withus.aspect.Statistical}.
	 */
	@Before("@annotation(withus.aspect.Statistical)")
	public void updateStatisticsAround(JoinPoint joinPoint) {
		logger.info("{}: About to update the statistics.", joinPoint.getSignature());

		// TODO: UPDATE THE STATISTICS HERE.
		logger.info("TODO: UPDATE THE STATISTICS HERE.");
	}
}
