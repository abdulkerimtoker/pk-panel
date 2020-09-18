package toker.panel.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import toker.panel.entity.Troop;
import toker.panel.annotation.DefaultTroopValue;
import toker.panel.repository.TroopRepository;

import javax.inject.Inject;
import java.lang.reflect.Method;

@Aspect
public class DefaultTroopValueInterceptor {

    private TroopRepository troopRepo;

    @Inject
    public void setTroopRepo(TroopRepository troopRepo) {
        this.troopRepo = troopRepo;
    }

    @Pointcut("execution(public toker.panel.entity.Troop *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultTroopValue)")
    public void getting() { }

    @Around("getting()")
    public Troop aroundGetting(ProceedingJoinPoint joinPoint) throws Throwable {
        Troop value = (Troop) joinPoint.proceed();
        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            try {
                Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
                return troopRepo.getOne(getter.getAnnotation(DefaultTroopValue.class).id());
            } catch (NoSuchMethodException ignored) { }
        }
        return value;
    }
}
