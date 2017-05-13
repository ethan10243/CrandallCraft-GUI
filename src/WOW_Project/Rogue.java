package WOW_Project;

import java.util.Random;

public class Rogue extends Player
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private int kills;
	
	private Random rand = new Random();
	
	public Rogue(String rogueName)
	{
		super(rogueName);
		setHealth(160);
		setHPThreshhold(160);
		kills = 0;
	}

	//ATTACKS:
	
	public void beat(RoadEnemy target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0 || attackChance == 2)
		{
			target.takeHP(15);
			GUI.println("You shot the " + target.getEnemyType() + " and damaged it 15 health points.");
		}
		else if(attackChance == 1)
		{
			GUI.println("You missed. Nothing happened.");
		}
	}
	public void strike(RoadEnemy target)
	{
		target.takeHP(12);
		GUI.println("You attacked the " + target.getEnemyType() + " and damaged it 8 health points.");
	}
	public void rob(RoadEnemy target)
	{
		int healChance = rand.nextInt(4);
		if(healChance == 0 || healChance == 2 || healChance == 3)
		{
			GUI.println("Your attempt to rob your enemy was successful");
			GameManager.giveReward(2);
		}
		else if(healChance == 1)
		{
			GUI.println("Your attempt failed. You gained no coins.");
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
		stat += "------------------------------" + "\n";
		stat += "playerName= " + getName() + "\n";
		stat += "playerHealth= " + getBattleHP() + "\n";
		stat += "playerPosition= BossArena (Indeterminate, Indeterminate)" + "\n";
		stat += "------------------------------" + "\n";
		
		return stat;
	}
	
	public void attackInfo()
	{
		GUI.println("Rogue Attack Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[B] Beat: Beat your enemy with a weapon. (mid/high damage, medium chance of success)");
		GUI.println("[R] Rob: Rob your enemy, without dealing damage. (high chance of success)");
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
		GUI.println("[S] Scout: Move a great distance in a random direction. ");
		GUI.println("-----------------------------------------------------");
	}
	
	//MOVEMENT:
		
	public void scout()
	{
		//MOVING...
		int xSpaces = rand.nextInt(26) - 13;
		int ySpaces = rand.nextInt(26) - 13;
		//add catch for no movement?
		changeUpX(xSpaces);
		changeUpY(ySpaces);
		
		GUI.println("You have scouted over a great distance! You are now at (" + this.getX() + ", " + this.getY() + ")");
		
	}
	
	//OTHER:
	
	public boolean checkForDeath() { return (getHP() < 1); }
	
	public String getPlayerType() { return "Rogue"; }
	
	public int getKills() { return kills; }
	public void setKills(int set) { kills = set; }
	public void addKills(int add) { kills += add; }
}
