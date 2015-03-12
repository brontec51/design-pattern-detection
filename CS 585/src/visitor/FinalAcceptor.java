package visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by misko on 18.1.2015.
 */
public class FinalAcceptor implements Acceptor{

    private List<Acceptor> acceptors;

    public FinalAcceptor() {
        acceptors = new ArrayList<Acceptor>();
        acceptors.add(new Bicycle(2));
        acceptors.add(new Bread(true));
        acceptors.add(new Light(60));
    }

    @Override
    public void accept(Visitor visitor) {
        for (Acceptor acceptor : acceptors) {
            acceptor.accept(visitor);
        }
        visitor.visit(this);
    }
}
