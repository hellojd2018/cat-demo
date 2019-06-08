package com.example.demo.service;

import com.dianping.cat.aop.CatAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @CatAnnotation //此处增加annotation
    @Override
    public void delete(Object entity) {
        sleep(30);
        logger.info("UserServiceImpl---删除方法:delete()---");
        getAllObjects();
    }

    @CatAnnotation //此处增加annotation
    @Override
    public void getAllObjects() {
        sleep(40);
        logger.info("UserServiceImpl---查找所有方法:getAllObjects()---");
    }

    @Override
    public void save(Object entity) {
        sleep(10);
        logger.info("UserServiceImpl---保存方法:save()---");
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }

    @CatAnnotation
    @Override
    public void update(Object entity) {
        sleep(20);
        logger.info("UserServiceImpl---更新方法:update()---");
    }

}