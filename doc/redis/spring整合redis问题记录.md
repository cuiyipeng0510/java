## 1. Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException:

1. application.yml的redis配置中的spring.redis.timeout中连接超时时间（毫秒）中时间设置不能为0

2. 找到redis的配置文件 redis.conf
   vim redis.conf
   修改 protected-mode  yes 改为：protected-mode no
   注释掉 #bin 127.0.0.1

3. yml文件中配置参数

   ```
   # REDIS (RedisProperties)
   # Redis数据库索引（默认为0）
   spring.redis.database=0
   
   # Redis服务器地址
   spring.redis.host=192.168.30.103
   
   # Redis服务器连接端口
   spring.redis.port=6379
   
   # 连接池最大连接数（使用负值表示没有限制）
   spring.redis.jedis.pool.max-active=8
   
   # 连接池最大阻塞等待时间（使用负值表示没有限制）
   spring.redis.jedis.pool.max-wait=-1
   
   # 连接池中的最大空闲连接
   spring.redis.jedis.pool.max-idle=8
   
   # 连接池中的最小空闲连接
   spring.redis.jedis.pool.min-idle=0
   
   # 连接超时时间（毫秒）
   spring.redis.timeout=5000
   ```

   