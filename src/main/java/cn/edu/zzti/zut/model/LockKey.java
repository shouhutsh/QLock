package cn.edu.zzti.zut.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 锁唯一键
 */
@Data
@AllArgsConstructor
public class LockKey implements Comparable<LockKey> {

    private String key;

    @Override
    public int compareTo(LockKey o) {
        return this.key.compareTo(o.key);
    }
}
