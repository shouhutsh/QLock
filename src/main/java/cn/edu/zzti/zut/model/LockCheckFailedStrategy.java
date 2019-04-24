package cn.edu.zzti.zut.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 锁校验失败策略
 */
public enum LockCheckFailedStrategy {

    // 只记下日志
    LOG_FAILED_REASON() {
        @Override
        public void handle(LockInfo lockInfo) {
            // TODO
            logger.warn("LOG_FAILED_REASON");
        }
    },


    ;

    private static final Logger logger = LoggerFactory.getLogger(LockCheckFailedStrategy.class);

    public abstract void handle(LockInfo lockInfo);
}
