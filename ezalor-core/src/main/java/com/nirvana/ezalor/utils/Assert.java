package com.nirvana.ezalor.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言。
 */
public class Assert {

    /* 错误信息 */

    private static final String IS_TRUE = "表达式必须为真。";
    private static final String IS_NULL = "参数必须为空。";
    private static final String NOT_NULL = "参数不能为空。";
    private static final String IS_EMPTY = "集合必须为空。";
    private static final String NOT_EMPTY = "集合不能为空。";
    private static final String HAS_LENGTH = "参数不能为空。";
    private static final String[] LENGTH_BETWEEN = {"参数长度必须介于", "与", "之间。"};
    private static final String[] LENGTH_LT = {"参数长度必须小于", "。"};
    private static final String[] LENGTH_GT = {"参数长度必须大于", "。"};
    private static final String[] LENGTH_LTE = {"参数长度必须小于等于", "。"};
    private static final String[] LENGTH_GTE = {"参数长度必须大于等于", "。"};

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, IS_TRUE);
    }

    public static void isNull(Object object, String msg) {
        if (object != null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isNull(Object object) {
        isNull(object, IS_NULL);
    }

    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notNull(Object object) {
        notNull(object, NOT_NULL);
    }

    public static void hasLength(String text, String msg) {
        if (text == null || text.length() <= 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void hasLength(String text) {
        hasLength(text, HAS_LENGTH);
    }

    /**
     * 断言text的长度介于min，max之间。
     *
     * @param text 字符串
     * @param min  如果不需要最小值可传null值。
     * @param max  如果不需要最大值可传null值。
     */
    public static void lengthBetween(String text, Integer min, Integer max, String msg) {
        notNull(text);
        if (min == null && max == null) {
            return;
        }
        int length = text.length();
        if (min != null && max != null) {
            if (min > max) {
                throw new IllegalArgumentException("最小值不能大于最大值。");
            }
            if (length < min || length > max) {
                throw new IllegalArgumentException(msg);
            }
        }
        if (min == null) {
            if (length > max) {
                throw new IllegalArgumentException(msg);
            }
        }
        if (min != null && max == null) {
            if (length < min) {
                throw new IllegalArgumentException(msg);
            }
        }
    }

    /**
     * 断言text的长度介于min，max之间。
     *
     * @param text 字符串
     * @param min  如果不需要最小值可传null值。
     * @param max  如果不需要最大值可传null值。
     */
    public static void lengthBetween(String text, Integer min, Integer max) {

        notNull(text);
        if (min == null && max == null) {
            return;
        }
        int length = text.length();
        if (min != null && max != null) {
            if (min > max) {
                throw new IllegalArgumentException("最小值不能大于最大值。");
            }
            if (length < min || length > max) {
                throw new IllegalArgumentException(LENGTH_BETWEEN[0] + min + LENGTH_BETWEEN[1] + max + LENGTH_BETWEEN[2]);
            }
        }
        if (min == null) {
            if (length > max) {
                throw new IllegalArgumentException(LENGTH_LTE[0] + max + LENGTH_LTE[1]);
            }
        }
        if (min != null && max == null) {
            if (length < min) {
                throw new IllegalArgumentException(LENGTH_GTE[0] + min + LENGTH_GTE[1]);
            }
        }
    }

    /**
     * 参数text长度必须小于参数a（不能等于），使用lengthBetween函数代替。
     *
     * @see #lengthBetween(String, Integer, Integer, String)
     */
    public static void lengthLessThan(String text, int a, String msg) {
        notNull(text);
        int length = text.length();
        if (length >= a) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 参数text长度必须小于参数a（不能等于），使用lengthBetween函数代替。
     *
     * @see #lengthBetween(String, Integer, Integer)
     */
    public static void lengthLessThan(String text, int a) {
        lengthLessThan(text, a, LENGTH_LT[0] + a + LENGTH_LT[1]);
    }

    /**
     * 参数text长度必须大于参数a（不能等于），使用lengthBetween函数代替。
     *
     * @see #lengthBetween(String, Integer, Integer, String)
     */
    public static void lengthLargerThan(String text, int a, String msg) {
        notNull(text);
        int length = text.length();
        if (length <= a) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 参数text长度必须大于参数a（不能等于），使用lengthBetween函数代替。
     *
     * @see #lengthBetween(String, Integer, Integer)
     */
    public static void lengthLargerThan(String text, int a) {
        lengthLargerThan(text, a, LENGTH_GT[0] + a + LENGTH_GT[1]);
    }


    public static void isEmpty(Collection collection, String msg) {
        if (collection != null && collection.size() > 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isEmpty(Collection collection) {
        isEmpty(collection, IS_EMPTY);
    }

    public static void isEmpty(Map map, String msg) {
        if (map != null && map.size() > 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isEmpty(Map map) {
        isEmpty(map, IS_EMPTY);
    }

    public static void isEmpty(Object[] array, String msg) {
        if (array != null && array.length > 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isEmpty(Object[] array) {
        isEmpty(array, IS_EMPTY);
    }

    public static void notEmpty(Collection collection, String msg) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Collection collection) {
        notEmpty(collection, NOT_EMPTY);
    }

    public static void notEmpty(Map map, String msg) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Map map) {
        notEmpty(map, NOT_EMPTY);
    }

    public static void notEmpty(Object[] array, String msg) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, NOT_EMPTY);
    }

}
