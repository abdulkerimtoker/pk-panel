package toker.panel.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import toker.panel.annotation.DefaultStringValue;

import java.lang.reflect.Method;

@Aspect
public class DefaultStringValueInterceptor {

    @Pointcut("execution(public String *(..)) && @annotation(toker.panel.annotation.DefaultStringValue)")
    public void getting() { }

    public String aroundGetting(ProceedingJoinPoint joinPoint) throws Throwable {
        String value = (String) joinPoint.proceed();
        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            try {
                Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
                return getter.getAnnotation(DefaultStringValue.class).value();
            } catch (NoSuchMethodException ignored) { }
        }
        return value;
    }
}
