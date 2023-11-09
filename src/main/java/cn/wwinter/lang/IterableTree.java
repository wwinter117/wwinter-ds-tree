package cn.wwinter.lang;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author: zhangdd
 * @date: 2023-08-21
 */
public interface IterableTree<T> {
    Iterator<T> levelIterator();
    Iterator<T> PreIterator();
    Iterator<T> InIterator();
    Iterator<T> PostIterator();

    default void levelForEach(Consumer<T> action) {
        forEach(levelIterator(), action);
    }
    default void preForEach(Consumer<T> action) {
        forEach(PreIterator(), action);
    }
    default void inForEach(Consumer<T> action) {
        forEach(InIterator(), action);
    }
    default void postForEach(Consumer<T> action) {
        forEach(PostIterator(), action);
    }
    default void forEach(Iterator<T> iterator, Consumer<T> action) {
        Objects.requireNonNull(action);
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }
}
