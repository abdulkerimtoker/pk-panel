package toker.panel.aop.interceptor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import toker.panel.annotation.DefaultBooleanValue

@Aspect
class DefaultBooleanValueInterceptor {

    @Pointcut("execution(public Boolean *(..)) && @annotation(toker.panel.annotation.DefaultBooleanValue)")
    fun getting() { }

    @Around("getting()")
    @Throws(Throwable::class)
    fun aroundGetting(joinPoint: ProceedingJoinPoint): Boolean? {
        val value = joinPoint.proceed() as Boolean?
        if (value == null) {
            val type = joinPoint.signature.declaringType
            try {
                val getter = type.getDeclaredMethod(joinPoint.signature.name)
                return getter.getAnnotation(DefaultBooleanValue::class.java).value
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return value
    }
}