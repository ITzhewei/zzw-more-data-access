package zzw;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * @author zhangzhewei
 * Created on 2020-06-06
 */
public class DataAccessUtils {

    private static <K, V> V get(K key, Iterator<MoreDataAccess<K, V>> accessIterable) {
        if (!accessIterable.hasNext()) {
            return null;
        }
        MoreDataAccess<K, V> currentAccess = accessIterable.next();
        V result = currentAccess.get(key);
        if (result == null) {
            result = get(key, accessIterable);
            if (result != null) {
                currentAccess.set(key, result);
            }
        }
        return result;
    }

    public static <K, V> V get(K key, Iterable<MoreDataAccess<K, V>> accessIterable) {
        return get(key, accessIterable.iterator());
    }

    public static <K, V> Map<K, V> get(Collection<K> keys,
            Iterable<MoreDataAccess<K, V>> accessIterable) {
        return get(keys, accessIterable.iterator());
    }

    private static <K, V> Map<K, V> get(Collection<K> keys,
            Iterator<MoreDataAccess<K, V>> accessIterable) {
        if (!accessIterable.hasNext() || CollectionUtils.isNotEmpty(keys)) {
            return Collections.emptyMap();
        }
        MoreDataAccess<K, V> dataAccess = accessIterable.next();
        Map<K, V> currentMap = dataAccess.get(keys);
        List<K> leftKeys = keys.stream() //
                .filter(k -> currentMap.get(k) != null) //
                .filter(k -> !currentMap.containsKey(k)) //
                .collect(Collectors.toList());
        Map<K, V> leftMap = get(leftKeys, accessIterable);
        if (MapUtils.isNotEmpty(leftMap)) {
            dataAccess.set(leftMap);
            currentMap.putAll(leftMap);
        }
        return currentMap;
    }

}
