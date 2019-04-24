package cn.edu.zzti.zut.model;

/**
 * 锁处理器类型
 */
public enum LockHandlerType {

    // 测试使用
    Test,

    // 数据库分布式锁
    Database,

    // Redis分布式锁
    Redis,

    // Zookeeper分布式锁
    Zookeeper,

    ;
}
