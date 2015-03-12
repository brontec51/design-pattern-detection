package chorimpl;

import chorimpl.handlers.*;

/**
 * Created by misko on 17.1.2015.
 */
public class Main {

    public static void main (String[] args) {
        Main main = new Main();

        Milk rajo = new Milk(Milk.SEMI_SKIMMED, Milk.LONG_LIFE, "Rajo");
        Milk sabi = new Milk(Milk.SKIMMED, !Milk.LONG_LIFE, "Milk-Agro");
        Milk lidl = new Milk(Milk.WHOLE, Milk.LONG_LIFE, "Lidl");

        Handler handler = new LongLifeMilkHandler(new NormalLifeMilkHandler(new SemiSkimmedMilkHandler(new SkimmedMilkHandler(new WholeMilkHandler(null)))));

        handler.handle(rajo);
        System.out.println("//=============================");
        handler.handle(sabi);
        System.out.println("//=============================");
        handler.handle(lidl);
    }

}
