### 下载redis源码

（1）下redis源码包，解压

```bash
http://distfiles.macports.org/redis/
tar zxf redis-5.0.8.tar.gz
```

（2）安装依赖

`yum -y install gcc gcc-c++`

### 配置集群

（1） 配置集群

```
cd /usr/local
mkdir redis_cluster  
cd redis_cluster/
mkdir 700{0..5}
vim /usr/local/redis_cluster/7000/redis.conf 
```

```
port 7000
cluster-enabled yes  ##开启集群模式
cluster-config-file nodes_7000.conf ##设定了保存节点配置文件的路径，它由 Redis 集群在启动时创建， 并在有需要时自动进行更新
cluster-node-timeout 5000
appendonly yes
daemonize yes
```

在文件夹 7000 至 7005 中， 各创建一个 redis.conf 文件， 文件的内容可以使用上面的示例配置文件， 但记得将配置中的端口号从 7000 改为与文件夹名字相同的端口号。

（2）启动redis示例

```
redis-server /usr/local/redis_cluster/7000/redis.conf
...... 
redis-server /usr/local/redis_cluster/7005/redis.conf 
```

- 测试7000端口的redis服务：

```
redis-cli -p 7000
info
```

###  创建集群

(1)创建集群
Redis5.0以后不再支持ruby，而是使用自带的redis-cli命令就可以创建集群,在弹出的页面输入yes，出现[OK] All 16384 slots covered.说明集群从创建成功

```
redis-cli --cluster  create --cluster-replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```

```
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 127.0.0.1:7004 to 127.0.0.1:7000
Adding replica 127.0.0.1:7005 to 127.0.0.1:7001
Adding replica 127.0.0.1:7003 to 127.0.0.1:7002
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: da8a4269ba339e451d6af8d137dfcfb3404b971c 127.0.0.1:7000
   slots:[0-5460] (5461 slots) master
M: cbb1548b9bd11550ef8be27589b73db6660e1f24 127.0.0.1:7001
   slots:[5461-10922] (5462 slots) master
M: 88ee4393f0ca05ed5e40a52b752612f3b14f695a 127.0.0.1:7002
   slots:[10923-16383] (5461 slots) master
S: c6f48fb5fceb79e344600f5e5b92d0fccbc771ae 127.0.0.1:7003
   replicates da8a4269ba339e451d6af8d137dfcfb3404b971c
S: 3c50a2765fd8e822e305df069bf34ddf45c06d0b 127.0.0.1:7004
   replicates cbb1548b9bd11550ef8be27589b73db6660e1f24
S: 4f8f952ee9bf55870a589034371fd175c27a67b0 127.0.0.1:7005
   replicates 88ee4393f0ca05ed5e40a52b752612f3b14f695a
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
...
>>> Performing Cluster Check (using node 127.0.0.1:7000)
M: da8a4269ba339e451d6af8d137dfcfb3404b971c 127.0.0.1:7000
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: 4f8f952ee9bf55870a589034371fd175c27a67b0 127.0.0.1:7005
   slots: (0 slots) slave
   replicates 88ee4393f0ca05ed5e40a52b752612f3b14f695a
M: 88ee4393f0ca05ed5e40a52b752612f3b14f695a 127.0.0.1:7002
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: c6f48fb5fceb79e344600f5e5b92d0fccbc771ae 127.0.0.1:7003
   slots: (0 slots) slave
   replicates da8a4269ba339e451d6af8d137dfcfb3404b971c
M: cbb1548b9bd11550ef8be27589b73db6660e1f24 127.0.0.1:7001
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 3c50a2765fd8e822e305df069bf34ddf45c06d0b 127.0.0.1:7004
   slots: (0 slots) slave
   replicates cbb1548b9bd11550ef8be27589b73db6660e1f24
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
[root@server1 local]# redis-cli --cluster  info 127.0.0.1:7000
127.0.0.1:7000 (da8a4269...) -> 0 keys | 5461 slots | 1 slaves.
127.0.0.1:7002 (88ee4393...) -> 0 keys | 5461 slots | 1 slaves.
127.0.0.1:7001 (cbb1548b...) -> 0 keys | 5462 slots | 1 slaves.
[OK] 0 keys in 3 masters.
0.00 keys per slot on average.
```

(2)集群完整性检查
集群完整性是指所有的槽都分配到存活的redis主节点上，只要16384个槽中有一个槽未被分配，则表示集群不完整

```
redis-cli --cluster  check 127.0.0.1:7000
```
