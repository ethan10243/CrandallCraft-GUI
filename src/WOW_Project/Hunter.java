package WOW_Project;

import java.util.Random;

public class Hunter extends Player
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private int kills;
	private Random rand = new Random();
	
	public Hunter(String hunterName)
	{
		super(hunterName);
		setHealth(120);
		setHPThreshhold(120);
		kills = 0;
	}

	//ATTACKS:
	
	public void shoot(RoadEnemy target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0 || attackChance == 2)
		{
			int damagePoints = rand.nextInt(15) + 25;
			target.takeHP(damagePoints);
			GUI.println("You shot the " + target.getEnemyType() + " and damaged it " + damagePoints + " health points.");
		}
		else if(attackChance == 1)
		{
			GUI.println("You missed. Nothing happened.");
		}
	}
	public void strike(RoadEnemy target)
	{
		int dChance = rand.nextInt(10);
		int damagePoints = rand.nextInt(7) + 8;
		if(dChance == 0 || dChance == 1 || dChance == 2 || dChance == 3 || dChance == 4 || dChance == 5 || dChance == 6 || dChance == 7 || dChance == 8)
		{
			target.takeHP(damagePoints);
			GUI.println("You attacked the " + target.getEnemyType() + " and damaged it " + damagePoints + " health points.");
		}
		else if(dChance == 9)
		{
			target.takeHP(damagePoints);
			GUI.println("Your attempt to strike the " + target.getEnemyType() + " failed. Nothing happened.");
		}
	}
	public void poison(RoadEnemy target)
	{
		int dChance = rand.nextInt(10);
		int damagePoints = rand.nextInt(7) + 8;
		if(dChance == 0 || dChance == 1 || dChance == 2 || dChance == 3 || dChance == 4 || dChance == 5 || dChance == 6 || dChance == 7 || dChance == 8)
		{
			target.takeHP(damagePoints);
			GUI.println("You attacked the " + target.getEnemyType() + " and damaged it " + damagePoints + " health points.");
		}
		else if(dChance == 9)
		{
			target.takeHP(damagePoints);
			GUI.println("Your attempt to strike the " + target.getEnemyType() + " failed. Nothing happened.");
		}
	}
	
	//STATS:
	
	public void getStats()
	{	
		GUI.println("Current Player Stats:");
		GUI.println("------------------------------");
		GUI.println("playerName= " + getName());
		GUI.println("playerLevel= " + getLevel());
		GUI.print("playerHealth= " + getHP());
		if(getHP() < 1) GUI.print(" [DEAD]");
		if(getHP() == getHPThreshhold()) GUI.print(" [MAX HEALTH]");
		GUI.println();
		GUI.println("playerKills= " + kills);
		GUI.println("playerGold= " + getGold());
		getLoc();
		printRealms();
		GUI.println("------------------------------");
	}
	
	public String getStatsString()
	{
		String statListing = "Current Player Stats:" + "\n";
		statListing += "------------------------------" + "\n";
		statListing += "playerName= " + getName() + "\n";
		statListing += "playerLevel= " + getLevel() + "\n";
		statListing += "playerHealth= " + getHP();
		if(getHP() < 1) statListing += " [DEAD]" + "\n";
		else if(getHP() == getHPThreshhold()) statListing += " [MAX HEALTH]" + "\n";
		else statListing += "\n";
		statListing += "playerKills= " + kills + "\n";
		statListing += "playerGold= " + getGold() + "\n";
		statListing += getLoc() + "\n";
		statListing += printRealms() + "\n";
		statListing += "------------------------------";
		
		return statListing;
	}
	
	public void getBattleStats()
	{	
		GUI.println("Current Player Battle Stats:");
		GUI.println("------------------------------");
		GUI.println("playerName= " + getName());
		GUI.println("playerHealth= " + getBattleHP());
		GUI.println("playerPosition= BossArena (Indeterminate, Indeterminate)");
		GUI.println("------------------------------");
	}
	
	public String getBattleStatsString()
	{
		String stat = "Current Player Battle Stats:" + "\n";
		stat += "------------------------------";
		stat += "playerName= " + getName() + "\n";
		stat += "playerHealth= " + getBattleHP() + "\n";
		stat += "playerPosition= BossArena (Indeterminate, Indeterminate)" + "\n";
		stat += "------------------------------" + "\n";
		
		return stat;
	}
	
	public void attackInfo()
	{
		GUI.println("King Attack Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[A] Launch Arrow Volley: Launch a projectile at your enemy with a catapult. (mid/high damage, medium chance of success)");
		GUI.println("[S] Strike: Strike your enemy directly. (low/mid damage, high chance of success)");
		GUI.println("[X] Flee: Run away from battle. (low chance of being damaged while fleeing)");
		GUI.println("-----------------------------------------------------");
	}
	
	public void actionInfo()
	{
		GUI.println("Player Action Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[stats] Stats: View current player stats. ");
		GUI.println("[M] Move: Move one space in specified direction. ");
		GUI.println("[H] Hunt: Hunt over a great distance in a random direction. ");
		GUI.println("-----------------------------------------------------");
	}
	
	//MOVEMENT:
	
	public void hunt()
	{
		//MOVING...
		int xSpaces = rand.nextInt(40) - 20;
		int ySpaces = rand.nextInt(30) - 15;
		//add catch for no movement?
		changeUpX(xSpaces);
		changeUpY(ySpaces);
		
		GUI.println("You have hunted over a great distance! You are now at (" + this.getX() + ", " + this.getY() + ")");

	}
	
	//OTHER:
	
	public boolean checkForDeath() { return (getHP() < 1); }
	
	public String getPlayerType() { return "Hunter"; }
	
	public int getKills() { return kills; }
	public void setKills(int set) { kills = set; }
	public void addKills(int add) { kills += add; }
}
