import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList; 

public class Engineer extends Unit{
	private static final String ENGINEER = "assets/units/engineer.png";
	private Image image;
	private static final double SPEED = 0.1;
	private static final double DISTANCE = 32;
	private static final double FIVE_SEC = 5000;
	private static int capacity = 2;
	
	private int time;
	private int hold;
	private String type;
	private boolean mining = false;
	private Resource lastMinePlace;
	private double lastMiningX;
	private double lastMiningY;
	private double closestCCX;
	private double closestCCY;
	private double closestDistance;
	private double dist;
	
	public Engineer(double x, double y) throws SlickException {
		super(x, y);
		this.image = new Image(ENGINEER);
	}
	
	public void update(World world) {
		super.update(world);
		if (super.inAction() == false && hold == 0  && mining == false) {
			time = 0;
			ArrayList<Resource> resources = world.allResources();
			//check resource that is close enough when it is not moving
			for (Resource r: resources) {
				if (World.distance(r.getX(), r.getY(), this.getX(), this.getY()) <= DISTANCE) {
					mining = true;
					lastMinePlace = r;
					if (r instanceof Metal) {
						type = "metal";
					} else {
						type = "unObtainium";
					}
				}
			}
		}
		//if it is mining
		if (mining == true) {
			int delta = world.getDelta();
			time = time + delta;
			if (time>=FIVE_SEC) {
				if (lastMinePlace.getAmount() >= capacity) {
					hold = capacity;
				} else {
					hold = lastMinePlace.getAmount();
				}
				lastMinePlace.mine(hold);
				closestDistance = -1;
				ArrayList<Building> buildings = world.allBuildings();
				for (Building b: buildings) {
					if (b instanceof CommandCenter) {
						dist = World.distance(super.getX(), super.getY(), b.getX(), b.getY());
						if (closestDistance == -1 || dist <closestDistance) {
							closestDistance = dist;
							closestCCX = b.getX();
							closestCCY = b.getY();
						}
					}
				}
				this.setTargetX(closestCCX);
				this.setTargetY(closestCCY);
				lastMiningX = super.getX();
				lastMiningY = super.getY();
				mining = false;
			}
		}
		if (hold >0 && mining == false) {
			ArrayList<Building> buildings = world.allBuildings();
			for (Building b: buildings) {
				if (b instanceof CommandCenter) {
					if (World.distance(super.getX(), super.getY(), b.getX(), b.getY()) <= DISTANCE) {
						if (type.equals("metal")) {
							world.resourceLeft().changeMetal(hold);
						} else {
							world.resourceLeft().changeUnobtanium(hold);
						}
						hold = 0;
						this.setTargetX(lastMiningX);
						this.setTargetY(lastMiningY);
					}
				}
			}
		}
	}
	
	public void render(Camera camera) {
		super.render(camera);
		image.drawCentered((int)camera.globalXToScreenX(super.getX()),
						   (int)camera.globalYToScreenY(super.getY()));
	}
	public double getSpeed() {
		return SPEED;
	}
	public static void addCapacity() {
		capacity = capacity + 1;
	}
}

