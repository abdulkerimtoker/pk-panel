package toker.warbandscripts.panel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import toker.warbandscripts.panel.util.DefaultBooleanValue;

import java.lang.reflect.Method;

@Aspect
@Component
public class DefaultBooleanValueInterceptor {

    @Pointcut("execution(public Boolean *(..))")
    public void getting() { }

    @Pointcut("@annotation(toker.warbandscripts.panel.util.DefaultBooleanValue)")
    public void annotated() {}

    @Around("getting() && annotated()")
    public Boolean intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean value = (Boolean) joinPoint.proceed();

        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
            return getter.getAnnotation(DefaultBooleanValue.class).value();
        }

        return value;
    }
}
