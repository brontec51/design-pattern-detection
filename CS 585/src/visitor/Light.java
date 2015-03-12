package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public class Light implements Acceptor {

	private int watts;

	public Light(int watts) {
		this.watts = watts;
	}

	public void light() {
		System.out.println("This light has " + watts + " W bulb.");
	}

	public void setWatts(int watts) {
		this.watts = watts;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
