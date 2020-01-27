import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Building {
	private static final String HIGHLIGHTLARGE = "assets/highlight_large.png";
	private Image image = new Image(HIGHLIGHTLARGE);
	
	private double x;
	private double y;
	private boolean selected = false;
	
	public Building(double x, double y) throws SlickException {
		this.x = x;
		this.y = y;
	}
	
	public void follow(Camera camera) {
		camera.followSprite(this);
		selected = true;
	}
	
	public void update(World world) {
	}
	
	public void deselect() {
		selected = false;
	}
	
	public boolean selectOrNot() {
		return selected;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public void render(Camera camera) {
		if (selected == true) {
			image.drawCentered((int)camera.globalXToScreenX(x),
					   (int)camera.globalYToScreenY(y));
		}
	}
	
	public void textDisplay(Graphics g) {
	}
	
	public String methods() {
		return "none";
	}
}
