package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public class SkimmedMilkHandler extends MilkHandler {

    public SkimmedMilkHandler(Handler successor) {
        super(successor);
    }

    @Override
    public void handle(Milk milk) {
        if (milk.getFat() == Milk.SKIMMED) {
            System.out.printf("I'm handling skimmed milk by %s.\n", milk.getManufacturer());
        }
        try {
            successor.handle(milk);
        }
        catch (NullPointerException e) {

        }
    }
}
