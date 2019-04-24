package cn.edu.zzti.zut.config;

import cn.edu.zzti.zut.model.LockHandlerType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LockConfig {

    // FIXME
    public LockHandlerType lockHandlerType = LockHandlerType.Test;

}
