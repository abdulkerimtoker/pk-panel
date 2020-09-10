package toker.warbandscripts.panel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import toker.warbandscripts.panel.util.DefaultStringValue;

import java.lang.reflect.Method;

@Aspect
@Component
public class DefaultStringValueInterceptor {

    @Pointcut("execution(public String *(..))")
    public void getting() { }

    @Pointcut("@annotation(toker.warbandscripts.panel.util.DefaultStringValue)")
    public void annotated() {}

    @Around("getting() && annotated()")
    public String intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        String value = (String) joinPoint.proceed();

        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
            return getter.getAnnotation(DefaultStringValue.class).value();
        }

        return value;
    }
}
