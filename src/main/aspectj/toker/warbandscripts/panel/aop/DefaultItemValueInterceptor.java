package toker.warbandscripts.panel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import toker.warbandscripts.panel.entity.Item;
import toker.warbandscripts.panel.repository.ItemRepository;
import toker.warbandscripts.panel.util.DefaultItemValue;

import java.lang.reflect.Method;

@Aspect
@Configurable(preConstruction=true)
public class DefaultItemValueInterceptor {

    private ItemRepository itemRepo;

    public void setItemRepo(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Pointcut("execution(public toker.warbandscripts.panel.entity.Item *(..))")
    public void getting() { }

    @Pointcut("@annotation(toker.warbandscripts.panel.util.DefaultItemValue)")
    public void annotated() {}

    @Around("getting() && annotated()")
    public Item intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        Item value = (Item) joinPoint.proceed();

        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
            return itemRepo.getOne(getter.getAnnotation(DefaultItemValue.class).itemId());
        }

        return value;
    }
}
