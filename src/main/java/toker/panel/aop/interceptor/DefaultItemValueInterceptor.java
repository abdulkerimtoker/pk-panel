package toker.panel.aop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import toker.panel.entity.Item;
import toker.panel.repository.ItemRepository;
import toker.panel.annotation.DefaultItemValue;

import javax.inject.Inject;
import java.lang.reflect.Method;

@Aspect
public class DefaultItemValueInterceptor {

    private ItemRepository itemRepo;

    @Inject
    public void setItemRepo(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Pointcut("execution(public toker.panel.entity.Item *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultItemValue)")
    public void getting() { }

    @Around("getting()")
    public Item aroundGetting(ProceedingJoinPoint joinPoint) throws Throwable {
        Item value = (Item) joinPoint.proceed();
        if (value == null) {
            Class<?> type = joinPoint.getSignature().getDeclaringType();
            try {
                Method getter = type.getDeclaredMethod(joinPoint.getSignature().getName());
                return itemRepo.getOne(getter.getAnnotation(DefaultItemValue.class).itemId());
            } catch (NoSuchMethodException ignored) { }
        }
        return value;
    }
}
