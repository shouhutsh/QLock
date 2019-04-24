package cn.edu.zzti.zut.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 锁校验注解，方便切面扫描，以及根据信息转换 LockInfo 信息
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LockCheck {

    LockType type();

    String keyExpression();

    LockCheckFailedStrategy failedStrategy();
}
