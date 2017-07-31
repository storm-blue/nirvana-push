package com.nirvana.push.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("JavaReflectionMemberAccess")
public class CodeEnumerator<T extends Enum> {

    private final Map<Integer, T> cache = new HashMap<>();

    public CodeEnumerator(Class<T> enumClass) {
        try {
            Field field = enumClass.getDeclaredField("code");
            field.setAccessible(true);
            for (T t : enumClass.getEnumConstants()) {
                cache.put(field.getInt(t), t);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("this Enum has no field names code.");
        } catch (IllegalAccessException ignore) {
        }
    }

    public T get(int code) {
        return cache.get(code);
    }

}
