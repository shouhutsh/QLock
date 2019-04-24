package cn.edu.zzti.zut.service.lock;

import java.util.concurrent.TimeUnit;

/**
 * TODO 需要考虑两点：
 *  1. tryCatch与保护的资源的key需要对应起来，如何优雅的保证
 *  2. 防止死锁（但是目前实现将tryCatch的逻辑交给程序员自己实现，因此死锁不需要由框架解决）
 */
public interface LockService {

    void lock() throws InterruptedException;

    boolean tryLock(long time, TimeUnit unit);

    boolean unlock();

    boolean  isHeldByCurrentThread();
}
