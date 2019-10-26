package com.contant.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class jedisTest {
	
	@Test
	public void testJestCluster() {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.166",7001));
		nodes.add(new HostAndPort("192.168.25.166",7002));
		nodes.add(new HostAndPort("192.168.25.166",7003));
		nodes.add(new HostAndPort("192.168.25.166",7004));
		nodes.add(new HostAndPort("192.168.25.166",7005));
		nodes.add(new HostAndPort("192.168.25.166",7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("sex", "boy");
		String string = jedisCluster.get("sex");
		System.out.println(string);
		jedisCluster.close();
	}

}
