package com.example.demo;

import com.example.demo.mapper.CityMapper;
import com.example.demo.model.City;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {
	@Autowired
	CityMapper cityMapper;
	@Test
	public void contextLoads() {
		City city = cityMapper.selectByPrimaryKey(1);
		Assert.assertNotNull(city);
	}

}
