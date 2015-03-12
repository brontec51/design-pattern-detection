package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public class LongLifeMilkHandler extends MilkHandler {


    public LongLifeMilkHandler(Handler successor) {
        super(successor);
    }

    @Override
    public void handle(Milk milk) {
        if (milk.isLongLife()) {
            System.out.printf("I'm handling %.1f long-life milk by %s.\n", milk.getFat(), milk.getManufacturer());
        }
        try {
            successor.handle(milk);
        }
        catch (NullPointerException e) {

        }
    }
}
