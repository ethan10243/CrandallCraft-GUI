package WOW_Project;

import java.util.ArrayList;
import java.util.Random;

public class Player
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	private int playerBattleHP;
	
	private boolean hasSpecialAttack;
	
	private int playerHP;
	private int playerHPThreshhold;
	private int playerGold;
	private String playerName;
	private int xPos;
	private int yPos;
	
	private int playerLevel;
	private String playerRealm;
	private ArrayList<String> discoveredRealms = new ArrayList<String>();
	
	//CREATE PLAYER
	public Player(String username)
	{
		xPos = 0;
		yPos = 0;
		playerName = username;
		playerHP = 1;
		playerLevel = 1;
		playerGold = 10;
		hasSpecialAttack = false;
		playerRealm = "Homeland";
		discoveredRealms.add("Homeland");
	}
	public void setNewName(String newPlayerName) { playerName = newPlayerName; }
	
	public boolean hasSpecialAttack() { return hasSpecialAttack; }
	public void setSpecialAttack(boolean newStatus) { hasSpecialAttack = newStatus; }
	
	//MOVEMENT
	public void setX(int xCo) { xPos = xCo; }
	public void setY(int yCo) { yPos = yCo; }
	public void changeUpX(int spaces) { xPos += spaces; }
	public void changeUpY(int spaces) { yPos += spaces; }
	public void changeDownX(int spaces) { xPos -= spaces; }
	public void changeDownY(int spaces) { yPos -= spaces; }
	
	public void checkPlayerRealm()
	{
		String oldRealm = playerRealm;
		updateRealm();
		String newRealm = playerRealm;
		//int realmsDiscovered = discoveredRealms.size();
		int t = 0;
		if(!oldRealm.equals(newRealm))
		{
			for(String e : discoveredRealms)
			{
				if(newRealm.equals(e)) { t++; }
			}
			if(t > 0) { GUI.println("You have re-entered the " + playerRealm); GUI.println("\n"); }
			else
			{
				if(playerRealm.equals("Dark Forest")) { GUI.println("\n" + "You have entered the Dark Forest! This is a great feat, but you gain no reward for entering the forbidden realm. \n"); }
				else if(playerRealm.equals("Secret Evolved Light Mages' Village"))
				{
					GUI.println("\n" + "You have entered a new realm! Welcome to the " + getCurrentRealm() + ", the mightiest of all the realms.");
					GameManager.giveReward(2);
					GUI.println("");
				}
				else
				{
					GUI.println("\n" + "You have entered a new realm! Welcome to the " + getCurrentRealm());
					GameManager.giveReward(1);
					GUI.println("");
				}
				addRealm(playerRealm);
			}
			if(playerRealm.equals("Dark Forest")) { GUI.println("\n" + "There are no shops or allies in the Dark Forest. This is a terribly dark and dangerous realm of beasts and monsters." + "\n"); GUI.setIcon2(GUI.Picture_DarkForest); }
			else
			{
				GUI.setIcon2(GUI.Picture_Shop);
				PlayerShop realmShop;
				if(this.getPlayerType().equals("Mage")) realmShop = new MageShop();
				else if(this.getPlayerType().equals("Hunter")) realmShop = new HunterShop();
				else if(this.getPlayerType().equals("Rogue")) realmShop = new RogueShop();
				else if(this.getPlayerType().equals("King")) realmShop = new KingShop();
				else realmShop = new PlayerShop();
				realmShop.enterShop(this);
			}
		}
	}
	public String getCurrentRealm() { return playerRealm; }
	public void updateRealm()
	{	
		if((xPos <= 5 && xPos >= -5) && (yPos <= 5 && yPos >= -5))
		{
			playerRealm = "Homeland";
		}
		else if((xPos < 251 && xPos > -251) && (yPos < 251 && yPos > -251))
		{
			//Quad 3
			if((xPos < 0 && xPos > -250) && (yPos < 0 && yPos > -250))
			{
				if(xPos <= -200) { playerRealm = "Blackrock Mining Town"; }
				else { playerRealm = "Skywall Nation"; }
			}
			//Quad 4
			else if((xPos > 0 && xPos < 250) && (yPos < 0 && yPos > -250))
			{
				if((xPos > 30 && xPos < 200) && (yPos < -100 && yPos > -170)) { playerRealm = "Lightbringer Tribe Territory"; }
				else { playerRealm = "Ice Mountain Realm"; }
			}
			//Quad 2
			else if((xPos < 0) && (yPos > 0 && yPos < 250))
			{
				if((xPos > -150 && xPos < -100) && (yPos > 75 && yPos < 200)) { playerRealm = "Echo Isles Realm"; }
				else { playerRealm = "Abandoned Beast Territory"; }
			}
			//Quad 1
			else if((xPos > 0 && xPos < 250) && (yPos > 0 && yPos < 250))
			{
				if((xPos > 200 && xPos < 210) && (yPos > 35 && yPos < 45)) { playerRealm = "Secret Evolved Light Mages' Village"; }
				else { playerRealm = "Sacred Temple Realm"; }
			}
		}
		else { playerRealm = "Dark Forest"; }
	}
	
	public void addRealm(String newRealm) { discoveredRealms.add(newRealm); }
	public String printRealms() { String realms = "Realms Discovered: "; realms += discoveredRealms.toString(); return realms; }
	
	//HP MANIPULATION
	public void setHealth(int newHealth) { playerHP = newHealth; }
	public void addHealth(int points) { playerHP += points; if(playerHP > playerHPThreshhold) playerHP = playerHPThreshhold; }
	public void takeHealth(int points) { playerHP -= points; }
	
	public int getHPThreshhold() { return playerHPThreshhold; }
	public void setHPThreshhold(int newValue) { playerHPThreshhold = newValue; }
	
	//GET INFO
	public int getHP() { return playerHP; }
	public int getX() { return xPos; }
	public int getY() { return yPos; }
	public String getLoc()
	{
		String realmList = "playerRealm= " + getCurrentRealm() + "\n";
		realmList += "playerPosition= " + " (" + getX() + ", " + getY() + ")";
		
		return realmList;
	}
	public String getName() { return playerName; }
	public String getPlayerType() { return "PLAYER"; }
	public int getKills() { return -1; }
	//public int getSteals() { return -1; }
	
	//Gold commands
	public int getGold() { if(playerGold < 1) playerGold = 0; return playerGold; }
	public void addGold(int coins) { playerGold += coins; if(playerGold < 1) playerGold = 0; }
	public void takeGold(int coins) { playerGold -= coins; if(playerGold < 1) playerGold = 0; }
	public void setGold(int coins) { playerGold = coins; if(playerGold < 1) playerGold = 0; }
	
	//Player Level Commands
	public int getLevel() { return playerLevel; }
	public void setLevel(int newLevel) { playerLevel = newLevel; }
	public void addLevel() { playerLevel++; }
	public void levelUp()
	{
		GUI.setIcon2(GUI.Picture_LevelUp);
		playerLevel++;
		playerHP = (playerHPThreshhold + 10);
		playerHPThreshhold += 10;
	}
	
	public void getStats()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		GUI.println("Current Player Stats:");
		GUI.println("-----------------------------------------------------");
		GUI.println("|  GAME PlayerType ERROR: PlayerType not specified. Stats not available.  |");
		GUI.println("-----------------------------------------------------");
	}
	
	public String getStatsString()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		String statListing = "Current Player Stats: " + "\n";
		statListing += "-----------------------------------------------------" + "\n";
		statListing += "|  GAME PlayerType ERROR: PlayerType not specified. Stats not available.  |" + "\n";
		statListing += "-----------------------------------------------------" + "\n";
		
		return statListing;
	}
	
	public void getBattleStats()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		GUI.println("Current Player Stats:");
		GUI.println("-----------------------------------------------------");
		GUI.println("|  GAME PlayerType ERROR: PlayerType not specified. Battle Stats not available.  |");
		GUI.println("-----------------------------------------------------");
	}
	
	public String getBattleStatsString()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		String stat = "Current Player Stats:" + "\n";
		stat += ("-----------------------------------------------------" + "\n");
		stat += ("|  GAME PlayerType ERROR: PlayerType not specified. Battle Stats not available.  |" + "\n");
		stat += ("-----------------------------------------------------" + "\n");
		
		return stat;
	}
	
	public void attackInfo()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		GUI.println("Player Attack Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("|  GAME PlayerType ERROR: PlayerType not specified. Info not available.  |");
		GUI.println("-----------------------------------------------------");
	}
	
	public void actionInfo()
	{
		GUI.setIcon2(GUI.Picture_GamePlayError);
		GUI.println("Player Action Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("|  GAME PlayerType ERROR: PlayerType not specified. Info not available.  |");
		GUI.println("-----------------------------------------------------");
	}
	
	//MOVEMENT:
	
	public void move(String direction)
	{
		if(direction.equalsIgnoreCase("U")) { changeUpY(1); GUI.println("You moved upward"); GUI.setIcon2(GUI.Picture_BLANK); }
		else if(direction.equalsIgnoreCase("D")) { changeDownY(1); GUI.println("You moved downward"); GUI.setIcon2(GUI.Picture_BLANK); }
		else if(direction.equalsIgnoreCase("L")) { changeDownX(1); GUI.println("You moved to the left"); GUI.setIcon2(GUI.Picture_BLANK); }
		else if(direction.equalsIgnoreCase("R")) { changeUpX(1); GUI.println("You moved to the right"); GUI.setIcon2(GUI.Picture_BLANK); }
		else
		{
			GUI.setIcon2(GUI.Picture_InputError);
			GUI.println("INPUT ERROR: Invalid direction input. Please try again.");
		} 
	}
	
	public void playerFlee()
	{
		int fleeChance = rand.nextInt(5);
		if(fleeChance == 0)
		{
			int hpLost = rand.nextInt(5) + 10;
			takeHealth(hpLost);
			GUI.println("You fled, but your enemy managed to hit you. you lost " + hpLost + " health points.");
		}
		else
		{
			GUI.println("You fled battle successfully.");
		}
	}
	
	public void shoot(RoadEnemy target) { }
	public void strike(RoadEnemy target) { }
	public void plunder(RoadEnemy target) { }
	public void fireball(RoadEnemy target) { }
	public void rob(RoadEnemy target) { }
	public void beat(RoadEnemy target) { }
	public int getMageHeals() { return -1; }
	public void teleport() { }
	public void ride() { }
	public void heal() { }
	public void addKills(int add) { }
	public void hunt() { }
	public void scout() { }
	
	public void special(RoadEnemy target)
	{
		if(hasSpecialAttack())
		{
			int damage;
			if(target.getEnemyType().equals("Powerful Warlock") || target.getEnemyType().equals("Dragon Boss")) damage = 65;
			else  damage = target.getHP();
			target.takeHP(damage);
			GUI.println("You used your special attack on your enemy and damaged it " + damage + " health points.");
			hasSpecialAttack = false;
		}
		else GUI.println("\n" + "PLAYER ERROR: No special attack available." + "\n");
	}
	
	public boolean isDead() { return (playerHP < 1); }
	public void createBattleHP() { playerBattleHP = playerHP; }
	public boolean checkBattleHP() { return (playerBattleHP < 1); }
	public int getBattleHP() { if(playerBattleHP < 1) return 0; else return playerBattleHP; }
	public void takeBattleHP(int points) { playerBattleHP -= points; if(playerBattleHP < 1) playerBattleHP = 0; }
	
	//Special Warlock Boss effect...
	private boolean poisoned;
	public void warlockSetP(boolean tOrF) { poisoned = tOrF; }
	public boolean warlockGetP() { return poisoned; }
}
