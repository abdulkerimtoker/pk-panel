package toker.panel.aop.interceptor;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Test {

    @Pointcut("execution(* toker.panel.PanelApplication.run(..))")
    public void sea() { }

    @After("sea()")
    public void intercept() {
        System.out.println("ase");
    }
}
