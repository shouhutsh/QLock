package cn.edu.zzti.zut.service.handler;

import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockState;

/**
 * 除了需要实现分布式锁之外，还需要考虑实现通知和重载的能力
 */
public interface LockHandler {

    LockState getState(LockKey key);

    boolean compileAndSwap(LockKey key, LockState expect, LockState update);
}
