package cn.wwinter.lang;

/**
 * @author: zhangdd
 * @date: 2023/08/21
 */
public interface TreeIterator<T> {
    boolean hasNext();

    T next();
}
