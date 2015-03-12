package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public class VisitorImpl implements Visitor {

    @Override
    public void visit(Bicycle bicycle) {
        System.out.println("visiting Bicycle");
        bicycle.setWheels(45);
        bicycle.cycle();
    }

    @Override
    public void visit(Bread bread) {
        System.out.println("visiting Bread");
        bread.setBrown(false);
        bread.eat();
    }

    @Override
    public void visit(Light light) {
        System.out.println("visiting Light");
        light.setWatts(3000);
        light.light();
    }

    @Override
    public void visit(FinalAcceptor main) {
        System.out.println("visiting main");
    }
}
