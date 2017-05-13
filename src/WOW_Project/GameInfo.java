package WOW_Project;

public abstract class GameInfo
{
	private static GameDisplay GUI = PlayGame.GUI;
	
	public static void intructions()
	{
		GUI.println("Players will select a player type, which will determine their player's attributes."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("You may select which player type you would like to be..."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Mage, King, Rogue, or Hunter"); try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("All player types have an equal chance of being damaged by an enemy."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("You may choose which action your player takes each turn."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("The actions you may take each turn depend on your player type."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("\n" + "You will amass enemy kills and gold throughout the game."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("You will earn an enemy kill whenever you defeat an enemy on the road by killing it (not when it flees)."); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("For every 50 gold you gain and keep through the end of a battle, your player will level up!"); try { Thread.sleep(340); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("When you level up, your orginal HP will be restored and then boosted by 10 points." + "\n");
		GUI.println("The game ends when you run out of HP, or when you reach level 10 and beat the game boss."); try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("You can only win by defeating the game boss."); try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("You can submit a command or response by pressing Enter or clicking the 'Go!' button.");
		GUI.println("  --  This game is in BETA. Thanks for playing!  --  ");try { Thread.sleep(60); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static void kingdomStory1()
	{
		GUI.println("YOU ARE NOW ENTERING THE KINGDOM OF CRANDALLCRAFT™");
		try { Thread.sleep(700); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println(" ' Pro honore, pro gloria, pro veritas. ' ");
		try { Thread.sleep(700); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("This is a realm full of dangerous monesters and magical beasts...");
		try { Thread.sleep(1050); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("But also a realm of adventure and opportunity for victory...");
		try { Thread.sleep(1050); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Around every corner, there is a new quest to embark on or new foe to defeat. \n");
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Long ago, The vast and powerful Kingdom of Crandallian was in harmony.");
		try { Thread.sleep(950); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Today, this once mighty kingdom has fallen and become a divided assortment of realms.");
		try { Thread.sleep(850); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.print("The Kingdom of Crandallian ");
		try { Thread.sleep(600); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("is no more. \n");
		try { Thread.sleep(1050); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("There are many realms to discover, many secrets to uncover.");
		try { Thread.sleep(950); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.print("ENTER ");
		try { Thread.sleep(600); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("A WORLD OF MAGIC, WAR, AND AMAZING ADVENTURE.");
		try { Thread.sleep(850); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("Your exciting future awaits...");
	}
	
	public static void mageI()
	{
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); } GUI.print("\n" + "Wonderful! You are now a Level 1 Mage!");
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); } GUI.println(" Mages wield great power, and can use it to grow stronger and defeat their foes.");
		GUI.println("As you progress through the game, you will face new challenges and defeat powerful enemies...");
		GUI.println("To play, follow the instructions onscreen. Type the letter commands in the prompt boxes [X] and press enter to complete an action.");
		GUI.println("");
		GUI.println("Now, embark on an adventure to become the best and most powerful in all the realms!");
	}
	
	public static void hunterI()
	{
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); } GUI.print("\n" + "Wonderful! You are now a Level 1 Hunter!");
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); } GUI.println(" Hunters are great at scouting for and fighting off their enemies. They are a great shot in war.");
		GUI.println("As you progress through the game, you will face new challenges and defeat powerful enemies...");
		GUI.println("To play, follow the instructions onscreen. Type the letter commands in the prompt boxes [X] and press enter to complete an action.");
		GUI.println("");
		GUI.println("Now, embark on an adventure to become the best and most powerful in all the realms!");
	}
	
	public static void kingI()
	{
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); } GUI.print("\n" + "Wonderful! You are now a Level 1 King!");
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); } GUI.println(" Kings are mighty leaders. They have huge armies at their command, and strong enchanted armour to protect them.");
		GUI.println("As you progress through the game, you will face new challenges and defeat powerful enemies...");
		GUI.println("To play, follow the instructions onscreen. Type the letter commands in the prompt boxes [X] and press enter to complete an action.");
		GUI.println("");
		GUI.println("Now, embark on an adventure to become the best and most powerful in all the realms!");
	}
	
	public static void rogueI()
	{
		try { Thread.sleep(140); } catch (InterruptedException e) { e.printStackTrace(); } GUI.print("\n" + "Wonderful! You are now a Level 1 Rogue!");
		try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); } GUI.println(" Rogues are fearsome warriors with a multitude of attacks to use in battle.");
		GUI.println("As you progress through the game, you will face new challenges and defeat powerful enemies...");
		GUI.println("To play, follow the instructions onscreen. Type the letter commands in the prompt boxes [X] and press enter to complete an action.");
		GUI.println("");
		GUI.println("Now, embark on an adventure to become the best and most powerful in all the realms!");
	}
	
	public static void adminStart()
	{
		GUI.println("\n|  GameManager ALERT: ADMINISTRATIVE GAMEMODE ACTIVE");
		GUI.println("|  ADMIN TASK ALERT: STAT MANIPULATION COMPLETE");
		try { Thread.sleep(155); } catch (InterruptedException e) { e.printStackTrace(); }
		GUI.println("|  Begin GamePlay Sequence...");
		GUI.println("-- ");
	}
}
