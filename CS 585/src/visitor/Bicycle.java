package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public class Bicycle implements Acceptor {

    public int wheels;

    public Bicycle(int wheels) {
        this.wheels = wheels;
    }

    public void cycle() {
        System.out.println("I'm cycling with " + wheels + " wheels.");
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
