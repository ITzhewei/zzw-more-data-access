package zzw;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * @author zhangzhewei
 * Created on 2020-06-06
 */
public class DataAccessUtilsTest {

    private static HashMap<String, String> cache1 = new HashMap<>();
    private static HashMap<String, String> cache2 = new HashMap<>();

    private MoreDataAccess<String, String> dataAccess1 = new MoreDataAccess<String, String>() {

        @Override
        public void set(String key, String value) {
            System.out.println("dataAccess1 set");
            cache1.put(key, value);
        }

        @Override
        public String get(String key) {
            System.out.println("dataAccess1 get");
            return cache1.get(key);
        }
    };

    private MoreDataAccess<String, String> dataAccess2 = new MoreDataAccess<String, String>() {

        @Override
        public String get(String key) {
            System.out.println("dataAccess2 get");
            return cache2.get(key);
        }
    };

    static {
        cache1.put("key1", "value1");
        cache2.put("key2", "value2");
    }

    @Test
    public void test() {
        String value1 = DataAccessUtils.get("key1", Arrays.asList(dataAccess1, dataAccess2));
        System.out.println(value1);
        String value2 = DataAccessUtils.get("key2", Arrays.asList(dataAccess1, dataAccess2));
        System.out.println(value2);
    }

}
