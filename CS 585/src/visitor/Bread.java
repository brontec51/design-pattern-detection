package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public class Bread implements Acceptor {

    public boolean brown;

    public Bread(boolean brown) {
        this.brown = brown;
    }

    public void eat() {
        if (brown) {
            System.out.println("I'm eating brown bread.");
        } else {
            System.out.println("I'm eating white bread.");
        }
    }

    public void setBrown(boolean brown) {
        this.brown = brown;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
