package toker.warbandscripts.panel.aop;

public aspect TestAspect {

    pointcut y() : call(* toker.warbandscripts.panel.entity.*.*(..));

    before() : y() {
        System.out.println("seda");
    }
}
