package utils;


import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.objenesis.SpringObjenesis;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    private static final SpringObjenesis OBJENESIS = new SpringObjenesis();

    public static <S, T> T toTarget(S source, Class<T> targetClass) {
        T target = null;
        if (source != null) {
            target = ReflectUtil.newInstance(targetClass);
            toTarget(source, target);
        }
        return target;
    }

    public static <S, T> T toTarget(S entity, T target) {
        if (entity != null && target != null) {
            org.springframework.beans.BeanUtils.copyProperties(entity, target);
        }
        return target;
    }

    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, boolean isToUnderlineCase, String... ignoreProperties) {
        return beanToMap(bean, targetMap, isToUnderlineCase, new IgnorePropertyPredicate(ignoreProperties));
    }

    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, boolean isToUnderlineCase, BiPredicate<String, Object> ignorePredicate) {
        return beanToMap(bean, targetMap, isToUnderlineCase ? StrUtil::toUnderlineCase : null, ignorePredicate);
    }

    public static Map<String, Object> beanToMap(Object bean, Map<String, Object> targetMap, Editor<String> keyEditor, BiPredicate<String, Object> ignorePredicate) {
        if (bean == null) {
            return null;
        }

        final Collection<BeanDesc.PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();

        String key;
        Method getter;
        Object value;
        for (BeanDesc.PropDesc prop : props) {
            key = prop.getFieldName();
            // 过滤class属性
            // 得到property对应的getter方法
            getter = prop.getGetter();
            if (null != getter) {
                // 只读取有getter方法的属性
                try {
                    value = getter.invoke(bean);
                } catch (Exception ignore) {
                    continue;
                }
                if (!ignorePredicate.test(key, value)) {
                    key = keyEditor == null ? key : keyEditor.edit(key);
                    if (null != key) {
                        targetMap.put(key, value);
                    }
                }
            }
        }
        return targetMap;
    }

    /**
     * 创建实例, 不需要关心构造函数
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        return OBJENESIS.newInstance(clazz);
    }

    public static final class IgnorePropertyPredicate implements BiPredicate<String, Object> {
        private String[] ignoreProperties;

        public IgnorePropertyPredicate(String[] ignoreProperties) {
            this.ignoreProperties = ignoreProperties;
        }

        @Override
        public boolean test(String s, Object o) {
            return StrUtil.containsAny(s, ignoreProperties);
        }
    }
}
