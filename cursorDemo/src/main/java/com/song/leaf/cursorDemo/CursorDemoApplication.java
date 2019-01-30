package com.song.leaf.cursorDemo;

import com.song.leaf.cursorDemo.registry.ServiceRegistry;
import com.song.leaf.cursorDemo.registry.ServiceRegistryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursorDemoApplication {

	public static void main(String[] args) {
		ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
		try {
			serviceRegistry.registryService("risk-service", "172.29.8.67:12004");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

