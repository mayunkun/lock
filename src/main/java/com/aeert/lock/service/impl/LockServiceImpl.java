package com.aeert.lock.service.impl;

import com.aeert.lock.annotation.Lock;
import com.aeert.lock.service.LockService;
import org.springframework.stereotype.Service;

/**
 * @Author l'amour solitaire
 * @Description LockService
 * @Date 2020/11/17 下午2:24
 **/
@Service("lockService")
public class LockServiceImpl implements LockService {

    @Override
    @Lock(key = "targetClass + methodName + ':' + #p0 + #p1 + #p2", expires = 20000L, registryKey = "redis-key",message = "当前操作正在执行！")
    public String getInfo(String param1, String param2, String param3) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param1 + param2 + param3;
    }
}
