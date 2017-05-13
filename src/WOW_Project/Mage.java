package WOW_Project;

import java.util.Random;

public class Mage extends Player
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private int kills;
	private int heals;
	
	private Random rand = new Random();
	
	//CREATE NEW MAGE
	public Mage(String mageName)
	{
		super(mageName);
		setHealth(100);
		setHPThreshhold(100);
		kills = 0;
		heals = 10;
	}
	
	//ATTACKS:
	
	public void fireball(RoadEnemy target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int hpTaken = rand.nextInt(14) + 25;
			target.takeHP(hpTaken);
			GUI.println("You shot a fireball at the " + target.getEnemyType() + " and damaged it " + hpTaken + " health points.");
		}
		else
		{
			GUI.println("You missed. Nothing happened.");
		}
	}
	public void beat(RoadEnemy target)
	{
		int attackChance = rand.nextInt(5);
		if(attackChance == 0 || attackChance == 2 || attackChance == 3)
		{
			int hpTaken = rand.nextInt(8) + 10;
			target.takeHP(hpTaken);
			GUI.println("You hit the " + target.getEnemyType() + " with a spell and damaged it 20 health points.");
		}
		else if(attackChance == 1 || attackChance == 4)
		{
			GUI.println("You attempted to harm your enemy, but failed. Nothing happened.");
		}
	}
	public void strike(RoadEnemy target)
	{
		target.takeHP(10);
		GUI.println("You struck your enemy and damaged it 10 health points.");
	}
	
	//SPEACIAL – HEAL:
	
	public void heal()
	{
		int healChance = rand.nextInt(6);
		if(getHP() <= (getHPThreshhold() - 10))
		{
			if(heals > 1 && (healChance == 0 || healChance == 2 || healChance == 3 || healChance == 4 || healChance == 5))
			{
				this.addHealth(10);
				heals++;
				GUI.println("Your attempt to heal yourself was successful. You gained 10 health points.");
			}
			else if(heals > 1 && healChance == 1)
			{
				heals++;
				GUI.println("Your attempt to heal yourself failed. You gained no health points.");
			}
			else if(heals == 1)
			{
				this.addHealth(10);
				heals--;
				GUI.println("Your attempt to heal yourself was successful, and you gained 10 health points.");
				GUI.println("You have no more heal attempts left.");
			}
			else if(heals <= 0)
			{
				GUI.println("You have already healed yourself the maximum number of times (10 attempts). You may not heal again.");
			}
		}
		else GUI.println("You are not in need of healing. You may only heal yourself when you are 10 or more health points below your starting health.");
	}
	
	//MOVEMENT:
	
	public void teleport()
	{
		//MOVING...
		int xSpaces = rand.nextInt(21) - 10;
		int ySpaces = rand.nextInt(21) - 10;
		//add catch for no movement?
		changeUpX(xSpaces);
		changeUpY(ySpaces);
		
		GUI.println("You have teleported! You are now at (" + this.getX() + ", " + this.getY() + ")");

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
		GUI.println("playerHeals= " + getMageHeals());
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
		statListing += "playerHeals= " + getMageHeals() + "\n";
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
		GUI.println("Mage Attack Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[F] Fireball: Launch a fireball at your enemy. (high damage, low chance of success)");
		GUI.println("[C] Cast Spell: Launch a harmful spell at your enemy. (mid damage, medium chance of success)");
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
		GUI.println("[H] Heal: Attempt to regain 10 health points. (May only be used if you are in need of healing)");
		GUI.println("[T] Teleport: Teleport a great distance in a random direction. ");
		GUI.println("-----------------------------------------------------");
	}
	
	//OTHER:
	
	public boolean checkForDeath() { return (getHP() < 1); }
	
	public void levelUp()
	{
		addLevel();
		setHealth(getHPThreshhold() + 10);
		setHPThreshhold(getHP());
	}
	
	public String getPlayerType() { return "Mage"; }
	
	public int getKills() { return kills; }
	public void setKills(int set) { kills = set; }
	public void addKills(int add) { kills += add; }
	
	public int getMageHeals() { return heals; }
	public void shopUpgrade() { heals = heals + 2; addHealth(15); }
}
