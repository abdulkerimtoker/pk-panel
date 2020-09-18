package toker.panel.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import toker.panel.annotation.DefaultBooleanValue;

import java.lang.reflect.Method;

@Aspect
public class DefaultBooleanValueInterceptor {

    @Pointcut("execution(public Boolean *(..)) && @annotation(toker.panel.annotation.DefaultBooleanValue)")
    public void getting() {}

    @Around("getting()")
    public Boolean aroundGetting(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean value = (Boolean) joinPoint.proceed();
        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            try {
                Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
                return getter.getAnnotation(DefaultBooleanValue.class).value();
            } catch (NoSuchMethodException ignored) { }
        }
        return value;
    }
}
