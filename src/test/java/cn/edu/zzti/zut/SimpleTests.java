package cn.edu.zzti.zut;

import cn.edu.zzti.zut.model.LockKey;
import cn.edu.zzti.zut.model.LockType;
import cn.edu.zzti.zut.service.lock.LockService;
import cn.edu.zzti.zut.service.lock.LockServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleApplication.class)
public class SimpleTests {

	@Autowired
	TestService testService;
	@Autowired
	LockServiceFactory lockServiceFactory;

	@Test
	public void multithreadingTest()throws Exception{
		int i = 0;
		Random random = new Random();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		while (i < 10000) {
			final int num = i;
			executorService.submit(() -> {
				try {
					if (random.nextBoolean()) {
						lockFunction(num);
					} else {
						noLockFunction(num);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			i++;
		}
		executorService.awaitTermination(5, TimeUnit.SECONDS);
	}

	private void lockFunction(int num) throws Exception {
		System.out.println("进入加锁线程:[" + Thread.currentThread().getName() + "]");

		LockService lock = lockServiceFactory.newInstance(LockType.Reentrant, new LockKey("simple_test"));
		lock.lock();
		try {
			String result = testService.getValue("sleep" + num);
			System.out.println("加锁线程:[" + Thread.currentThread().getName() + "]拿到结果=》" + result + new Date().toLocaleString());
		} finally {
			lock.unlock();
		}

		System.out.println("离开加锁线程:[" + Thread.currentThread().getName() + "]");
	}

	private void noLockFunction(int num) throws Exception {
		System.out.println("进入未加锁线程:[" + Thread.currentThread().getName() + "]");

		String result = testService.getValue("sleep" + num);
		System.out.println("未加锁线程:[" + Thread.currentThread().getName() + "]拿到结果=》" + result + new Date().toLocaleString());

		System.out.println("进入未加锁线程:[" + Thread.currentThread().getName() + "]");
	}
}
