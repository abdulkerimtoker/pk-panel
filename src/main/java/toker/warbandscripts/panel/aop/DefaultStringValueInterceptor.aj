package toker.warbandscripts.panel.aop;

public aspect DefaultStringValueInterceptor {

    pointcut getting() : call(* toker.warbandscripts.panel.entity.Player.get*(..));

    Object around() : getting() {
        System.out.println("999");
        int x = 0;
        return proceed();
    }
}
