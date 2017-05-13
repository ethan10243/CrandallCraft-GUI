package WOW_Project;

import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;

public class GameManager
{
	private static GameDisplay GUI = PlayGame.GUI;
	
	public static boolean inBossBattle;
	private static boolean adminReviveCatch;
	
	private final static int killsForGold = 15; //Kills needed to get gold
	public static int getKillsForGold() { return killsForGold; }
	private final static int levelUpGold = 50; //Gold needed to level up
	public static int getLevelUpGold() { return levelUpGold; }
	private final static int levelsToBoss = 10; //Player level needed to reach Boss Arena
	
	private static int killThreshhold; //Current <code>Player killThreshhold</code> ( + <code>killsForGold</code> = [limit to receive gold])
	public static int getKillThreshhold() { return killThreshhold; }
	private static boolean bossOver;
	
	private static Player p1; //The <code>Player</code>
	//private static KingdomRealm currentRealm;
	
	private static Random rand = new Random();
	
	public static void startGame(Player gamePlayer)
	{
		inBossBattle = false;
		killThreshhold = 0;
		bossOver = false;
		p1 = gamePlayer;
		//KingdomRealm.setOccupyingPlayer(p1);
		//currentRealm = new KingdomRealm("Homeland");
		adminReviveCatch = true;
	}
	
	/**
	 * Checks to see if the <code>Player</code> has reached the <code>killsForGold</code>
	 * <br>
	 * Handles <code>Player</code> gold gift (and <code>killThreshhold</code> setting) steps if <code>Player</code> has reached necessary kill threshhold
	 */
	public static void killCHECK()
	{
		if(p1.getKills() >= (killThreshhold + killsForGold))
		{
			GUI.println("\n" + "GAME ALERT: You have reached the playerKills threshhold!");
			GUI.print("You have recieved 10 gold!");
			p1.addGold(10);
			killThreshhold = p1.getKills();
			GUI.println("\n");
			GUI.setIcon2(GUI.Picture_Gold);
		}
	}
	
	/**
	 * Prompts the <code>Player</code> to make a choice on what they would like to do (each turn)
	 * @return <code>Player</code> action choice (from given options)
	 */
	public static String playerActionPrompt()
	{
		boolean choice = true;
		if(!GameManager.bossLives()) GUI.println("\n" + "Which action would you like to take?");
		while(choice)
		{
			if(p1.getPlayerType().equals("King"))
			{
				GUI.println("[stats] View Player Stats, [i] Action Info, [M] Move, [R] Ride with your legion");
				choice = false;
			}
			else if(p1.getPlayerType().equals("Hunter"))
			{
				GUI.println("[stats] View Player Stats, [i] Action Info, [M] Move, [H] Hunt");
				choice = false;
			}
			else if(p1.getPlayerType().equals("Mage"))
			{
				if(p1.getMageHeals() < 10) GUI.println("[stats] View Player Stats, [i] Action Info, [M] Move, [H] Heal, [T] Teleport");
				else GUI.println("[stats] View Player Stats, [i] Action Info, [M] Move, [T] Teleport");
				choice = false;
			}
			else if(p1.getPlayerType().equals("Rogue"))
			{
				GUI.println("[stats] View Player Stats, [i] Action Info, [M] Move, [S] Scout");
				choice = false;
			}
			else GUI.println("|  GAME PlayerType ERROR: PlayerType not specified. Options not available.  |");
		}
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
		
		while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
		String playerTurnAction = GUI.playerCommand();
		
		return playerTurnAction;
	}
	
	/**
	 * Game acts based on the choice of the <code>Player</code>
	 * @param playerAction – the action choice of the <code>Player</code>
	 */
	public static boolean adminResponseCatch(String playerTurnAction)
	{
		boolean trigger = false;
		
		if(playerTurnAction.equals("ADMIN_list")) { trigger = true; GUI.print("\n" + "Administrator Commands:" + "\n" + "MONEY_OVERRIDE" + "\n" + "HEALTH_OVERRIDE" + "\n" + "HEALTH_DRAIN" + "\n" + "FORCEQUITGAME" + "\n" + "WARP" + "\n---\n\n"); }
		else if(playerTurnAction.equals("ADMIN_normal")) { trigger = true; ((AdminPlayer) p1).normalStats(); }
		else if(playerTurnAction.equals("ADMIN_MONEY_OVERRIDE")) { trigger = true; GUI.print("amount: "); while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } } String ADMIN_command1 = GUI.playerCommand(); int goldSet = Integer.parseInt(ADMIN_command1); p1.setGold(goldSet); }
		else if(playerTurnAction.equals("ADMIN_HEALTH_OVERRIDE")) { trigger = true; GUI.print("HP: "); while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } } String ADMIN_command1 = GUI.playerCommand(); int hpSet = Integer.parseInt(ADMIN_command1); p1.setHealth(hpSet); }
		else if(playerTurnAction.equals("ADMIN_HEALTH_DRAIN")) { trigger = true; p1.takeHealth(p1.getHP()); }
		else if(playerTurnAction.equals("ADMIN_FORCEQUITGAME")) { trigger = true; try{Thread.sleep(300);} catch(InterruptedException e){ e.printStackTrace(); }GUI.println("-- ADMIN COMMAND RECOGNIZED: IMMEDIATE FORCE GAME QUIT --"); System.exit(0); }
		else if(playerTurnAction.equals("ADMIN_WARP"))
		{
			GUI.print("x: "); while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } } String ADMIN_command1 = GUI.playerCommand(); int xCo = Integer.parseInt(ADMIN_command1);
			GUI.print("y: "); while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } } String ADMIN_command2 = GUI.playerCommand(); int yCo = Integer.parseInt(ADMIN_command2);
			
			p1.setX(xCo); p1.setY(yCo);
			trigger = true;
		}
		else if(playerTurnAction.equals("ADMIN_Clear")) { ((AdminPlayer) p1).AClear(); trigger = true; }
		else if(playerTurnAction.equals("ADMIN_restart")) { trigger = true; ((AdminPlayer) p1).ARestart(); }
		
		return trigger;
	}
	
	/**
	 * Evaluates chance of a road encounter for a <code>Player</code> when they move great distances
	 * <br>
	 * Takes action based on chance
	 */
	public static void roadElements()
	{
		if(p1.getPlayerType().equalsIgnoreCase("Mage"))
		{
			int chanceForGain = rand.nextInt(3);
			if((p1.getCurrentRealm().equals("Dark Forest")) || chanceForGain == 0)
			{
				int randomHealthLost = rand.nextInt(9) + 3;
				if(randomHealthLost > p1.getHP()) randomHealthLost = p1.getHP();
				p1.takeHealth(randomHealthLost);
				GUI.println("You teleported into dangerous territory and were attacked. You lost " + randomHealthLost + " health points.");
			}
			else if(chanceForGain == 1)
			{
				GUI.println("You have discovered gold!");
				giveReward(1);
			}
			else if(chanceForGain == 2)
			{
				GUI.println("You teleported near a robber gang!");
				takeReward(1);
			}
		}
		else if(p1.getPlayerType().equalsIgnoreCase("King"))
		{
			int chanceForGain = rand.nextInt(3);
			if((p1.getCurrentRealm().equals("Dark Forest")) || chanceForGain == 2)
			{
				int healthLost = rand.nextInt(30) + 5;
				if(healthLost > p1.getHP()) healthLost = p1.getHP();
				p1.takeHealth(healthLost);
				GUI.println("Your legion was attacked on the road! You lost " + healthLost + " health points.");
			}
			else if(chanceForGain == 0)
			{
				GUI.println("You plundered a village!");
				giveReward(1);
			}
			else if(chanceForGain == 1)
			{
				GUI.println("You attempted to plunder a village, but you failed!");
				takeReward(2);
			}
		}
		else if(p1.getPlayerType().equalsIgnoreCase("Hunter"))
		{
			int chanceForGain = rand.nextInt(3);
			if((p1.getCurrentRealm().equals("Dark Forest")) || chanceForGain == 2)
			{
				int healthLost = rand.nextInt(10) + 1;
				p1.takeHealth(healthLost);
				if(healthLost > p1.getHP()) healthLost = p1.getHP();
				GUI.println("You were attacked on the road! You lost " + healthLost + " health points.");
			}
			else if(chanceForGain == 0)
			{
				GUI.println("You found an abandoned satchel filled with coins!");
				giveReward(1);
			}
			else if(chanceForGain == 1)
			{
				GUI.println("You walked into a theives' town, and were robbed!");
				takeReward(1);
			}
		}
		else if(p1.getPlayerType().equalsIgnoreCase("Rogue"))
		{
			int chanceForGain = rand.nextInt(3);
			if((p1.getCurrentRealm().equals("Dark Forest")) || chanceForGain == 2)
			{
				int healthLost = rand.nextInt(9) + 3;
				if(healthLost > p1.getHP()) healthLost = p1.getHP();
				p1.takeHealth(healthLost);
				GUI.println("You were attacked on the road! You lost " + healthLost + " health points.");
			}
			else if(chanceForGain == 0)
			{
				GUI.println("You found an abandoned camp with supplies and gold!");
				giveReward(1);
			}
			else if(chanceForGain == 1)
			{
				GUI.println("You ran into a fierce enemy who robbed you!");
				takeReward(1);
			}
		}
	}
	
	/**
	 * Selects an enemy type based on <code>Player</code>'s current level
	 * @return enemy – enemy to be faced in battle
	 */
	public static RoadEnemy revealEnemyType()
	{
		RoadEnemy enemy;
		int enemyType;
		if(p1.getLevel() >= 8) enemyType = rand.nextInt(12) + 1;
		else if(p1.getLevel() >= 5) enemyType = rand.nextInt(10) + 1;
		else if(p1.getLevel() >= 3) enemyType = rand.nextInt(7) + 1;
		else enemyType = rand.nextInt(6) + 1;
		//Level 1+
		if(enemyType == 1) { enemy = new RoadMage(); GUI.setIcon2(GUI.Picture_RoadMage); }
		else if(enemyType == 2) { enemy = new RoadMage(); GUI.setIcon2(GUI.Picture_RoadMage); }
		else if(enemyType == 3) { enemy = new RoadDragon(); GUI.setIcon2(GUI.Picture_RoadDragon); }
		else if(enemyType == 4) { enemy = new RoadDarkMage(); GUI.setIcon2(GUI.Picture_RoadDarkMage);  }
		else if(enemyType == 5) { enemy = new RoadSoldier(); GUI.setIcon2(GUI.Picture_RoadSoldier); }
		else if(enemyType == 6) { enemy = new RoadRobber(); GUI.setIcon2(GUI.Picture_RoadRobber); }
		//Level 3+
		else if(enemyType == 7) { enemy = new RoadDarkElf(); GUI.setIcon2(GUI.Picture_RoadDarkElf); }
		//Level 5+
		else if(enemyType == 8) { enemy = new RoadElemental(); GUI.setIcon2(GUI.Picture_RoadElemental); }
		else if(enemyType == 9) { enemy = new RoadGiant(); GUI.setIcon2(GUI.Picture_RoadGiant); }
		else if(enemyType == 10) { enemy = new RoadBeast(); GUI.setIcon2(GUI.Picture_RoadBeast); }
		//Level 8+
		else if(enemyType == 11) { enemy = new RoadOrc(); GUI.setIcon2(GUI.Picture_RoadOrc); }
		else if(enemyType == 12) { enemy = new RoadEvolvedDarkMage(); GUI.setIcon2(GUI.Picture_RoadEvolvedDarkMage); }
		else { GUI.println("|  GAME ERROR: EnemyType assignment error. Please see game administrator or restart game to resolve issue.  |"); enemy = new RoadEnemy(); GUI.setIcon2(GUI.Picture_GamePlayError); }
		GUI.println("Your enemy is a " + enemy.getEnemyType() + " (HP: " + enemy.getHP() + ")");
		
		return enemy;
	}
	
	/**
	 * Executes a road battle between the <code>Player</code> and a <code>RoadEnemy</code>
	 * @param enemy – The <code>Player</code>'s enemy in battle (target)
	 */
	public static void roadBattle(RoadEnemy enemy)
	{
		boolean fleeStatus = false;
		while(!enemy.isDefeated() && !p1.isDead() && fleeStatus == false)
		{
			GUI.println("");
			if(p1.getPlayerType().equals("King"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [i] Attack Info, [L] Launch Catapult, [P] Plunder, [S] Strike, [X] Flee");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("I")) { p1.attackInfo(); }
					else if(p1AttackChoice.equalsIgnoreCase("L")) { p1.shoot(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("P")) { p1.plunder(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("X")) { p1.playerFlee(); playerAttacking = false; enemy.setFlee(true); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Hunter"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [i] Attack Info, [A] Arrow Volley, [S] Strike, [X] Flee");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("I")) { p1.attackInfo(); }
					else if(p1AttackChoice.equalsIgnoreCase("A")) { p1.shoot(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("X")) { p1.playerFlee(); playerAttacking = false; enemy.setFlee(true); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Mage"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [i] Attack Info, [F] Shoot Fireball, [S] Strike, [X] Flee");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("I")) { p1.attackInfo(); }
					else if(p1AttackChoice.equalsIgnoreCase("C")) { p1.beat(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("F")) { p1.fireball(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("X")) { p1.playerFlee(); playerAttacking = false; enemy.setFlee(true); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Rogue"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [i] Attack Info, [B] Beat, [R] Rob, [S] Strike, [X] Flee");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("I")) { p1.attackInfo(); }
					else if(p1AttackChoice.equalsIgnoreCase("B")) { p1.beat(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("R")) { p1.rob(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")"); }
					else if(p1AttackChoice.equalsIgnoreCase("X")) { p1.playerFlee(); playerAttacking = false; enemy.setFlee(true); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(enemy); playerAttacking = false; GUI.println("(Updated Enemy HP: " + enemy.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
			GUI.println("\n");
			//Game Scan:
			if(enemy.getFlee() && enemy.getHP() > 0) { GUI.println(""); fleeStatus = true; GUI.setIcon2(GUI.Picture_BLANK); } // PLAYER FLEES
			else if(enemy.getFlee() && enemy.getHP() < 1) { GUI.println("Your enemy has fled!"); fleeStatus = true; GUI.setIcon2(GUI.Picture_BLANK); } //ENEMY FLEES
			else if(!enemy.getFlee() && enemy.getHP() < 1) { GUI.println("You have defeated your enemy!"); p1.addKills(1); GUI.setIcon2(GUI.Picture_BLANK); }
			//Enemy Response:
			else if(!enemy.getFlee() && enemy.getHP() > 0) enemy.enemyAttack(p1); //ENEMY NOT DEFEATED, enemy attacks
		}
	}
	
	/**
	 * Executes a road battle between the <code>Player</code> and the <code>gameBoss</code>
	 * @param enemy – The <code>Player</code>'s enemy in battle (target) ... will be <code>gameBoss</code>
	 */
	public static void bossBattle()
	{
		inBossBattle = true;
		GUI.println("You have reached PLAYER LEVEL " + GameManager.bossLevel() + ". In order to win the game, you must beat the game boss.");
		int givenBattleHP = rand.nextInt(25) + 60;
		int restoreHP = p1.getHP();
		p1.addHealth(givenBattleHP);
		GUI.println("You have been given a gift to use in battle: " + givenBattleHP + " extra HP. \n");
		GUI.println("---------------------------------------------------------------------");
		if(p1 instanceof AdminPlayer) GUI.println("ADMIN ALERT: Type [ADMIN_giveSpecial] to instantly buy the special [KILL] attack from a realm shop.");
		GUI.println("");
		int bossType = rand.nextInt(3);
		RoadEnemy gameBoss;
		if(bossType == 0 || bossType == 1) { gameBoss = new DragonBoss(); }
		else { gameBoss = new WarlockBoss(); p1.warlockSetP(false); }
		p1.createBattleHP();
		while(!p1.checkBattleHP() && !gameBoss.checkBossHP())
		{
			//Player attack
			if(p1.getPlayerType().equals("King"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [L] Launch Missle, [P] Plunder, [S] Strike");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getBattleStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("L")) { p1.shoot(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("P")) { p1.plunder(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Hunter"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [A] Arrow Volley, [S] Strike");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getBattleStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("A")) { p1.shoot(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Mage"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [F] Shoot Fireball, [S] Strike");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getBattleStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("F")) { p1.fireball(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("C")) { p1.beat(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if((p1AttackChoice.equalsIgnoreCase("ADMIN_giveSpecial")) && (p1 instanceof AdminPlayer)) ((AdminPlayer) p1).giveSpecialAttack(p1);
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			else if(p1.getPlayerType().equals("Rogue"))
			{
				boolean playerAttacking = true;
				while(playerAttacking)
				{
					GUI.println("What would you like to do?");
					GUI.println("[stats] View Player Stats, [B] Beat, [R] Rob, [S] Strike");
					while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
					String p1AttackChoice = GUI.playerCommand();
					if(p1AttackChoice.equalsIgnoreCase("stats")) { p1.getBattleStats(); }
					else if(p1AttackChoice.equalsIgnoreCase("B")) { p1.beat(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("S")) { p1.strike(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("R")) { p1.rob(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else if(p1AttackChoice.equalsIgnoreCase("KILL")) { p1.special(gameBoss); playerAttacking = false; GUI.println("(Updated Enemy HP: " + gameBoss.getHP() + ")\n"); }
					else GUI.println("INPUT ERROR: Invalid input. Please try again.");
				}
			}
			
			GUI.println("");
			if(p1.warlockGetP()) { int hp = rand.nextInt(7) + 10; p1.takeBattleHP(hp); GUI.println("The dagger the Warlock hit you with earlier was poisoned. Now that it has taken effect, you have lost " + hp + " health points."); p1.warlockSetP(false); GUI.println("(Player HP: " + p1.getBattleHP() + ")"); }
			GUI.println("");
			//Boss attack
			if(!gameBoss.checkBossHP()) gameBoss.bossAttack(p1);
			GUI.println("");
		}
		GUI.setIcon2(GUI.Picture_GameAlert);
		GUI.println("\n");
		if(p1.checkBattleHP()) { inBossBattle = false; GUI.println("You have lost to the Boss. Your level has been reduced back to level " + (GameManager.bossLevel() / 2) + "."); p1.setHPThreshhold(restoreHP); p1.setGold(10); p1.setX(0); p1.setY(0); p1.setHealth(restoreHP / 2); }
		else if(gameBoss.checkBossHP()) { inBossBattle = false; GUI.println("You have beaten to the Boss! YOU WIN!" + "\n"); GUI.setIcon2(GUI.Picture_Gold); }
		if(gameBoss.checkBossHP()) GameManager.killBoss();
		else { p1.setLevel((GameManager.bossLevel() / 2)); GUI.setIcon2(GUI.Picture_BLANK); }
		GUI.println("---------------------------------------------------------------------");
		GUI.println("\n");
	}
	
	/**
	 * Checks to see if the <code>Player</code> has reached the <code>levelUpGold</code>
	 * <br>
	 * Handles <code>Player</code> level up steps if <code>Player</code> has reached necessary gold threshhold
	 */
	public static void lvlCHECK()
	{
		if(p1.getGold() >= levelUpGold)
		{
			GUI.println("\n" + "GAME ALERT: You have " + levelUpGold  + " gold!");
			GUI.println("Would you like to spend this gold now to level up? [Y] for YES, [N] for NO.");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			String lvlP = GUI.playerCommand();
			if(lvlP.equalsIgnoreCase("Y"))
			{
				p1.levelUp();
				GUI.print("\n" + "You are now a level " + p1.getLevel() + " " + p1.getPlayerType());
				p1.takeGold(levelUpGold);
				GUI.println("\n");
			}
			else GUI.print("Very well, you will not level up. \n\n");
		}
	}
	
	/**
	 * Prompts <code>Player</code> for desired movement direction
	 * @return direction – direction to move (U, D, L, or R)
	 */
	public static String moveDirection()
	{
		boolean dirChoose = true;
		String direction = "No Direction";
		while(dirChoose)
		{
			GUI.println("Which direction?");
			GUI.println("[U] up, [D] down, [L] left, [R] right");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			direction = GUI.playerCommand();
			if(direction.equalsIgnoreCase("U") || direction.equalsIgnoreCase("D") || direction.equalsIgnoreCase("L") || direction.equalsIgnoreCase("R")) { dirChoose = false; GUI.setIcon2(GUI.Picture_BLANK); }
			else { GUI.println("INPUT ERROR: Invalid direction choice. Please try again."); GUI.setIcon2(GUI.Picture_InputError); }
		}
		
		return direction;
	}
	
	/**
	 * Checks and updates the <code>Player</code> realm
	 * <br>
	 * Activates <code>Player</code>'s <code>checkPlayerRealm()</code> method
	 */
	public static void realmCHECK() { p1.checkPlayerRealm(); }
	
	/**
	 * Checks to see if the <code>Player</code> has reached the <code>levelsToBoss</code>
	 * <br>
	 * @return <code>true</code> if the Player will battle the <code>gameBoss</code>, <code>false</code> if not
	 */
	public static boolean bossCHECK()
	{
		return (p1.getLevel() >= levelsToBoss);
	}
	
	/**
	 * Checks to see if the game is over
	 * @return <code>true</code> to continue, <code>false</code> to end game
	 */
	public static boolean theGameContinues() { return (!p1.isDead() && !bossOver); }
	
	/**
	 * Checks to see if the <code>gameBoss</code> is still alive
	 * @return <code>true</code> if the <code>gameBoss</code> is dead, <code>false</code> if the <code>gameBoss</code> is dead
	 */
	public static boolean bossLives() { return (!bossOver); }
	
	/**
	 * Sets <code>bossOver</code> to true
	 * <br>
	 * Used when the <code>gameBoss</code> is dead / defeated
	 */
	public static void killBoss() { bossOver = true; inBossBattle = false; }
	
	/**
	 * Checks which level the player must reach to end the game
	 * @return the level the <code>Player</code> must reach to battle the <code>gameBoss</code>
	 */
	public static int bossLevel() { return levelsToBoss; }
	
	/**
	 * Checks the <code>Player</code>'s live status
	 * @return <code>true</code> if <code>Player</code> is alive, <code>false</code> is <codePlayer</code> is dead
	 */
	public static boolean playerLives() { return (!(p1.getHP() < 1) && (adminReviveCatch)); }
	
	/**
	 * Handles gold rewards for various achievements throughout the game. </br>
	 * Gives gold to the player based on: level of the achievement, and the current <code>playerLevel</code>.
	 * @param achLevel – level of achievement. 1 = low, 2 = medium, 3 = high.
	 * 
	 * Pre-condition: level is an integer between 1 and 3 (inclusive).
	 */
	public static void giveReward(int achLevel)
	{
		int goldReward = -1;
		if(p1.getLevel() > 5) achLevel--;
		GUI.setIcon2(GUI.Picture_Gold);
		
		if(achLevel <= 0)
		{
			goldReward = rand.nextInt(5) + 1;
		}
		//Example: Beating a simple enemy (tier 1)
		else if(achLevel == 1)
		{
			goldReward = rand.nextInt(10) + 5;
		}
		//Example: Beating a high-level enemy (tier 2 - 3), discovering a new realm (common)
		else if(achLevel == 2)
		{
			goldReward = rand.nextInt(20) + 10;
		}
		//Example: Beating a very high-level enemy (tier 3 - 4), discovering a new realm (rare)
		else if(achLevel == 3)
		{
			goldReward = rand.nextInt(15) + 20;
		}
		else if(achLevel >= 4)
		{
			goldReward = rand.nextInt(15) + 20;
			GUI.println("GameManager ALERT: GameManager Execution Error (codeIn?pre:false, GameManager.giveReward())");
		}
		
		if(goldReward <= 0) { goldReward = 1; GUI.println("GameManager ALERT: Registered PlayerData Error (management?stats:gold, GameManager.giveReward())"); }
		
		p1.addGold(goldReward);
		GUI.println("You have gained " + goldReward + " gold!");
	}
	
	/**
	 * Handles gold penalties for various misfortunes throughout the game. </br>
	 * Takes gold from the player based on: level of the achievement, and the current <code>playerLevel</code>.
	 * </br>
	 * PRE-CONDITION: level is an integer between 1 and 3 (inclusive).
	 * 
	 * @param achLevel – level of loss. 1 = low, 2 = medium, 3 = high.
	 */
	public static void takeReward(int achLevel)
	{
		int goldPenalty = -1;
		if(p1.getLevel() > 5) achLevel++;
		
		if(achLevel <= 0)
		{
			goldPenalty = rand.nextInt(5) - 8;
			GUI.println("GameManager ALERT: GameManager Execution Error (codeIn?pre:false, GameManager.takeReward())");
		}
		//Example: Robbed on the road
		else if(achLevel == 1)
		{
			goldPenalty = rand.nextInt(10) - 20;
		}
		//Example: 
		else if(achLevel == 2)
		{
			goldPenalty = rand.nextInt(10) - 30;
		}
		//Example: 
		else if(achLevel == 3)
		{
			goldPenalty = rand.nextInt(10) - 40;
		}
		else if(achLevel >= 4)
		{
			goldPenalty = rand.nextInt(9) - 49;
		}
		
		if(goldPenalty >= 0) { goldPenalty = -5; GUI.println("GameManager ALERT: Registered PlayerData Error (management?stats:gold, GameManager.takeReward())"); }
		if(goldPenalty > p1.getGold()) { goldPenalty = p1.getGold(); }
		
		if(goldPenalty > 0)
		{
			p1.addGold(goldPenalty);
			GUI.println("You lost " + ((-1) * goldPenalty) + " gold!");
		}
		else if(goldPenalty == 0) { GUI.println("You have no more gold to lose."); }
		else
		{
			GUI.println("GameManager ALERT: Unregistered PlayerData Error (management?stats:gold, GameManager.takeReward())");
		}
	}
	
	/**
	 * Ends the current <code>game session</code>
	 * <br>
	 * <i>ADMIN NOTE: Includes &nbsp;<code>administrative revival catch</code>&nbsp; and &nbsp;<code>print Game Record</code>&nbsp; procedure</i>
	 */
	public static void endGame()
	{
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
		if(p1.getHP() < 1) { GUI.println("GAME ALERT: You have lost all your HP. GAME OVER." + "\n"); }
		else if(!(GameManager.bossLives())) { GUI.println("GAME ALERT: You have won the game!"); p1.addGold(999); }
		else { GUI.println("GAME ALERT: The game has ended. \n"); }
		GUI.println("Ending Player Stats... \n"); p1.getStats();
		try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("\nQuitting game...\n");
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Thanks for playing KINGDOM OF CRANDALLCRAFT™!");
		while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
		String revival = "end";
		if(p1.getHP() < 1) revival = GUI.playerCommand();
		//
		if(revival.equals("ADMIN_reviveMe"))
		{
			GUI.setIcon2(GUI.Picture_AdminPlayer);
			try { Thread.sleep(700); } catch(InterruptedException e) { e.printStackTrace(); }
			GUI.hiddenprintln("\n\n" + "-- ADMIN COMMAND RECOGNIZED: ADMIN PLAYER REVIVAL ('Second Chance' Cheat) --");
			GUI.hiddenprintln("|  ADMIN ALERT: Player revival initiated... Please enter below \n|\n|\n|");
			GUI.hiddenprintln("|  Starting HP:  ");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { GUI.setIcon2(GUI.Picture_GamePlayError); GUI.print("GameEnvironment ERROR: Thread.sleep – Process failure (GameManager.endGame())"); } }
			int adminHealthReset = Integer.parseInt(GUI.playerCommand());
			GUI.hiddenprintln("|  PlayerName:  ");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { GUI.setIcon2(GUI.Picture_GamePlayError); GUI.print("GameEnvironment ERROR: Thread.sleep – Process failure (GameManager.endGame())"); } }
			String adminNameReset = GUI.playerCommand();
			
			p1.setHealth(adminHealthReset);
			p1.setNewName(adminNameReset);
			GameManager.startGame(p1);
			adminReviveCatch = true;
			GUI.hiddenprintln("-- AdminGameManager Status Alert: 'admin command REVIVE caused this game session to reset.' --");
			GUI.println("\n" + "GamePlay ERROR: GameMonitor encountered an unexpected error. The cause is unknown. \nPlease contact a game administrator if you see any more problems persist. (monitor?gameplay:playerStatus, GameManager.endGame())\n" + "\n");
			GUI.setIcon2(GUI.Picture_GamePlayError);
		}
		else if(revival.equals("ADMIN_printGameRecord"))
		{
			try {
				PrintWriter writer = new PrintWriter("/Users/Master/Desktop/LatestGameRecord.txt");
				writer.println("CRANDALLCRAFT GAME RECORD:" + "\n");
				writer.println("Game Session Information:");
				writer.println("Session Access Username: " + p1.getName());
				writer.println("Active Session Time: " + GUI.getGameTime());
				writer.println("Game Session Transcript: " + "\n" + "________________" + "\n");
				writer.println(GUI.retrieveGameRecord());
				writer.close();
				GUI.println("-- GameManager Alert: PrintWriter successful, game record saved to external file --");
			} catch (FileNotFoundException e) {
				GUI.println("-- GameManager Error: PrintWriter error, unable to save game record to file --");
				GUI.setIcon1(GUI.Picture_GamePlayError);
				GUI.setIcon2(GUI.Picture_GamePlayError);
			}
			finally
			{
				try { Thread.sleep(200); } catch(InterruptedException e) { GUI.setIcon2(GUI.Picture_GamePlayError); GUI.print("GameEnvironment ERROR: Thread.sleep – Process failure (GameManager.endGame())"); }
				GUI.setIcon1(GUI.Picture_GameAlert); GUI.setIcon2(GUI.Picture_GameAlert); GUI.println("-- GameManager Status Alert: 'this game session has closed. goodbye.' --"); adminReviveCatch = false; p1.setHealth(-1);
			}
		}
		else { GUI.setIcon1(GUI.Picture_GameAlert); GUI.setIcon2(GUI.Picture_GameAlert); GUI.println("-- GameManager Status Alert: 'this game session has closed. goodbye.' --"); adminReviveCatch = false; p1.setHealth(-1); }
	}
	
}

//Created and developed by Ethan D Bütt, copyright Ethan D Bütt (2017)