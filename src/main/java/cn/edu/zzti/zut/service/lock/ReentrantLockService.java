package cn.edu.zzti.zut.service.lock;

import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockState;
import cn.edu.zzti.zut.service.handler.LockHandler;

import java.util.concurrent.TimeUnit;

public class ReentrantLockService implements LockService {

    private final Sync sync;

    public ReentrantLockService(LockKey key, LockHandler lockHandler) {
        this.sync = new Sync(key, lockHandler);
    }

    @Override
    public void lock() throws InterruptedException {
        this.sync.acquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return this.sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public boolean unlock() {
        return this.sync.release(1);
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return this.sync.isHeldExclusively();
    }

    private static final class Sync extends AbstractLockService {

        public Sync(LockKey key, LockHandler lockHandler) {
            super(key, lockHandler);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            LockState state = getState();
            if (INIT_STATE == state) {
                return compileAndSwap(INIT_STATE, new LockState(arg));
            } else if (state.heldLockThread.equals(getLockThread())) {
                return compileAndSwap(state, new LockState(state.code + arg));
            } else {
                return false;
            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            LockState state = getState();
            if (INIT_STATE != state && state.heldLockThread.equals(getLockThread())) {
                return compileAndSwap(state, new LockState(state.code - arg));
            }
            return false;
        }

        @Override
        protected boolean isHeldExclusively() {
            if (INIT_STATE == getState()) {
                return false;
            } else {
                return getLockThread().equals(getState().heldLockThread);
            }
        }
    }
}
