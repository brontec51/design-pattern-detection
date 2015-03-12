package visitor;

/**
 * Created by misko on 18.1.2015.
 */
public interface Visitor {
	public void visit(Bicycle bicycle);

	public void visit(Bread bread);

	public void visit(Light light);

	public void visit(FinalAcceptor main);
}
