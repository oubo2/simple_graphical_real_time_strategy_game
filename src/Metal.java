import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Metal extends Resource{
	private static final String METAL_MINE = "assets/resources/metal_mine.png";
	private Image image;
	private int amount = 500;
	
	public Metal(double x, double y) throws SlickException {
		super(x, y);
		image = new Image(METAL_MINE);
	}
	public void mine(int mined) {
		amount = amount - mined;
	}
	public void render(Camera camera) {
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	
	public int getAmount() {
		return amount;
	}
}
