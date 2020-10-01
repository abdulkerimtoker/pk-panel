package toker.panel.bean

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextHolder : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        toker.panel.bean.applicationContext = applicationContext
    }
}

private lateinit var applicationContext: ApplicationContext

val ApplicationContext: ApplicationContext
    get() = applicationContext

