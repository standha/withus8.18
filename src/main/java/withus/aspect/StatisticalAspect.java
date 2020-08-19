package withus.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticalAspect {
	private static final Logger logger = LoggerFactory.getLogger(StatisticalAspect.class);

	/**
	 * Gets invoked around methods annotated with {@link withus.aspect.Statistical}.
	 */
	@Around("@annotation(withus.aspect.Statistical)")
	public Object updateStatisticsAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		try {
			// TODO: UPDATE THE STATISTICS HERE.
			logger.info("TODO: UPDATE THE STATISTICS HERE.");
			result = proceedingJoinPoint.proceed();
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage(), exception);
		}

		return result;
	}
}
