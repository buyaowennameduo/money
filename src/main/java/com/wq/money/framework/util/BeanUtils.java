package com.wq.money.framework.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    public static <T> T copyProperties(Object source, Class<T> classType) {
        T t;
        try {
            t = classType.newInstance();
            copyProperties(source, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }


    /***
     * 判断封装类中指定的属性名的类型
     * @param itemName 指定属性名称
     * @param clazz 功能的封装类
     * @return
     */
    public static String getFieldTypeName(String itemName, Class<?> clazz) {
        String typeName = "";
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        // 遍历声明对象域
        for (Field field : fieldList) {
            String fieldname = field.getName();
            if (fieldname.equals(itemName)) {
                typeName = (field.getType()).getName();
            }
        }
        return typeName;
    }

    public static <T> T copyProperties(Map<String, String> source, Class<T> target) {
        T t = null;
        try {
            t = target.newInstance();
            for (Map.Entry<String, String> entry : source.entrySet()) {
                if (!StringUtils.isBlank(String.valueOf(entry.getValue()))) {
                    org.apache.commons.beanutils.BeanUtils.setProperty(t, entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void transBean2Map(Object bean, Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (value != null && (value instanceof String || value instanceof Integer || value instanceof Float || value instanceof Boolean)) {
                        map.put(key, value);
                    } else if (value instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> params = (Map<String, Object>) value;
                        for (String k : params.keySet()) {
                            Object v = map.get(k);
                            if (v != null && (v instanceof String || v instanceof Integer || v instanceof Float || value instanceof Boolean))
                                map.put(k, v);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: transBean2StringMap @Description:
     * 将实体属性的值转换为字符串放到Map中 @param @param bean java实体对象 @param @param map
     * 要转换的map对象 @param @param null2Empty 是否将null转换为空字符串 @return void
     * 返回类型 @throws
     */
    public static void transBean2StringMap(Object bean, Map<String, Object> map, boolean null2Empty) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (null2Empty == true && value == null) {
                        map.put(key, "");
                    } else {
                        map.put(key, value.toString());
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("实体转换失败", e);
        }
    }

    /**
     * @param <T>
     * @Title: transBeans2StringMaps @Description:
     * 将List中的JavaBean转换成List的Map，如果JavaBean的属性为null，根据null2Empty判断是否转换为空字符串 @param @param
     * beanList @param @param mapList @param @param null2Empty
     * null2Empty 是否将null转换为空字符串 @return void 返回类型 @throws
     */
    public static <T> void transBeans2StringMaps(List<T> beanList, List<Map<String, Object>> mapList, boolean null2Empty) {
        for (Object bean : beanList) {
            Map<String, Object> map = new HashMap<String, Object>();
            transBean2StringMap(bean, map, true);
            mapList.add(map);
        }
    }

    /**
     * @Title: transNull2Empty @Description: 将Map中属性为null的设置为空字符串 @param @param
     * map 设定文件 @return void 返回类型 @throws
     */
    public static void transNull2Empty(Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    entry.setValue("");
                }
            }
        }
    }

    public static void transMap2Bean(Map<String, ? extends Object> map, Object obj) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("转换实体属性失败" + e);
        }
    }

    /**
     * 实例化一个class
     *
     * @param <T>
     * @param clazz Person.class
     * @return
     */
    public static <T> T instanceClass(Class<T> clazz) {
        if (!clazz.isInterface()) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("实例化对象失败" + e);
            }
        }
        return null;
    }

    /**
     * 通过构造函数实例化
     *
     * @param <T>
     * @param ctor
     * @param args
     * @return
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T instanceClass(Constructor<T> ctor, Object... args)
            throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        makeAccessible(ctor);
        return ctor.newInstance(args);// 调用构造方法实例化
    }

    /**
     * 查找某个class的方法
     *
     * @param clazz
     * @param methodName
     * @param paramTypes
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            return findDeclaredMethod(clazz, methodName, paramTypes);// 返回共有的方法
        }
    }

    public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException ex) {
            if (clazz.getSuperclass() != null) {
                return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
            }
            return null;
        }
    }

    /**
     * @Title: findDeclaredField @Description: 查找某个属性，如果没有返回null @param @param
     * clazz @param @param fieldName @param @return 设定文件 @return Field
     * 返回类型 @throws
     */
    public static Field findDeclaredField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
    }

    public static Method[] findDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);// 如果是私有的 设置为true 使其可以访问
        }
    }

    public static Field[] findDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }


    public final static <T, K> Map<K, List<T>> groupBy(Collection<T> list, Function<? super T, ? extends K> k) {
        return list.stream().filter(Utils::isNotEmpty).filter(e -> Utils.isNotEmpty(get(k, e))).collect(Collectors.groupingBy(k));
    }

    public final static <T, K, V> Map<K, List<V>> groupBy(Collection<T> list, Function<? super T, ? extends K> k, Function<? super T, ? extends V> v) {
        return list.stream().filter(Utils::isNotEmpty).filter(e -> Utils.isNotEmpty(get(k, e))).collect(Collectors.groupingBy(k, Collectors.mapping(v, Collectors.toList())));
    }

    public final static <T> List<T> sorted(List<T> list, Comparator<? super T> comparator) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>(0);
        }

        if (Utils.isEmpty(comparator)) {
            return list;
        }

        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

    public final static <T> List<T> filter(Predicate<T> predicate, List<T> list) {
        if (Utils.isEmpty(list)) {
            return Collections.emptyList();
        }

        if (Utils.isEmpty(predicate)) {
            return list;
        }

        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public final static <T, K, V> Map<K, V> map(T t, Function<? super T, ? extends K> kf, Function<? super T, ? extends V> vf) {
        if (Utils.anyIsEmpty(t, kf, vf)) {
            return Collections.emptyMap();
        }
        Map<K, V> map = new HashMap<>(1);
        map.put(get(kf, t), get(vf, t));
        return map;
    }

    private final static <T, K, V> Map<K, V> map(Collection<T> c, Function<? super T, ? extends K> kf, Function<? super T, ? extends V> vf) {
        if (Utils.anyIsEmpty(c, kf, vf)) {
            return Collections.emptyMap();
        }
        Map<K, V> map = new HashMap<>(c.size());
        c.forEach(t -> {
            K k = get(kf, t);
            V v = get(vf, t);
            map.put(k, v);
        });
        return map;
    }

    public final static <T, R> List<R> distinct(Collection<T> c, Function<? super T, ? extends R> f) {
        if (Utils.anyIsEmpty(c, f)) {
            return Collections.emptyList();
        }
        return c.stream().map(f).filter(Utils::isNotEmpty).distinct().collect(Collectors.toList());
    }

    /**
     * 根据bean中的方法比较返回distinct的List对象集合
     *
     * @param c
     * @param f
     * @param <T>
     * @param <R>
     * @return
     */
    public final static <T, R> Collection<T> distinctObject(Collection<T> c, Function<? super T, ? extends R> f) {
        if (Utils.anyIsEmpty(c, f)) {
            return Collections.emptyList();
        }
        return c.stream().filter(distinctPredicate(f)).collect(Collectors.toList());
    }

    private static <T, R> Predicate<T> distinctPredicate(Function<? super T, ? extends R> f) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(f.apply(t));
    }

    public final static <S, T, F, C> void set(S s, Function<S, F> sf, Function<S, C> sc, T t, Function<T, F> tf, BiConsumer<T, C> tc) {
        if (Utils.anyIsEmpty(s, sf, sc, t, tf, tc)) {
            return;
        }
        Map<F, C> map = map(s, sf, sc);
        set(t, map, tf, tc);
    }

    public final static <S, T, F, C> void set(Collection<S> sl, Function<S, F> sf, Function<S, C> sc, Collection<T> tl, Function<T, F> tf, BiConsumer<T, C> tc) {
        if (Utils.isEmpty(sl) || Utils.isEmpty(tl) || Utils.anyIsEmpty(sf, sc, tf, tc)) {
            return;
        }
        Map<F, C> map = map(sl, sf, sc);
        if (Utils.isEmpty(map)) {
            return;
        }
        tl.forEach(t -> set(t, map, tf, tc));
    }


    public final static <T, F> F get(Function<T, F> tf, T t) {
        if (Utils.anyIsEmpty(t, tf)) {
            return null;
        }
        return tf.apply(t);
    }

    private final static <T, F, C> void set(T t, Map<F, C> map, Function<T, F> tf, BiConsumer<T, C> tc) {
        if (Utils.isEmpty(map) || Utils.anyIsEmpty(t, tf, tc)) {
            return;
        }
        F f = tf.apply(t);
        if (Utils.isNotEmpty(f)) {
            C c = map.get(f);
            if (Utils.isNotEmpty(c)) {
                tc.accept(t, c);
            }
        }
    }

    private static final class Utils {
        public static boolean anyIsEmpty(Object... args) {
            for (Object arg : args) {
                if (isEmpty(arg)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean isEmpty(Object e) {
            return e == null || e.equals("");
        }

        public static boolean isNotEmpty(Object e) {
            return !isEmpty(e);
        }

        public static boolean isEmpty(Map e) {
            return e == null || e.size() == 0;
        }

        public static boolean isEmpty(Collection e) {
            return e == null || e.size() == 0;
        }
    }
}
