# string

- 将string保存到redis 中 set


​	如果存在，那么覆盖 srtnx 这个命令，如果key存在就不添加了

​	set key value

- 获取string get

​	get key

- 追加字符串 append key value

​	append ip 9999 返回追加之后的字符串的长度

​	如果key不存在就相当于set命令

- 获取key对应的value的长度 strlen key

​	返回value 的长度

- 将key中储存则数字值增1 incr key

​	如果key 不存在，那么先初始化key 为 0 然后incr 变成1 incr k1 v1 (k1 不存在) k1 1

​	如果key 存在但是是一个字符串，那么就报错 ERR value is not an integer or out of range

- 将key中储存的数字值减1 decr key （基本同上）

- 将key中储存的数字值增增。自定义步长 incrby key 步长

- 将key中储存的数字值增减。自定义步长 decrby key 步长

​	decrby age 20 加20 so easy

- 可以同时设置多组键值对 mset key1 value1 key2 value2 ........

​	mset k1 v1 k2 v2 k3 v3 mget k1 k2 k3 得： v1 v2 v3

- 可以同时一个或多个key的value值 mget key1 key2 key3 ....

- 同时设置多组键值对 msetnx key1 value1 key2 value2 .....

​	同时设置多组键值对，当且仅当所有给定的key都不存在时，才能设置成功原子性，有一个失败，则都失败

- 可以获取1个或多个键值对 mgetnx key1 key2 key3

- 子字符串操作

​	setrange key 起始位置 字符串 初始hello 命令 setrange 3 asd 结果 helasd 把3后面得三个字符重写（覆盖子字符串）如果是空，就是起始位置之前的char用\x00来表示，新增一个key

​	getrange key 起始位置(最小0) 结束位置(string.size()-1) 和数组一样 下标从0开始 getrange key 0 3 得：hela 闭区间截取 不影响redis得存储，只是单纯的取出来 下标可以是负数，和python差不多，最后一个字符是-1 ，然后自右向左，减一，-3 -1 就是取最后三个char

- setex key second value

​	设置时候指定存在时长

- getset key value

​	返回旧值，设置新值 相当于先get 然后set

# list

- lpush （list push ）


​	将一个或者多个值**依次插入到列表的表头**（列表的最左侧）

​	lpush key value1 value2 value3 value4 ...........

- lrange

  查看list内容 等于getrange

  lrange key start stop (如果想看全部的就是0，-1)

- rpush

  lpush 是在表头插入，rpush是在表尾插入

- lpop

  左端删除，返回删除值

- rpop

  右端删除，返回删除值

- del

  全部删除

- llen

  list 长度

- lindex

  获取index 的值 lindex key index

- lrem

  lrem key count value

  根据count 值移除指定列表中和value相等的数据：

  count > 0 : 从列表左侧移除count个和value 相同的元素

  count < 0 : 从列表右侧移除count个和value 相同的元素

  count = 0 ：从列表中删除和value相等的元素

# set

- 读取元素：smembers sismember scard srandmember

- 增加：sadd

- 删除：srem spop (spop key [count] 随机删除几个) smove()

  集合的交并差运算

- 交集：

  sinter key1 key2 key3 ..... 找到这几个集合中的相同元素

  sinterstore destination key1 key2 key3 ..... 找到的元素存储起来

- 并集：

  sunion key1 key2 key3 找到这几个集合的全部元素

  sunionstore destination key1 key2 key3 存储起来

- 差集：

  sdiff 获取一个集合中有，但是其他集合没有的元素组成一个新集合

  sdiff key1 key2 key3 (集合key1 中有但是key2 key3 中没有)

  sdiffstore destination key1 key2 key3 存储到destination

# hash

- 单key : field -vaule field -vaule field -vaule field -vaule field -vaule field -vaule (多个)

  Redis [hash] 是一个string类型的 field 和 value 的映射表，hash特别适合用于存储对象。

  Redis 中每个 hash 可以存储 2^32 - 1 键值对（40多亿)。

存取数据

- 存取：

  hset key field1 value1 [field1 value1]

  hmset key field1 value1 [field1 value1]  

  hsetnx key field value 如果field-value 不存在，就设置（返回1），存在，放弃设置（没有例子，返回0）

- 获取：

  hgetall key

  hget key field

  hmget key field [field.....]

- 进阶版获取 ：

  hlen key

  hkeys key

  hvals key

- 删除：

  hdel key field [field......]

- 存在：

  hexists key field 存在1 不存在0

- 增加：

  hincrby key field int

  hincrbyfloat key field float

# zset

zset 通常包含 3 个 关键字操作：

- key (与我们 redis 通常操作的 key value 中的key 一致)


- score (排序的分数，该分数是有序集合的关键，可以是双精度或者是整数)


- member (指我们传入的 obj，与 key value 中的 value 一致)

每个元素的分数可以重合，但是元素不能重合（本质上就是set）

​	存取数据(从小到大加了rev就是从大到小)

- 添加数据

  zadd key score member [score member] (score是数值类型)

- 获取元素

  根据指令下标 zrange key startindex stopindex [withscores]

  根据分数区间 zrangebyscore key minscore maxscore [withscores]

- 删除元素

  zrem key member [member]

- 获取member个数

  zcard key

- 获取指定元素的排名

  zrank key member (其实返回的是下标，排名从0开始的！！！)（从小到大）

  zrevrank key member （从大到小，排名从0开始 ！）

- 某个分数区间的个数

  zcount key minscore maxscore

- 指定元素查分数

  zscore key member