import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Unit {
	private static final String HIGHLIGHT = "assets/highlight.png";
	private Image image = new Image(HIGHLIGHT);
	
	private double x;
	private double y;
	private double targetX;
	private double targetY;
	private boolean selected = false;
	private boolean moving = false;
	private Camera camera;
	
	public Unit(double x, double y) throws SlickException {
		this.x = x;
		this.y = y;
		targetX = x;
		targetY = y;
	}
	
	public void follow(Camera camera) {
		this.camera = camera;
		camera.followSprite(this);
		selected = true;
	}
	
	public void update(World world) {
		Input input = world.getInput();
		
		// If the mouse button is being clicked, set the target to the cursor location
		if (selected) {
			if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				targetX = camera.screenXToGlobalX(input.getMouseX());
				targetY = camera.screenYToGlobalY(input.getMouseY());
			}
		}
		// If we're close to our target, reset to our current position
		if (World.distance(x, y, targetX, targetY) <= getSpeed()) {
			moving = false;
			resetTarget();
		} else {
			moving = true;
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - y, targetX - x);
			double dx = (double)Math.cos(theta) * world.getDelta() * getSpeed();
			double dy = (double)Math.sin(theta) * world.getDelta() * getSpeed();
			// Check the tile is free before moving; otherwise, we stop moving
			if (world.isPositionFree(x + dx, y + dy)) {
				x += dx;
				y += dy;
			} else {
				moving = false;
				resetTarget();
			}
		}
	}
	
	private void resetTarget() {
		targetX = x;
		targetY = y;		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setTargetX(double tragetX) {
		this.targetX = tragetX;
	}
	
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
	
	public void deselect() {
		selected = false;
	}
	
	public boolean selectOrNot() {
		return selected;
	}
	
	public double getSpeed() {
		return 1;
	}
	
	public boolean inAction() {
		return moving;
	}
	
	public void textDisplay(Graphics g) {
	}
	
	public void render(Camera camera) {
		if (selected == true) {
			image.drawCentered((int)camera.globalXToScreenX(x),
					   (int)camera.globalYToScreenY(y));
		}
	}
}
