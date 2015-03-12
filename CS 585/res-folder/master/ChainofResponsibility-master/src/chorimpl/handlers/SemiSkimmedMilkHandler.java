package chorimpl.handlers;

import chorimpl.Milk;

/**
 * Created by misko on 17.1.2015.
 */
public class SemiSkimmedMilkHandler extends MilkHandler {

    public SemiSkimmedMilkHandler(Handler successor) {
        super(successor);
    }

    @Override
    public void handle(Milk milk) {
        if (milk.getFat() == Milk.SEMI_SKIMMED) {
            System.out.printf("I'm handling semi-skimmed milk by %s.\n", milk.getManufacturer());
        }
        try {
            successor.handle(milk);
        }
        catch (NullPointerException e) {

        }
    }
}
