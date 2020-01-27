import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Scout extends Unit{
	private static final String SCOUT = "assets/units/scout.png";
	private Image image;
	private static final double SPEED = 0.25;
	
	public Scout(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(SCOUT);
	}
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	public double getSpeed() {
		return SPEED;
	}
}

