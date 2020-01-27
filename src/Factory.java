import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Factory extends Building{
	private static final String FACTORY = "assets/buildings/factory.png";
	private Image image;
	private static final int X_OFFSET = 32;
	private static final int Y_OFFSET = 100;
	private static final int FIVE_MINUTE = 5000;
	private boolean creating = false;
	private int time;
	
	public Factory(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(FACTORY);
	}
	
	public void update(World world) {
		super.update(world);
		if (super.selectOrNot() ==  true && creating == false) {
			Input input = world.getInput();
			if (input.isKeyPressed(Input.KEY_1)) {
				if (world.resourceLeft().getMetal() >= 150) {
					world.resourceLeft().changeMetal(-150);
					creating = true;
					time = 0;
					System.out.println("Factory ordered to create truck");
				}
			}
		}
		if (creating == true) {
			int delta = world.getDelta();
			time = time + delta;
			if (time>=FIVE_MINUTE) {
				try {
					world.addUnit(new Truck(super.getX(),super.getY()));
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
			g.drawString(("1- Create Truck"), X_OFFSET, Y_OFFSET);
		}
	}
}