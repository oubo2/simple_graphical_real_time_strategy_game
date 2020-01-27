import org.newdawn.slick.Input;

/**
 * This class should be used to restrict the game's view to a subset of the entire world.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */
public class Camera {
	private static final double CAMERA_SPEED = 0.4;
	//random starting points, not important;
	private double x = 300;
	private double y = 300;
	private double targetX = 512;
	private double targetY = 384;
	private Unit targetU;
	private Building targetB;
	
	public void followSprite(Unit target) {
		this.targetU = target;
		x = targetU.getX() - App.WINDOW_WIDTH/2;
		y = targetU.getY() - App.WINDOW_WIDTH/2;
	}
	
	public void followSprite(Building target) {
		this.targetB = target;
		x = targetB.getX() - App.WINDOW_WIDTH/2;
		y = targetB.getY() - App.WINDOW_WIDTH/2;
	}
	public double globalXToScreenX(double x) {
		return x - this.x;
	}
	public double globalYToScreenY(double y) {
		return y - this.y;
	}

	public double screenXToGlobalX(double x) {
		return x + this.x;
	}
	public double screenYToGlobalY(double y) {
		return y + this.y;
	}
	
	public void update(World world) {
		Input input = world.getInput();
		if (input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_S) || 
				input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_D)) {
			if (input.isKeyDown(Input.KEY_W)) {
				targetY -=  world.getDelta() * CAMERA_SPEED;
			}
			if (input.isKeyDown(Input.KEY_S)) {
				targetY +=  world.getDelta() * CAMERA_SPEED;
			}
			if (input.isKeyDown(Input.KEY_A)) {
				targetX -=  world.getDelta() * CAMERA_SPEED;
			}
			if (input.isKeyDown(Input.KEY_D)) {
				targetX +=  world.getDelta() * CAMERA_SPEED;
			}
			if (world.unitSelectOrNot()) {
				world.deselectUnit();
			}
			if (world.buildingSelectOrNot()) {
				world.deselectBuilding();
			}
		}
		if (world.buildingSelectOrNot()) {
			targetX = targetB.getX() - App.WINDOW_WIDTH / 2;
			targetY = targetB.getY() - App.WINDOW_HEIGHT / 2;
		} else if (world.unitSelectOrNot()) {
			targetX = targetU.getX() - App.WINDOW_WIDTH / 2;
			targetY = targetU.getY() - App.WINDOW_HEIGHT / 2;
		} else {
		
		//targetX = x - App.WINDOW_WIDTH / 2;
		//targetY = y - App.WINDOW_HEIGHT / 2;
		}

		x = Math.min(targetX, world.getMapWidth() -	 App.WINDOW_WIDTH);
		x = Math.max(x, 0);
		y = Math.min(targetY, world.getMapHeight() - App.WINDOW_HEIGHT);
		y = Math.max(y, 0);
	}
}
