package WOW_Project;

import java.util.Random;

public class HunterShop extends PlayerShop
{
	private GameDisplay GUI = PlayGame.GUI;
	
	public HunterShop()
	{
		super();
	}
	
	/**
	 * Player receives upgrade 2: HEALTH RESTORATION <br>
	 * This upgrade is not the same for all player types, so it is overridden within individual store types
	 * <br>
	 * NOTE: The amount of health restored differs based on the <code>PlayerType</code>
	 * @param shopper – Player to receive item
	 */
	@Override
	public void use2(Player shopper)
	{
		Random rand = new Random();
		int addedHP = rand.nextInt(9) + 25;
		GUI.println("You have gained " + addedHP + " health points.");
		shopper.addHealth(addedHP);
		super.use2(shopper);
	}
	
	/**
	 * Player receives upgrade 3: WEAPONS UPGRADE <br>
	 * This upgrade is the not same for all player types, so it is overridden within individual store types
	 * <br>
	 * NOTE: Players may only upgrade their weapons once per game, and the upgrade differs based on <code>PlayerType</code>
	 * @param shopper – Player to receive item
	 */
	@Override
	public void use3(Player shopper)
	{
		GUI.println("");
		super.use3(shopper);
	}
}
