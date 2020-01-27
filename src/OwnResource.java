
public class OwnResource {
	private int metal;
	private int unobtainium;
	
	public OwnResource(int metal, int unobtainium) {
		this.metal = metal;
		this.unobtainium = unobtainium;
	}
	public void changeMetal(int x) {
		metal = metal + x;
	}
	public void changeUnobtanium(int x) {
		unobtainium = unobtainium + x;
	}
	public int getMetal() {
		return metal;
	}
	public int getUnobtainium() {
		return unobtainium;
	}
}
