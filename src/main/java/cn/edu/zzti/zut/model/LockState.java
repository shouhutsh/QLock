package cn.edu.zzti.zut.model;

/**
 * 同步锁状态抽象
 */
public class LockState {
    public final int code;
    public final LockThread heldLockThread;

    public LockState(int code) {
        this.code = code;
        this.heldLockThread = new LockThread(Thread.currentThread());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + code;
        result = result * 31 + heldLockThread.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LockState) {
            LockState val = (LockState) obj;
            return this.code == val.code && this.heldLockThread.equals(val.heldLockThread);
        }
        return false;
    }
}
