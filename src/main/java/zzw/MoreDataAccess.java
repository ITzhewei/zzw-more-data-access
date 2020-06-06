package zzw;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhangzhewei
 * Created on 2020-06-06
 */
public interface MoreDataAccess<K, V> {

    default void set(K key, V value) {
        set(Collections.singletonMap(key, value));
    }

    default V get(K key) {
        return get(Collections.singleton(key)).get(key);
    }

    default void set(Map<K, V> dataMap) {

    }

    default Map<K, V> get(Collection<K> keys) {
        return Collections.emptyMap();
    }

}
