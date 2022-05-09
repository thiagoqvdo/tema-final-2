package com.thiagoqvdo.cloudnative.temafinal2.songmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SongMicroserviceApplication {
	public static void main(String[] args) {
//		DiscoveryManager.getInstance().initComponent(new MyDataCenterInstanceConfig(), new DefaultEurekaClientConfig());
//		System.out.println("Registering service to eureka with STARTING status");
		SpringApplication.run(SongMicroserviceApplication.class, args);

//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//		}

//
//		System.out.println("Done sleeping, now changing status to UP");
//		ApplicationInfoManager.getInstance().setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	}

}
