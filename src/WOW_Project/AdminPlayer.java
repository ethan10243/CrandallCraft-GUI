package WOW_Project;

import java.util.Random;

public class AdminPlayer extends Mage
{
	private static GameDisplay GUI = PlayGame.GUI;
	
	public AdminPlayer()
	{
		super("Mage");
		setHealth(987654);
		setHPThreshhold(999999);
		kills = 9999;
		heals = 9999;
		setLevel(9);
		setGold(501);
		GUI.println("\n" + "-- ADMIN COMMAND RECOGNIZED: GAME ADMINISTRATOR LOGIN ACCEPTED --" + "\n|");
	}

	private int kills;
	private int heals;
	
	private Random rand = new Random();
	
	//ATTACKS:
	
	public void fireball(RoadEnemy target)
	{
		int hpTaken = rand.nextInt(14) + 25;
		target.takeHP(hpTaken);
		GUI.println("You shot a fireball at the " + target.getEnemyType() + " and damaged it " + hpTaken + " health points.");
	}
	public void beat(RoadEnemy target)
	{

		int hpTaken = rand.nextInt(8) + 10;
		target.takeHP(hpTaken);
		GUI.println("You hit the " + target.getEnemyType() + " with a spell and damaged it 20 health points.");
	}
	public void strike(RoadEnemy target)
	{
		GUI.println("ADMIN EXCEPTION: You struck your enemy and damaged it 0 health points.");
	}
	
	
	//MOVEMENT:
	
	public void teleport()
	{
		//MOVING...
		GUI.println("GameManager ALERT: Admin GameWarp Override Enacted. Player TELEPORT Command Overridden.");
		GUI.println("x spaces: (positive / negative)");
		while(!GUI.click) { try { Thread.sleep(200); } catch (InterruptedException e) { GUI.setIcon2(GUI.Picture_GamePlayError); GUI.println("GameEnvironment ERROR: Thread.sleep – Process failure (ADMIN_PLAYER Line 54)"); } }
		int xSpaces = Integer.parseInt(GUI.playerCommand());
		GUI.println("y spaces: (positive / negative)");
		while(!GUI.click) { try { Thread.sleep(200); } catch (InterruptedException e) { GUI.setIcon2(GUI.Picture_GamePlayError); GUI.println("GameEnvironment ERROR: Thread.sleep – Process failure (ADMIN_PLAYER Line 57)"); } }
		int ySpaces = Integer.parseInt(GUI.playerCommand());
		//Change AdminPlayer position based on input
		changeUpX(xSpaces);
		changeUpY(ySpaces);
		GUI.println("Admin ALERT: You have teleported as requested. \n");
	}
	
	//STATS:
	
	public void normalStats()
	{
		setHealth(200);
		setHPThreshhold(250);
		kills = kills - 9999;
		heals = heals - 9999;
		setLevel(5);
		setGold(25);
		GUI.setIcon2(GUI.Picture_GameAlert);
		GUI.println("\n" + "ADMIN Task Status Alert: Stats Manipulation Complete." + "\n");
	}
	
	public void getStats()
	{	
		GUI.println("Current Player Stats:");
		GUI.println("------------------------------");
		GUI.println("playerName= " + " ADMINISTRATOR (Assumed Type: Mage)");
		GUI.println("playerLevel= " + getLevel());
		GUI.println("playerHealth= " + getHP());
		GUI.println("playerHealthThreshhold= " + getHPThreshhold());
		GUI.println("player(Mage)Heals= " + getMageHeals());
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
	
	public void getBattleStats()
	{	
		GUI.println("Current Player Battle Stats:");
		GUI.println("------------------------------");
		GUI.println("playerName= " + " ADMINISTRATOR (Assumed Type: Mage)");
		GUI.println("playerHealth= " + getBattleHP());
		GUI.println("playerPosition= ADMIN ACCESS ... BossArena (0, 0)");
		GUI.println("------------------------------");
	}
	
	public void attackInfo()
	{
		GUI.println("ADMIN_Mage Attack Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[F] Fireball: Launch a fireball at your enemy. (high damage, low chance of success)");
		GUI.println("[C] Cast Spell: Launch a harmful spell at your enemy. (mid damage, medium chance of success)");
		GUI.println("[S] Skip: Skip attacking. (NO damage)");
		GUI.println("[X] Flee: Run away from battle. (low chance of being damaged while fleeing)");
		GUI.println("-----------------------------------------------------");
	}
	
	public void actionInfo()
	{
		GUI.println("AdminPlayer Action Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("[stats] Stats: View current player stats. ");
		GUI.println("[M] Move: Move one space in specified direction. ");
		GUI.println("[H] Heal: Attempt to regain 10 health points. (May only be used if you are in need of healing)");
		GUI.println("[T] Teleport: Teleport a great distance (OVERRIDE: Specify movement distances {x,y}) ");
		GUI.println("[H] Heal: Attempt to regain 10 health points. (May only be used if you are in need of healing)");
		GUI.println();
		GUI.println("[ADMIN_Warp] Warp: Warp to a specified location (Supplied {x,y})");
		GUI.println("[ADMIN_Clear] Clear: Clear the GamePrompt Text (except the last printed line)");
		GUI.println("[ADMIN_normal] Set Stats: Reset playerStats to normal player stats (no ADMIN manipulation, exempting PlayerGold)");
		GUI.println("[ADMIN_restart] Force Restart: Force the GameSession to rebuild and restart");
		GUI.println("-----------------------------------------------------");
		GUI.println("Current GamePlay Session Info:");
		GUI.println("-----------------------------------------------------");
		GUI.println("GameSession   |val: gameTimer.tickerVal= " + GUI.gameTimer.getElapsedTime());
		GUI.println("GameManager  |val: KillThreshhold= " + GameManager.getKillThreshhold());
		GUI.println("GameManager  |val: killsForGold= " + GameManager.getKillsForGold());
		GUI.println("GameManager  |val: levelUpGold= " + GameManager.getLevelUpGold());
		GUI.println("GameManager  |val: levelsToBoss= " + GameManager.bossLevel());
		GUI.println("-----------------------------------------------------");
	}
	
	//OTHER:
	
	public void AClear() { GUI.clearGamePrompt(); }
	
	public void ARestart()
	{
		GUI.println("-- AdminGameManager Status Alert: 'admin command Force_RESTART caused this game session to reset.' --");
		GUI.clearGamePrompt();
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Status Check: REBUILDING...");
		GUI.println();
		try { Thread.sleep(700); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.clearGamePrompt();
		try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
		PlayGame.main(null);
	}
	
	public boolean checkForDeath() { return (getHP() < 1); }
	
	public void levelUp()
	{
		addLevel();
		setHealth(getHPThreshhold() + 10);
		setHPThreshhold(getHP());
	}
	
	public String getPlayerType() { return "Mage"; }  //ASSUMED <code>PlayerType</code> of Mage, for ADMIN_PLAYER
	
	public int getKills() { return kills; }
	public void setKills(int set) { kills = set; }
	public void addKills(int add) { kills += add; }
	
	public int getMageHeals() { return heals; }
	public void shopUpgrade() { heals = heals - 2; addHealth(15); }
	public void giveSpecialAttack(Player adminShopper)
	{
		PlayerShop adminShopCheat = new MageShop();
		adminShopCheat.use3(adminShopper);
		GUI.println("GAME ALERT: You have been given a Special Attack to use in battle! Type [KILL] to use it. \n");
	}
}
