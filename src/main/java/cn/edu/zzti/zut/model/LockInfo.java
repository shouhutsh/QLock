package cn.edu.zzti.zut.model;

import lombok.Data;

/**
 * 锁的基本信息，包含类型及唯一属性等
 */
@Data
public class LockInfo {

    private LockType lockType;

    private LockKey lockKey;

    private LockHandlerType lockHandlerType;
}
