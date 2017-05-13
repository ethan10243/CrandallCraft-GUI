package WOW_Project;

import java.util.Random;

public class PlayGame
{
	public static GameDisplay GUI = new GameDisplay();
	
	public static void main(String[] args)
	{	
		//GameDisplay GUI = new GameDisplay();
		
		Random rand = new Random();
		
		//STARTUP...
		try { Thread.sleep(120); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.print("Launching.");
		for(int i = 0; i < 5; i++)
		{
			try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
			GUI.print(".");
		}
		try { Thread.sleep(260); } catch (InterruptedException e) { e.printStackTrace(); }
		
		boolean playerTypeChoice0 = false;
		
		GUI.println("\n\n" + "HELLO, AND WELCOME TO KINGDOM OF CRANDALLCRAFT™" + "\n");
		GUI.println("What is your name?  \n");
		while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
		String playerUsername = GUI.playerCommand();
		GUI.println("Logging in.");
		for(int i = 0; i <= 3; i++)
		{
			try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
			GUI.print(".");
		}
		try { Thread.sleep(260); } catch (InterruptedException e) { e.printStackTrace(); }
		if(playerUsername.equals("ADMIN_PLAYER")) playerTypeChoice0 = true;
		GUI.println("  Logged in as " + playerUsername);
		if(playerUsername.equals("ADMIN_PLAYER")) ;
		else
		{
			GUI.println("Is this your first time playing? (Enter 'Y' for intro story)");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			String instruct2 = GUI.playerCommand();
			if(instruct2.equalsIgnoreCase("Y"))
			{
				GameInfo.kingdomStory1();
				GUI.println("");
			}
		}
		if(playerUsername.equals("ADMIN_PLAYER")) ;
		else
		{
			GUI.println("Is this your first time playing? (Enter 'Y' for instructions)");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			String instruct = GUI.playerCommand();
			if(instruct.equalsIgnoreCase("Y"))
			{
				GameInfo.intructions();
				GUI.println("");
			}
		}
		//SETUP CHARACTER...
		
		Player player1 = null;
		String player1Type = "Player";
		while(!playerTypeChoice0)
		{
			GUI.println("Which player type would you like to be?");
			GUI.println("Enter: [R] Rogue, [M] Mage, [K] King, [H] Hunter");
			GUI.println("Enter:  ");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			String playerType = GUI.playerCommand();

			if(playerType.equalsIgnoreCase("R"))
			{
				GUI.setIcon1(GUI.Picture_Rogue);
				player1Type = "Rogue";
				player1 = new Rogue(playerUsername);
				GameInfo.rogueI();
				playerTypeChoice0 = true;
			}
			else if(playerType.equalsIgnoreCase("M"))
			{
				GUI.setIcon1(GUI.Picture_Mage);
				player1Type = "Mage";
				player1 = new Mage(playerUsername);
				GameInfo.mageI();
				playerTypeChoice0 = true;
			}
			else if(playerType.equalsIgnoreCase("K"))
			{
				GUI.setIcon1(GUI.Picture_King);
				player1Type = "King";
				player1 = new King(playerUsername);
				GameInfo.kingI();
				playerTypeChoice0 = true;
			}
			else if(playerType.equalsIgnoreCase("H"))
			{
				GUI.setIcon1(GUI.Picture_Hunter);
				player1Type = "Hunter";
				player1 = new Hunter(playerUsername);
				GameInfo.hunterI();
				playerTypeChoice0 = true;
			}
			else
			{
				GUI.setIcon1(GUI.Picture_InputError);
				GUI.println("INPUT ERROR: Invalid type input. Please try again.");
			}
		}
		if(playerUsername.equals("ADMIN_PLAYER"))
		{
			GUI.setIcon1(GUI.Picture_AdminPlayer);
			player1Type = "Mage";
			player1 = new AdminPlayer();
			GameInfo.adminStart();
		}
		GUI.println("\n" + "Here are your starting stats... ");
		player1.getStats();
		GUI.println("You may look at the STATS panel at any time to see your player's current stats (or type [stats] in the command box.)");
		GUI.println("\n\n");
		GameDisplay.setPlayer(player1);
		
		GameManager.startGame(player1);
		/**
		 * GAMEPLAY BELOW...
		 */
		
		while(GameManager.playerLives())
		{
		// PLAYING...		
			while(GameManager.theGameContinues())
			{
				//Realm Check:
				GameManager.realmCHECK();
				
				//Check player kills
				GameManager.killCHECK();
				
				//Level Up check:
				GameManager.lvlCHECK();
				
				//BOSS CHECK:
				
				if(GameManager.bossCHECK())
				{
					GameManager.bossBattle();
				}
				
				
				int chanceOfEncounter = rand.nextInt(5);
				
				String playerTurnAction = GameManager.playerActionPrompt();
				
				//ADMIN Commands...
				if(GameManager.adminResponseCatch(playerTurnAction)) GUI.println();
				//PLAYER Commands...
				else if(playerTurnAction.equalsIgnoreCase("stats")) { player1.getStats(); }
				else if(playerTurnAction.equalsIgnoreCase("M"))
				{
					
					String direction = GameManager.moveDirection();
					player1.move(direction);
					GUI.println("");
					if(chanceOfEncounter == 0)
					{
						GUI.println("\n");
					}
					else if(chanceOfEncounter == 4)
					{
						GameManager.roadElements();
					}
					else if(chanceOfEncounter == 1 || chanceOfEncounter == 2 || chanceOfEncounter == 3)
					{
						GUI.println("\n" + "You encountered an enemy on the road!" + "\n");
						//Enemy assignment...
						RoadEnemy enemy = GameManager.revealEnemyType();
						//Battle...
						GameManager.roadBattle(enemy);
					}
				}
				else if(player1Type.equalsIgnoreCase("Mage") && playerTurnAction.equalsIgnoreCase("H"))
				{
					player1.heal();
				}
				else if(player1Type.equalsIgnoreCase("Mage") && playerTurnAction.equalsIgnoreCase("T"))
				{
					player1.teleport();
					GameManager.roadElements();
				}
				else if(player1Type.equalsIgnoreCase("King") && playerTurnAction.equalsIgnoreCase("R"))
				{
					player1.ride();
					GameManager.roadElements();
				}
				else if(player1Type.equalsIgnoreCase("Hunter") && playerTurnAction.equalsIgnoreCase("H"))
				{
					player1.hunt();
					GameManager.roadElements();
				}
				else if(player1Type.equalsIgnoreCase("Rogue") && playerTurnAction.equalsIgnoreCase("S"))
				{
					player1.scout();
					GameManager.roadElements();
				}
				else if(playerTurnAction.equalsIgnoreCase("I")) { player1.actionInfo(); }
				else GUI.println("INPUT ERROR: Invalid action choice. Please try again.");
			}
		GameManager.endGame();
		}
	}
}


//Created by Ethan Bütt, copyright Ethan Bütt (2017)