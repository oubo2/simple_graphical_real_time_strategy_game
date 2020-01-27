import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Truck extends Unit{
	private static final String TRUCK = "assets/units/truck.png";
	private Image image;
	private static final double SPEED = 0.25;
	private static final int X_OFFSET = 32;
	private static final int Y_OFFSET = 100;
	private static final int FIFTEEN_SEC = 15000;
	private boolean creating = false;
	private boolean finishCreating = false;
	private int time = 0;
	
	public Truck(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(TRUCK);
	}
	
	public void update(World world) {
		super.update(world);
		if (super.selectOrNot() ==  true && creating == false) {
			Input input = world.getInput();
			if (input.isKeyPressed(Input.KEY_1)) {
				if (world.isPositionOccupied(super.getX(), super.getY())) {
					creating = true;
					System.out.println("Truck ordered to self destroy");
				}
			}
		}
		if (creating == true) {
			int delta = world.getDelta();
			time = time + delta;
			if (time>=FIFTEEN_SEC) {
				finishCreating = true;
				try {
					world.addBuilding(new CommandCenter(super.getX(),super.getY()));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean selfdestroy() {
		return finishCreating;
	}
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	
	public void textDisplay(Graphics g) {
		if (super.selectOrNot() == true) {
			g.drawString(("1- Create Command Centre"), X_OFFSET, Y_OFFSET);
		}
	}
	public double getSpeed() {
		return SPEED;
	}
}

