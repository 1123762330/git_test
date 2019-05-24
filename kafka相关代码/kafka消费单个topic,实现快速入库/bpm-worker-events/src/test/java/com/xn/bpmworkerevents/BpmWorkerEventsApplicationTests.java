package com.xn.bpmworkerevents;

import com.xn.bpmworkerevents.domain.PoolWorker;
import com.xn.bpmworkerevents.service.PoolWorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BpmWorkerEventsApplicationTests {

	@Autowired
	private PoolWorkerService poolWorkerService;
	@Test
	public void contextLoads() {
		List<PoolWorker> poolWorkers = poolWorkerService.findAll();
		System.out.println(poolWorkers);
	}

}
