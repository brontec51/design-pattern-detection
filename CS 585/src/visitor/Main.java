package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public class Main {

    public static void main(String[] args) {
        Acceptor acceptor = new FinalAcceptor();
        acceptor.accept(new VisitorImpl());
    }
}
