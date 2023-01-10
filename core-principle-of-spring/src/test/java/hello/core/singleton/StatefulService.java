package hello.core.singleton;

public class StatefulService {
    private int price; // 상태를 유지하는 필드 10000 -> 20000

    public void order(String name, int price){
        System.out.println("name = " + name + " price = "+ price);
        this.price = price;
    }

    public int getPrice(){
        return price;
    }

/*    // 공유되지 않는 파라미터 사용 시.
    public int order(String name, int price){
        System.out.println("name = " + name + " price = "+ price);
        return price;
    }
*/
}
