package toker.panel.aop.interceptor

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import toker.panel.annotation.DefaultTroopValue
import toker.panel.entity.Troop
import toker.panel.repository.TroopRepository
import javax.inject.Inject

@Aspect
class DefaultTroopValueInterceptor {
    private var troopRepo: TroopRepository? = null
    @Inject
    fun setTroopRepo(troopRepo: TroopRepository?) {
        this.troopRepo = troopRepo
    }

    @Pointcut("execution(public toker.panel.entity.Troop *(..)) && " +
            "@annotation(toker.panel.annotation.DefaultTroopValue)")
    fun getting() {
    }

    @Around("getting()")
    @Throws(Throwable::class)
    fun aroundGetting(joinPoint: ProceedingJoinPoint): Troop? {
        val value = joinPoint.proceed() as Troop?
        if (value == null) {
            val type = joinPoint.signature.declaringType
            try {
                val getter = type.getDeclaredMethod(joinPoint.signature.name)
                return troopRepo!!.getOne(getter.getAnnotation(DefaultTroopValue::class.java).id)
            } catch (ignored: NoSuchMethodException) {
            }
        }
        return value
    }
}