import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Builder extends Unit{
	private static final String BUILDER = "assets/units/builder.png";
	private Image image;
	private static final double SPEED = 0.1;
	private static final int X_OFFSET = 32;
	private static final int Y_OFFSET = 100;
	private static final int TEN_SEC = 10000;
	private boolean creating = false;
	private int time;
	
	public Builder(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(BUILDER);
	}
	
	public void update(World world) {
		super.update(world);
		if (super.selectOrNot() ==  true && creating == false) {
			Input input = world.getInput();
			if (input.isKeyPressed(Input.KEY_1)) {
				if (world.isPositionOccupied(super.getX(), super.getY())) {
					if (world.resourceLeft().getMetal() >= 100) {
						world.resourceLeft().changeMetal(-100);
						creating = true;
						time = 0;
					}
				}
			}
		}
		if (creating == true) {
			int delta = world.getDelta();
			time = time + delta;
			if (time>=TEN_SEC) {
				try {
					world.addBuilding(new Factory(super.getX(),super.getY()));
				} catch (SlickException e) {
					e.printStackTrace();
				}
				creating = false;
			}
		}
	}
	
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	public void textDisplay(Graphics g) {
		if (super.selectOrNot() == true) {
			g.drawString(("1- Create factory"), X_OFFSET, Y_OFFSET);
		}
	}
	public double getSpeed() {
		return SPEED;
	}
}

