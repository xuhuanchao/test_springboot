package com.xhc.test.springboot.aop;

import com.xhc.test.springboot.repository.StudentDaoImpl;
import com.xhc.test.springboot.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xuhuanchao on 2018/5/24.
 */
@Aspect
@Component
public class RedisCache {

    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数,(*,String)代表第一个参数为任何值,第二个为String类型.
     举例说明:
     任意公共方法的执行：
     execution(public * *(..))
     任何一个以“set”开始的方法的执行：
     execution(* set*(..))
     AccountService 接口的任意方法的执行：
     execution(* com.xyz.service.AccountService.*(..))
     定义在service包里的任意方法的执行：
     execution(* com.xyz.service.*.*(..))
     定义在service包和所有子包里的任意类的任意方法的执行：
     execution(* com.xyz.service..*.*(..))
     定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：
     execution(* com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))")
     ***> 最靠近(..)的为方法名,靠近.*(..))的为类名或者接口名,如上例的JoinPointObjP2.*(..))
     */
    @Pointcut("execution(* com.xhc.test.springboot.repository..*.find*(..)) || execution(* com.xhc.test.springboot.repository..*.get*(..))")
    public void executeQuery() {
    }

    @Pointcut("execution(* com.xhc.test.springboot.repository..*.add*(..)) || execution(* com.xhc.test.springboot.repository..*.modify*(..))")
    public void executeTranscation(){

    }

    @After("executeQuery()")
    public void saveRedis(JoinPoint point){
        Object[] args = point.getArgs();
        String name = point.getSignature().getName();
        if(redisUtil != null){
            redisUtil.put(name, 1);
        }
        log.info("aop point after execute");
    }

    @Around("executeTranscation()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint){
        try {
            log.info("aop point before around");
            Object proceed = joinPoint.proceed();
            log.info("aop point after around");
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
