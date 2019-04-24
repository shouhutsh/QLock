package cn.edu.zzti.zut;

import cn.edu.zzti.zut.model.LockCheck;
import cn.edu.zzti.zut.model.LockCheckFailedStrategy;
import cn.edu.zzti.zut.model.LockType;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @LockCheck(type = LockType.Reentrant, keyExpression = "'simple_test'", failedStrategy = LockCheckFailedStrategy.LOG_FAILED_REASON)
    public String getValue(String param) throws Exception {
        Thread.sleep(100);
        return "success";
    }
}
