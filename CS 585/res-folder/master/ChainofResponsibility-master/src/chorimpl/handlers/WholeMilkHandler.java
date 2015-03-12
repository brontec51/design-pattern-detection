package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public class WholeMilkHandler extends MilkHandler {

    public WholeMilkHandler(Handler successor) {
        super(successor);
    }

    @Override
    public void handle(Milk milk) {
        if (milk.getFat() == Milk.WHOLE) {
            System.out.printf("I'm handling whole milk by %s.\n", milk.getManufacturer());
        }

        try {
            successor.handle(milk);
        }
        catch (NullPointerException e) {

        }
    }
}
