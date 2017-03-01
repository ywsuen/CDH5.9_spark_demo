package com.ywsuen.spark.kpi.util;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import scala.Serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;


public class RedisUtil implements Serializable {

    private static final long serialVersionUID = 678615884296304463L;

    public JedisCluster getClient() {
        return client;
    }

    public void setClient(JedisCluster client) {
        this.client = client;
    }

    private JedisCluster client;

    public RedisUtil(String nodes){
        Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        String[] ipAndPorts = nodes.split(",");
        for(String ipAndPort: ipAndPorts){
            String[] strs = ipAndPort.split(":");
            hostAndPorts.add(new HostAndPort(strs[0], Integer.parseInt(strs[1])));
        }
        this.client = new JedisCluster(hostAndPorts);
    }

    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    public static byte[] ObjectToByte(java.lang.Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
