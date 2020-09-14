package withus.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class StatisticalAspect {
	/**
	 * Gets invoked before methods annotated with {@link withus.aspect.Statistical}.
	 */
	@Before("@annotation(withus.aspect.Statistical)")
	public void updateStatisticsAround(JoinPoint joinPoint) {
		log.info("{}: About to update the statistics.", joinPoint.getSignature());

		// TODO: UPDATE THE STATISTICS HERE.
		log.info("TODO: UPDATE THE STATISTICS HERE.");
	}
}
