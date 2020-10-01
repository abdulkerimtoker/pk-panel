package toker.panel.aop.interceptor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import toker.panel.annotation.DefaultItemValue
import toker.panel.entity.Item
import toker.panel.repository.ItemRepository
import javax.inject.Inject

@Aspect
class DefaultItemValueInterceptor {
    private var itemRepo: ItemRepository? = null
    @Inject
    fun setItemRepo(itemRepo: ItemRepository?) {
        this.itemRepo = itemRepo
    }

    @Pointcut("execution(public toker.panel.entity.Item *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultItemValue)")
    fun getting() {
    }

    @Around("getting()")
    @Throws(Throwable::class)
    fun aroundGetting(joinPoint: ProceedingJoinPoint): Item? {
        val value = joinPoint.proceed() as Item?
        if (value == null) {
            val type = joinPoint.signature.declaringType
            try {
                val getter = type.getDeclaredMethod(joinPoint.signature.name)
                return itemRepo!!.getOne(getter.getAnnotation(DefaultItemValue::class.java).itemId)
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return value
    }
}