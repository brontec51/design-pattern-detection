package chorimpl;

/**
 * Created by misko on 17.1.2015.
 */
public class Milk {
    private float fat;
    private boolean longLife;
    private String manufacturer;

    public static final float WHOLE = 3;
    public static final float SEMI_SKIMMED = 1.5f;
    public static final float SKIMMED = 0.5f;
    public static final boolean LONG_LIFE = true;

    public Milk(float fat, boolean longLife, String manufacturer) {
        this.fat = fat;
        this.longLife = longLife;
        this.manufacturer = manufacturer;
    }

    public float getFat() {
        return fat;
    }

    public boolean isLongLife() {
        return longLife;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
