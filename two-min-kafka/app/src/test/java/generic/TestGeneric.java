package generic;


import org.junit.Test;

public class TestGeneric {
    @Test
    public void test1() {
        Plate<Fruit> p = new Plate<Apple>(new Apple());
        int hashCode = p.get().hashCode();
        System.out.println("test p:" + hashCode);
    }
}
