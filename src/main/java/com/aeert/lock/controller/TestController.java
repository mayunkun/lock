package com.aeert.lock.controller;

import com.aeert.lock.service.LockService;
import com.aeert.lock.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author l'amour solitaire
 * @Description 测试redis锁
 * @Date 2020/11/17 上午9:27
 **/
@RestController
@RequestMapping("/redis/lock")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestController {

    private final LockService lockService;

    @GetMapping("/test")
    public R test(String param1, String param2, String param3) {
        return R.ok(lockService.getInfo(param1, param2, param3));
    }

    @GetMapping("/test1")
    public R test1(String param1, String param2, String param3) {
        return R.ok(lockService.getInfo(param1, param2, param3));
    }
}
