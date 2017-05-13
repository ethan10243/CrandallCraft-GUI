package WOW_Project;

import java.util.Random;

public class King extends Player
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private int kills;
	
	private Random rand = new Random();
	
	public King(String kingName)
	{
		super(kingName);
		setHealth(180);
		setHPThreshhold(180);
		kills = 0;
	}

	//ATTACKS:
	
	public void shoot(RoadEnemy target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0 || attackChance == 2)
		{
			int d = rand.nextInt(10) + 15;
			target.takeHP(d);
			GUI.println("You shot the " + target.getEnemyType() + " and damaged it " + d + " health points.");
		}
		else if(attackChance == 1)
		{
			GUI.println("You missed. Nothing happened.");
		}
	}
	public void strike(RoadEnemy target)
	{
		target.takeHP(8);
		GUI.println("You attacked the " + target.getEnemyType() + " and damaged it 8 health points.");
	}
	public void plunder(RoadEnemy target)
	{
		int plunderChance = rand.nextInt(5);
		if(plunderChance == 0 || plunderChance == 2 || plunderChance == 3)
		{
			GUI.println("Your attempt to plunder was successful.");
			GameManager.giveReward(2);
		}
		else if(plunderChance == 1)
		{
			GUI.println("Your attempt failed. You gained no gold.");
		}
		else if(plunderChance == 4)
		{
			int points = rand.nextInt(5) + 5;
			target.takeHP(points);
			GUI.println("Your attempt to plunder was unsuccessful, but you managed to hit your enemy. Your enemy lost " + points + " health points.");
		}
	}
	
	//MOVEMENT:
	
	public void ride()
	{
		//MOVING...
		int xSpaces = rand.nextInt(50) - 25;
		int ySpaces = rand.nextInt(50) - 25;
		//Add catch for no movement?
		changeUpX(xSpaces);
		changeUpY(ySpaces);
		
		GUI.println("You have riden a great distance! You are now at (" + this.getX() + ", " + this.getY() + ")");

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
		stat += "------------------------------" + "\n";
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
		GUI.println("[L] Launch Catapult: Launch a projectile at your enemy with a catapult. (mid/high damage, medium chance of success)");
		GUI.println("[P] Plunder: Go in to rob and damage your enemy. (medium chance of gaining gold only, low chance of low damage only, low chance of no gain or damage)");
		GUI.println("[S] Strike: Strike your enemy directly. (low damage, 100% chance of success)");
		GUI.println("[X] Flee: Run away from battle. (low chance of being damaged while fleeing)");
		GUI.println("-----------------------------------------------------");
	}
	
	public void actionInfo()
	{
		GUI.println("Player Action Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[stats] Stats: View current player stats. ");
		GUI.println("[M] Move: Move one space in specified direction. ");
		GUI.println("[R] Ride: Ride a great distance in a random direction, accompanied by your legion. ");
		GUI.println("-----------------------------------------------------");
	}
	
	//OTHER:
	
	public boolean checkForDeath() { return (getHP() < 1); }
	
	public String getPlayerType() { return "King"; }
	
	public int getsKills() { return kills; }
	public void setKills(int set) { kills = set; }
	public void addKills(int add) { kills += add; }
}
