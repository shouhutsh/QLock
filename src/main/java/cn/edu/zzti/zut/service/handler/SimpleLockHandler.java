package cn.edu.zzti.zut.service.handler;

import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class SimpleLockHandler implements LockHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentHashMap<LockKey, AtomicReference<LockState>> map = new ConcurrentHashMap<>();

    @Override
    public LockState getState(LockKey key) {
        return getValue(key).get();
    }

    @Override
    public boolean compileAndSwap(LockKey key, LockState expect, LockState update) {
        AtomicReference<LockState> state = getValue(key);
        boolean success = state.compareAndSet(expect, update);
        if (success && update.code == 0) {
            delValue(key);
        }
        return success;
    }

    private AtomicReference<LockState> getValue(LockKey key) {
        return map.computeIfAbsent(key, (k) -> new AtomicReference<>(null));
    }

    private AtomicReference<LockState> delValue(LockKey key) {
        return map.remove(key);
    }
}

