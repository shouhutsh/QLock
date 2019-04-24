package cn.edu.zzti.zut.service.handler;

import cn.edu.zzti.zut.model.LockHandlerType;
import cn.edu.zzti.zut.model.LockType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LockHandlerFactory {

    private static final ConcurrentHashMap<LockType, LockHandler> handlers = new ConcurrentHashMap<LockType, LockHandler>();

    @Autowired
    private SimpleLockHandler simpleLockHandler;

    // FIXME
    public LockHandler singleInstance(LockHandlerType type) {
        return simpleLockHandler;
    }
}
