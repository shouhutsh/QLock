package cn.edu.zzti.zut.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 持有锁的线程信息
 */
@Data
@AllArgsConstructor
public class LockThread implements Comparable<LockThread> {

    private Thread thread;


    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + thread.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LockThread) {
            LockThread val = (LockThread) obj;
            return this.thread.equals(val.thread);
        }
        return false;
    }

    @Override
    public int compareTo(LockThread lockThread) {
        return (this.thread.equals(lockThread.thread)) ? 0 : -1;
    }
}
