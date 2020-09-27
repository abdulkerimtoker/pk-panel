package toker.panel.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import toker.panel.bean.SelectedServerIdKt;
import toker.panel.entity.Faction;
import toker.panel.entity.pk.FactionPK;
import toker.panel.annotation.DefaultFactionValue;
import toker.panel.repository.FactionRepository;

import javax.inject.Inject;
import java.lang.reflect.Method;

@Aspect
public class DefaultFactionValueInterceptor {

    private FactionRepository factionRepo;

    @Inject
    public void setFactionRepo(FactionRepository factionRepo) {
        this.factionRepo = factionRepo;
    }

    @Pointcut("execution(public toker.panel.entity.Faction *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultFactionValue)")
    public void getting() { }

    @Around("getting()")
    public Faction aroundGetting(ProceedingJoinPoint joinPoint) throws Throwable {
        Faction value = (Faction) joinPoint.proceed();
        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            try {
                Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
                return factionRepo.getOne(new FactionPK(getter.getAnnotation(DefaultFactionValue.class).index(),
                        SelectedServerIdKt.getSelectedServerId()));
            } catch (NoSuchMethodException ignored) { }
        }
        return value;
    }
}
