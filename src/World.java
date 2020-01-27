import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList; 

/**some parts in world, camera and unit are based on project 1 sample solution
 * This class should be used to contain all the different objects in your game world, and schedule their interactions.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */

public class World {
	private static final String MAP_PATH = "assets/main.tmx";
	private static final String SOLID_PROPERTY = "solid";
	private static final String OCCUPIED_PROPERTY = "occupied";
	private static final int TEXT_OFFSET = 32;
	
	
	//private Sprite sprite;
	private boolean unitIsSelected = false;
	private boolean buildingIsSelected = false;
	private Unit selectedUnit;
	private Building selectedBuilding;
	private double selectX;
	private double selectY;
	private OwnResource own;
	private static final double DISTANCE = 32;
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	private ArrayList<Unit> units = new ArrayList<Unit>();
	
	private TiledMap map;
	private Camera camera = new Camera();
	
	private Input lastInput;
	private int lastDelta;

	public Input getInput() {
		return lastInput;
	}
	public int getDelta() {
		return lastDelta;
	}
	
	public OwnResource resourceLeft() {
		return own;
	}
	
	public ArrayList<Unit> allUnits() {
		return units;
	}
	
	public ArrayList<Resource> allResources() {
		return resources;
	}
	
	public ArrayList<Building> allBuildings() {
		return buildings;
	}
	
	public void addUnit(Unit u) {
		units.add(u);
	}
	
	public void addBuilding(Building b) {
		buildings.add(b);
	}
	
	public boolean isPositionFree(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, SOLID_PROPERTY, "false"));
	}
	
	public boolean isPositionOccupied(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, OCCUPIED_PROPERTY, "false"));
	}
	
	public double getMapWidth() {
		return map.getWidth() * map.getTileWidth();
	}
	
	public double getMapHeight() {
		return map.getHeight() * map.getTileHeight();
	}
	
	public World() throws SlickException {
		map = new TiledMap(MAP_PATH);
		own = new OwnResource(1000,0);
		CSVReader();
	}
	
	public void update(Input input, int delta) throws SlickException {
		lastInput = input;
		lastDelta = delta;
		if (lastInput.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			selectX = camera.screenXToGlobalX(input.getMouseX());
			selectY = camera.screenYToGlobalY(input.getMouseY());
			if (unitIsSelected == false && buildingIsSelected == false) {
				for (Unit u:units) {
					if (distance(selectX, selectY, u.getX(), u.getY()) <= DISTANCE) {
						u.follow(camera);
						unitIsSelected = true;
						selectedUnit = u;
						break;
					}
				}
				if (unitIsSelected == false) {
					for (Building b:buildings) {
						if (distance(selectX, selectY, b.getX(), b.getY()) <= DISTANCE) {
							b.follow(camera);
							buildingIsSelected = true;
							selectedBuilding = b;
							break;
						}
					}
				}
			}
			if (unitIsSelected == true) {
				if (distance(selectX, selectY, selectedUnit.getX(), selectedUnit.getY()) > DISTANCE) {
					unitIsSelected = false;
					selectedUnit.deselect();
				}
			}
			if (buildingIsSelected == true) {
				if (distance(selectX, selectY, selectedBuilding.getX(), selectedBuilding.getY()) > DISTANCE) {
					buildingIsSelected = false;
					selectedBuilding.deselect();
				}
			}
		}
		for (int i = 0; i < units.size(); ++i) {
			Unit u;
		    u = units.get(i);
		    u.update(this);
		    if (u instanceof Truck) {
				if (((Truck) u).selfdestroy() == true) {
					{units.remove(i); --i; }
				}
		    }
		}
		for (Building b: buildings) {
			b.update(this);
		}
		for (int i = 0; i < resources.size(); ++i) {
			Resource r;
		    r = resources.get(i);
		    if (r.getAmount() == 0) {
				{resources.remove(i); --i; }
			}
		}
		camera.update(this);
	}
	
	public void render(Graphics g) {
		map.render((int)camera.globalXToScreenX(0),
				   (int)camera.globalYToScreenY(0));
		g.drawString("Metal: " + own.getMetal() + "\nUnobtanium: " + own.getUnobtainium(), TEXT_OFFSET, TEXT_OFFSET);
		for (Resource r:resources) {
	         r.render(camera);
		}
		for (Building b:buildings) {
	         b.render(camera);
	         b.textDisplay(g);
		}
		for (Unit u:units) {
	         u.render(camera);
	         u.textDisplay(g);
		}
	}
	
	public void CSVReader() {
		try (BufferedReader br = new BufferedReader(new FileReader("assets/objects.csv"))) {
			String input;
			String type;
			int x;
			int y;
			while ((input = br.readLine()) != null) {
				String[] part = input.split(",");
				type = part[0];
				x = Integer.parseInt(part[1]);
				y = Integer.parseInt(part[2]);
				if (type.equals("metal_mine")) {
					resources.add(new Metal(x,y));
				} else if (type.equals("unobtainium_mine")) {
					resources.add(new Unobtainium(x,y));
				} else if (type.equals("command_centre")) {
					buildings.add(new CommandCenter(x,y));
				} else if (type.equals("pylon")) {
					buildings.add(new Pylon(x,y));
				} else if (type.equals("factory")) {
					buildings.add(new Factory(x,y));
				} else if (type.equals("engineer")) {
					units.add(new Engineer(x,y));
				} else if (type.equals("builder")) {
					units.add(new Builder(x,y));
				} else if (type.equals("scout")) {
					units.add(new Scout(x,y));
				} else if (type.equals("truck")) {
					units.add(new Truck(x,y));
				} else {
					System.out.println("do not belong here");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean unitSelectOrNot() {
		return unitIsSelected;
	}
	
	public boolean buildingSelectOrNot() {
		return buildingIsSelected;
	}
	
	public void deselectUnit() {
		unitIsSelected = false;
		selectedUnit.deselect();
	}
	
	public void deselectBuilding() {
		buildingIsSelected = false;
		selectedBuilding.deselect();
	}
	
	// This should probably be in a separate static utilities class, but it's a bit excessive for one method.
	public static double distance(double x1, double y1, double x2, double y2) {
		return (double)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	private int worldXToTileX(double x) {
		return (int)(x / map.getTileWidth());
	}
	private int worldYToTileY(double y) {
		return (int)(y / map.getTileHeight());
	}
}
