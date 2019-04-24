package cn.edu.zzti.zut.aspect;

import cn.edu.zzti.zut.config.LockConfig;
import cn.edu.zzti.zut.model.LockCheck;
import cn.edu.zzti.zut.model.LockInfo;
import cn.edu.zzti.zut.service.lock.LockServiceFactory;
import cn.edu.zzti.zut.utils.KeyParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(0)
public class LockCheckAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockServiceFactory lockServiceFactory;
    @Autowired
    private LockConfig lockConfig;

    /**
     * 从 lockCheck 提取校验信息
     * 从 lockServiceFactory 中检查当前线程是否持有锁
     * 若持有则继续进行
     * 否侧按照等待策略，执行处理逻辑，允许用户自定义处理
     */
    @Around(value = "@annotation(lockCheck)")
    public Object around(ProceedingJoinPoint joinPoint, LockCheck lockCheck) throws Throwable {
        LockInfo lockInfo = transLockInfo(joinPoint, lockCheck);

        if (!lockServiceFactory.isHeldByCurrentThread(lockInfo.getLockKey())) {
            lockCheck.failedStrategy().handle(lockInfo);
        }
        return joinPoint.proceed();
    }

    private LockInfo transLockInfo(ProceedingJoinPoint joinPoint, LockCheck lockCheck) {
        LockInfo lockInfo = new LockInfo();
        lockInfo.setLockType(lockCheck.type());
        lockInfo.setLockKey(KeyParser.getSpelDefinitionKey(lockCheck.keyExpression(), getMethod(joinPoint), joinPoint.getArgs()));
        lockInfo.setLockHandlerType(lockConfig.getLockHandlerType());
        return lockInfo;
    }

    private static Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),
                        method.getParameterTypes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method;
    }
}
