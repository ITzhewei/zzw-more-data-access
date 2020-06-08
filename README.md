#  这是什么?
    这是一个多数据源获取数据的工具.
#  背景
    我们经常会碰到这种场景,当我们需要获取一种数据的时候首先从localCache中拿,如果拿不到的话,就从remoteCache获取,然后返回数据,并写入到
    localCache,如果remoteCache中也没有就去readDb,获取到的数据再去写入remoteCache以及localCache.
    这样形成的代码大概是这样的:
    ```
    Data localCacheData = localCache.get(key);
    if(localData == null) {
        Data remoteCacheData = remoteCache.get(key);
    }
    if (remoteCacheData != null) {
        localCache.set(key, localCache);
        return localCache;
    }
    ...
    ```
    这样写起来非常繁琐不说,对于业务理解上也更繁琐.
    所以开发了这样一个组件来帮助我们更好地处理这种情况.
# 使用
```
    private MoreDataAccess<String, String> dataAccess = new MoreDataAccess<String, String>() {
        @Override
        public void set(String key, String value) {
            //这里dataSource可以是localCache/remoteCache/db
            这里dataSource可以是localCache.put(key, value);
        }
        @Override
        public String get(String key) {
            return 这里dataSource可以是localCache.get(key);
        }
    };
    //这里我们可以定义三个dataAccess 比如 localCacheDataAccess remoteCacheDataAccess dbDataAccess
    DataAccessUtils.get("key1", Arrays.asList(localCacheDataAccess, remoteCacheDataAccess, dbDataAccess));
```

    这样写就可以直接通过三个数据源进行获取和写入数据了.
    流程是这样的,先从第一个dataAccess那里获取数据获取到就直接返回,没有获取到就调用下一个dataAccess,并调用这个dataAccess之前的
    dataAccess的set方法.

