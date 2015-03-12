package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public class NormalLifeMilkHandler extends MilkHandler {

    public NormalLifeMilkHandler(Handler successor) {
        super(successor);
    }

    @Override
    public void handle(Milk milk) {
        if (!milk.isLongLife()) {
            System.out.printf("I'm handling %.1f normal-life milk by %s.\n", milk.getFat(), milk.getManufacturer());
        }

        try {
            successor.handle(milk);
        }
        catch (NullPointerException e) {

        }
    }
}
