package WOW_Project;

public class PlayerShop
{
	private static GameDisplay GUI = PlayGame.GUI;
	
	private boolean hasVisited;
	
	private boolean upgrade1;
	private boolean upgrade2;
	private static boolean upgrade3;
	
	/**
	 * Constructs a player Shop to visit
	 * <br>
	 * NOTE: One shop per realm visited by a player
	 */
	public PlayerShop()
	{
		hasVisited = false;
		
		upgrade1 = false;
		upgrade2 = false;
	}
	
	/**
	 * Enters the store to receive available items offered
	 * <br>
	 * NOTE: Max 3 items per store
	 * @param shopper – The player (shopping in the store)
	 */
	public void enterShop(Player shopper)
	{
		boolean shopping = true;
		GUI.println("Welcome to the " + shopper.getCurrentRealm() + " market!");
		GUI.println("We have many items that we think you'll find very useful... if you have the gold to pay for them.");
		while(shopping)
		{
			GUI.println("Your gold supplies currently store " + shopper.getGold() + " gold.");
			GUI.println("We currently have these items available for you to buy: ");
			if(!upgrade1) GUI.println("[1] Item 1: Change Name (PRICE = 10 GOLD)"); else GUI.println("[1] Item 1: ALREADY PURCHASED"); 
			if(!upgrade2) GUI.println("[2] Item 2: Restore Health (PRICE = 20 GOLD)"); else GUI.println("[2] Item 2: ALREADY PURCHASED");
			if(!upgrade3) GUI.println("[3] Item 3: Upgrade Weapons (PRICE = 105 GOLD)"); else GUI.println("[3] Item 3: ALREADY PURCHASED");
			if(upgrade1 && upgrade2 && upgrade3) GUI.println("-- THERE ARE NO ITEMS AVAILABLE --");
			
			boolean choosing = true;
			while(choosing)
			{
				GUI.println("What would you like to do? [1] Buy item 1, [2] Buy item 2, [3] Buy item 3, [E] Exit the shop, [stats] View player stats");
				while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
				String shopperItem = GUI.playerCommand();
				if(shopperItem.equalsIgnoreCase("stats"))
				{
					shopper.getStats();
					choosing = false;
				}
				else if(shopperItem.equalsIgnoreCase("1"))
				{
					if(shopper.getGold() >= 10 && (!upgrade1))
					{
						GUI.println("You have purchased Item 1... Change Player Name");
						use1(shopper);
						shopper.takeGold(10);
					}
					else if(upgrade1) GUI.println("PLAYER ERROR: Either you have already purchased this item, or this item is not currently available.");
					else GUI.println("PLAYER ERROR: You do not have enough gold to purchase this item.");
					choosing = false;
				}
				else if(shopperItem.equalsIgnoreCase("2"))
				{
					if(shopper.getGold() >= 20 && (!upgrade2))
					{
						GUI.println("You have purchased Item 2... Restore Player Health");
						use2(shopper);
						shopper.takeGold(20);
					}
					else if(upgrade2) GUI.println("PLAYER ERROR: Either you have already purchased this item, or this item is not currently available.");
					else GUI.println("PLAYER ERROR: You do not have enough gold to purchase this item.");
					choosing = false;
				}
				else if(shopperItem.equalsIgnoreCase("3"))
				{
					if(shopper.getGold() >= 105 && (!upgrade3))
					{
						GUI.println("You have purchased Item 3... Upgrade Player Weapons");
						use3(shopper);
						shopper.takeGold(105);
					}
					else if(upgrade3) GUI.println("PLAYER ERROR: Either you have already purchased this item, or this item is not currently available.");
					else GUI.println("PLAYER ERROR: You do not have enough gold to purchase this item.");
					choosing = false;
				}
				else if(shopperItem.equalsIgnoreCase("E"))
				{
					GUI.println("Hope you found what you were looking for, and best of luck in your travels. Goodbye!");
					hasVisited = true;
					choosing = false;
					shopping = false;
				}
				else
				{
					GUI.println("INPUT ERROR: Invalid response. Try again. \n");
				}
			}
		}
	}
	
	/**
	 * Player receives upgrade 1: CHANGE NAME <br>
	 * This upgrade is the same for all player types, so it is not overridden within individual store types
	 * <br>
	 * NOTE: Player names must include at least one character
	 * @param shopper – Player to receive item
	 */
	public void use1(Player shopper)
	{
		String newName = shopper.getName();
		boolean choosing = true;
		while(choosing)
		{
			GUI.println("What would you like your new name to be?");
			while(!GUI.click) { try { Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); } }
			newName = GUI.playerCommand();
			if(newName.equalsIgnoreCase("") || newName.equals(null) || newName.equalsIgnoreCase(" ")) GUI.println("INPUT ERROR: Invalid name. Try again.");
			else choosing = false;
		}
		shopper.setNewName(newName);
		GUI.setIcon2(GUI.Picture_GameAlert);
		GUI.println("GAME ALERT: New PlayerName set. Your name is now:  " + shopper.getName() + ". \n");
		//Upgrade 1 can only be used once per shop, so [boolean]upgrade1 is set to TRUE (used)
		upgrade1 = true;
	}
	
	/**
	 * Player receives upgrade 2: HEALTH RESTORATION <br>
	 * This upgrade is not the same for all player types, so it is overridden within individual store types
	 * <br>
	 * NOTE: The amount of health restored differs based on the <code>PlayerType</code>
	 * @param shopper – Player to receive item
	 */
	public void use2(Player shopper)
	{
				//GUI.println("GamePlay ERROR: Error reading PlayerType in playerShop. Contact game administrator or exit shop to fix this issue.");
		//Player receives upgrade 2: RESTORE HEALTH
		//Upgrade 2 can only be used once per shop, so [boolean]upgrade2 is set to TRUE (used)
		GUI.setIcon2(GUI.Picture_GameAlert);
		upgrade2 = true;
	}
	
	/**
	 * Player receives upgrade 3: WEAPONS UPGRADE <br>
	 * This upgrade is the not same for all player types, so it is overridden within individual store types
	 * <br>
	 * NOTE: Players may only upgrade their weapons once per game, and the upgrade differs based on <code>PlayerType</code>
	 * @param shopper – Player to receive item
	 */
	public void use3(Player shopper)
	{
		//Player receives upgrade 3: UPGRADE WEAPONS
		GUI.setIcon2(GUI.Picture_GameAlert);
		shopper.setSpecialAttack(true);
		GUI.println("\n" + "You have recieved a special attack. This attack may be used ONLY ONCE to completely destroy one enemy you are battling.");
		GUI.println("Enter [KILL] to use this attack." + "\n");
		//Upgrade 3 can only be used once per game, so [static boolean]upgrade3 is set to TRUE (used)
		upgrade3 = true;
	}
	
	/**
	 * Checks if the store is open (if any upgrades are available)
	 * @return true (open), false (closed)
	 */
	public boolean isOpen()
	{
		if((!upgrade1) || (!upgrade2) || (!upgrade3)) return true;
		else return false;
	}
	
	/**
	 * Checks if a specified upgrade item has been used
	 * @param up – Upgrade Item Number
	 * @return true (not used / available), false (already used / unavailable)
	 */
	public boolean checkUse(int up){ if(up == 1)return upgrade1; if(up == 2)return upgrade3; if(up == 3)return upgrade3; else return false; }
	
	/**
	 * Checks if a store has been visited by a player before
	 * @return true (player has visited), false (player has not visited)
	 */
	public boolean checkVisits() { return hasVisited; }
}
