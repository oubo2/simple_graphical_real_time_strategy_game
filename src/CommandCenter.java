import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CommandCenter extends Building{
	private static final String COMMANDCENTER = "assets/buildings/command_centre.png";
	private Image image;
	private static final int X_OFFSET = 32;
	private static final int Y_OFFSET = 100;
	private static final int FIVE_SEC = 5000;
	private boolean creating = false;
	private boolean creatingScout = false;
	private boolean creatingBuilder = false;
	private boolean creatingEngineer = false;
	private int time;
	
	public CommandCenter(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(COMMANDCENTER);
	}
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	
	public void update(World world) {
		super.update(world);
		if (super.selectOrNot() ==  true && creating == false) {
			Input input = world.getInput();
			if (input.isKeyPressed(Input.KEY_1)) {
				if (world.resourceLeft().getMetal() >= 5) {
					world.resourceLeft().changeMetal(-5);
					creating = true;
					creatingScout = true;
					time = 0;
					System.out.println("Command Centre ordered to create scout");
				}
			}
			if (input.isKeyPressed(Input.KEY_2)) {
				if (world.resourceLeft().getMetal() >= 10) {
					world.resourceLeft().changeMetal(-10);
					creating = true;
					creatingBuilder = true;
					time = 0;
					System.out.println("Command Centre ordered to create builder");
				}
			}
			if (input.isKeyPressed(Input.KEY_3)) {
				if (world.resourceLeft().getMetal() >= 20) {
					world.resourceLeft().changeMetal(-20);
					creating = true;
					creatingEngineer = true;
					time = 0;
					System.out.println("Command Centre ordered to create engineer");
				}
			}
		}
		if (creating == true) {
			int delta = world.getDelta();
			time = time + delta;
			if (time>=FIVE_SEC) {
				if (creatingScout == true) {
					try {
						world.addUnit(new Scout(super.getX(),super.getY()));
					} catch (SlickException e) {
						e.printStackTrace();
					}
					creating = false;
					creatingScout = false;
				}
				if (creatingBuilder == true) {
					try {
						world.addUnit(new Builder(super.getX(),super.getY()));
					} catch (SlickException e) {
						e.printStackTrace();
					}
					creating = false;
					creatingBuilder = false;
				}
				if (creatingEngineer == true) {
					try {
						world.addUnit(new Engineer(super.getX(),super.getY()));
					} catch (SlickException e) {
						e.printStackTrace();
					}
					creating = false;
					creatingEngineer = false;
				}
			}
		}
	}
	
	public void textDisplay(Graphics g) {
		if (super.selectOrNot() == true) {
			g.drawString(("1- Create Scout\n2- Create Builder\n3- Create "
					+ "Engineer\n"), X_OFFSET, Y_OFFSET);
		}
	}
}