package cn.wwinter.lang;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author: zhangdd
 * @date: 2023-08-08
 */
public interface Iterablee<T> {
    Iteratorr<T> iteratorr();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Iteratorr<T> itr = iteratorr();
        while (itr.hasNext()) {
            action.accept(itr.next());
        }
    }
}
