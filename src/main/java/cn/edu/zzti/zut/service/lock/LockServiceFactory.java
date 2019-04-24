package cn.edu.zzti.zut.service.lock;

import cn.edu.zzti.zut.config.LockConfig;
import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockType;
import cn.edu.zzti.zut.model.exception.LockCheckException;
import cn.edu.zzti.zut.service.handler.LockHandler;
import cn.edu.zzti.zut.service.handler.LockHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LockServiceFactory {

    @Autowired
    private LockConfig lockConfig;
    @Autowired
    private LockHandlerFactory lockHandlerFactory;

    private static final ThreadLocal<Map<LockKey, LockService>> local = ThreadLocal.withInitial(HashMap::new);

    public boolean isHeldByCurrentThread(LockKey lockKey) {
        LockService lockService = local.get().get(lockKey);
        return (null != lockService) && lockService.isHeldByCurrentThread();
    }

    public LockService newInstance(LockType lockType, LockKey key) throws LockCheckException {
        LockHandler handler = lockHandlerFactory.singleInstance(lockConfig.lockHandlerType);
        LockService service;
        switch (lockType) {
            case Reentrant:
                service = new ReentrantLockService(key, handler);
                break;
            default:
                throw new LockCheckException();
        }
        local.get().put(key, service);
        return service;
    }
}
