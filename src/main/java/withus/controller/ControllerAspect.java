package withus.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	// TODO: 모든 GET request에 대해서만 처리 중
	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
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
