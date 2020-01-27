import org.newdawn.slick.SlickException;

public class Resource {
	private double x;
	private double y;
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public Resource(double x, double y) throws SlickException {
		this.x = x;
		this.y = y;
	}
	
	public void render(Camera camera) {
	}
	
	public int getAmount() {
		return 0;
	}
	
	public void mine(int mined) {
	}
}
