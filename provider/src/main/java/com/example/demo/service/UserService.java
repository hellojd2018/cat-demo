package com.example.demo.service;

import com.dianping.cat.aop.CatAnnotation;

public interface UserService {


    public void delete(Object entity);

	public void getAllObjects();
	

	public void save(Object entity);
	

	public void update(Object entity);
}