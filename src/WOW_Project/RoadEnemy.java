package WOW_Project;

//import java.util.Random;

public class RoadEnemy
{
	//private GameDisplay GUI = PlayGame.GUI;
	
	//private Random rand = new Random();
	private String enemyType;
	private int enemyHP;
	private boolean enemyFlees;
	
	public RoadEnemy() {}
	
	public RoadEnemy(String type)
	{
		//int enemyTypeNumber = rand.nextInt(5);
	//LEVEL 1+
		//Enemy 1: Mage (HP: low, Attack: mid)
		if(type.equals("Mage")) { enemyType = "Mage"; enemyHP = 30; }
		//Enemy 2: Rebel Soldier (HP: high, Attack: low)
		if(type.equals("Soldier")) { enemyType = "Rebel Soldier"; enemyHP = 80; }
		//Enemy 3: Robber (HP: low, Attack: low)
		if(type.equals("Robber")) { enemyType = "Robber"; enemyHP = 15; }
		//Enemy 4: Small Dragon (HP: high, Attack: high)
		if(type.equals("Dragon")) { enemyType = "Small Dragon"; enemyHP = 100; }
		//Enemy 5: Dark Mage (HP: mid, Attack: high)
		if(type.equals("Dark Mage")) { enemyType = "Dark Mage"; enemyHP = 40; }
	//LEVEL 3+
		//Enemy 6: Dark Elf (HP: high, Attack: mid)
		if(type.equals("Dark Elf")) { enemyType = "Dark Elf"; enemyHP = 60; }
	//LEVEL 5+
		//Enemy 7: Elemental (HP: mid, Attack: mid)
		if(type.equals("Elemental")) { enemyType = "Elemental"; enemyHP = 55; }
		//Enemy 8: Giant (HP: high, Attack: low)
		if(type.equals("Giant")) { enemyType = "Giant"; enemyHP = 71; }
		//Enemy 9: Magic Beast (HP: low, Attack: mid)
		if(type.equals("Magic Beast")) { enemyType = "Magic Beast"; enemyHP = 30; }
	//LEVEL 9+
		//Enemy 10: Orc (HP: high, Attack: high)
		if(type.equals("")) { enemyType = ""; enemyHP = 67; }
		//Enemy 11: Evolved Dark Mage (HP: mid, Attack: high)
		if(type.equals("Evolved Dark Mage")) { enemyType = "Evolved Dark Mage"; enemyHP = 52; }
	}
	
	//"Empty" Methods...
	
	public void enemyAttack(Player targetPlayer) { }
	
	//OTHER...
	
	public void setHP(int newHealth) { enemyHP = newHealth; }
	public void takeHP(int points) { enemyHP -= points; }
	public int getHP()
	{
		if(enemyHP < 1) return 0;
		else return enemyHP;
	}
	
	public boolean isDefeated() { return (enemyHP < 1); }
	public void setFlee(boolean yesOrNo) { enemyFlees = yesOrNo; }
	public boolean getFlee() { return enemyFlees; }
	
	public void setEnemyType(String type) { enemyType = type; }
	public String getEnemyType() { return enemyType; }

	public boolean checkBossHP() { return false; }
	public void bossAttack(Player player1) { }
}
