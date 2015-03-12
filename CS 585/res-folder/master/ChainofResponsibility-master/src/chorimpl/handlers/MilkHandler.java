package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public abstract class MilkHandler implements Handler {

    protected Handler successor;

    public MilkHandler(Handler successor) {
        this.successor = successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
}
