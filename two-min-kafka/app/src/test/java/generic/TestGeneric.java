package generic;

import org.junit.Test;

/**
 * Java 泛型 <? super T> 中 super 怎么 理解？与 extends 有何不同？
 * https://www.zhihu.com/question/20400700
 */
public class TestGeneric {

    static class Fruit {
    }

    static class Apple extends Fruit {
    }

    static class Plate<T> {
        private T item;
        public Plate(T t) {
            item = t;
        }
        public void set(T t) {
            item = t;
        }
        public T get() {
            return item;
        }
    }

    @Test
    public void test1() {
//        Plate<Fruit> p = new Plate<Apple>(new Apple());
        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
        int hashCode = p.get().hashCode();
        System.out.println("test p:" + hashCode);
    }

    @Test
    public void UpperBound_set_fail() {
        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());

        // UpperBound can only get()
        p.set(new Fruit());
        p.set(new Apple());

        Fruit f1 = p.get();
        Object o1 = p.get();
        Apple a1 = p.get();
    }

    @Test
    public void LowerBound_get_fail() {
        Plate<? super Fruit> p = new Plate<Apple>(new Apple());

        p.set(new Apple());
        p.set(new Fruit());

        Apple a1 = p.get();
        Fruit f1 = p.get();
        Object o1 = p.get();
    }
}
