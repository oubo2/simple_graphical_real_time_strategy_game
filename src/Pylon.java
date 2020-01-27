import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList; 

public class Pylon extends Building{
	private static final String PYLON = "assets/buildings/pylon.png";
	private static final String PYLONACTIVATE = "assets/buildings/pylon_active.png";
	private static final double DISTANCE = 32;
	private Image image;
	private boolean activated = false;
	
	public Pylon(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(PYLON);
	}
	
	public void update(World world) {
		super.update(world);
		if (activated == false) {
			ArrayList<Unit> units = world.allUnits();
			for (Unit u:units) {
				if (World.distance(u.getX(), u.getY(), this.getX(), this.getY()) <= DISTANCE) {
					activated = true;
					Engineer.addCapacity();
					try {
						image = new Image(PYLONACTIVATE);
					} catch (SlickException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
}
