package cn.edu.zzti.zut.service.lock;

import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockState;
import cn.edu.zzti.zut.model.LockThread;
import cn.edu.zzti.zut.service.handler.LockHandler;
import lombok.AllArgsConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

@AllArgsConstructor
public abstract class AbstractLockService {

    protected static final LockState INIT_STATE = null;

    private final LockKey key;

    private final LockHandler lockHandler;

    // FIXME 简单实现，随机等待一定时间再次尝试获取锁，后边需要实现阻塞通知等操作
    public final void acquire(int arg) throws InterruptedException {
        while (true) {
            if (tryAcquire(arg)) {
                return;
            }
            Thread.sleep(new Random().nextInt(200));
        }
    }

    // FIXME 需要实现阻塞通知等操作
    public final boolean tryAcquireNanos(int arg, long nanosTimeout) {
        long deadline = System.nanoTime() + nanosTimeout;
        while (true) {
            if (tryAcquire(arg)) {
                return true;
            }
            if (deadline < System.nanoTime()) {
                return false;
            }
            try {
                Thread.sleep(new Random().nextInt(200));
            } catch (InterruptedException e) {
                return false;
            }
        }
    }

    // FIXME 需要实现阻塞通知等操作
    public final boolean release(int arg) {
        if (! tryRelease(arg)) {
            return false;
        }
        return true;
    }

    // region 需要子类实现方法
    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout) {
        throw new NotImplementedException();
    }

    public final boolean releaseShared(int arg) {
        throw new NotImplementedException();
    }


    protected boolean tryAcquire(int arg) {
        throw new NotImplementedException();
    }


    protected boolean tryRelease(int arg) {
        throw new NotImplementedException();
    }


    protected boolean isHeldExclusively() {
        throw new NotImplementedException();
    }
    // endregion

    // region 子类可以使用的工具方法
    protected LockState getState() {
        return lockHandler.getState(key);
    }

    protected boolean compileAndSwap(LockState expire, LockState update) {
        return lockHandler.compileAndSwap(key, expire, update);
    }

    protected LockThread getLockThread() {
        return new LockThread(Thread.currentThread());
    }
    // endregion
}
