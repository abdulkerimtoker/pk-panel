package toker.panel.aop.interceptor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import toker.panel.annotation.DefaultFactionValue
import toker.panel.bean.SelectedServerId
import toker.panel.entity.Faction
import toker.panel.entity.pk.FactionPK
import toker.panel.repository.FactionRepository
import javax.inject.Inject

@Aspect
class DefaultFactionValueInterceptor {
    lateinit var factionRepo: FactionRepository

    @Pointcut("execution(public toker.panel.entity.Faction *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultFactionValue)")
    fun getting() { }

    @Around("getting()")
    @Throws(Throwable::class)
    fun aroundGetting(joinPoint: ProceedingJoinPoint): Faction? {
        val value = joinPoint.proceed() as Faction?
        if (value == null) {
            val type = joinPoint.signature.declaringType
            try {
                val getter = type.getDeclaredMethod(joinPoint.signature.name)
                return factionRepo!!.getOne(FactionPK(getter.getAnnotation(DefaultFactionValue::class.java).index,
                        SelectedServerId))
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return value
    }
}