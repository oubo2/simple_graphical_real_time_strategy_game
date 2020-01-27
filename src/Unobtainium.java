import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Unobtainium extends Resource{
	private static final String UNOBTAINIUM_MINE = "assets/resources/unobtainium_mine.png";
	private Image image;
	private int amount = 50;
	
	public Unobtainium(double x, double y) throws SlickException {
		super(x, y);
		image = new Image(UNOBTAINIUM_MINE);
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
