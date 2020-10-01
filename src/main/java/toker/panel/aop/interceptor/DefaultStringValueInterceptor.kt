package toker.panel.aop.interceptor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import toker.panel.annotation.DefaultStringValue

@Aspect
class DefaultStringValueInterceptor {
    @Pointcut("execution(public String *(..)) && @annotation(toker.panel.annotation.DefaultStringValue)")
    fun getting() {
    }

    @Throws(Throwable::class)
    fun aroundGetting(joinPoint: ProceedingJoinPoint): String? {
        val value = joinPoint.proceed() as String?
        if (value == null) {
            val type = joinPoint.signature.declaringType
            try {
                val getter = type.getDeclaredMethod(joinPoint.signature.name)
                return getter.getAnnotation(DefaultStringValue::class.java).value
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return value
    }
}