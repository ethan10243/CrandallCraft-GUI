package WOW_Project;

import java.util.Random;

public class RoadDragon extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadDragon()
	{
		super("Dragon");
		setHP(100);
		super.setEnemyType("Small Dragon");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(5);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(15) + 15;
			target.takeHealth(attackDamage);
			GUI.println("The Small Dragon hit you with a fire blast. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(5) + 15;
			target.takeGold(attackDamage2);
			GUI.println("The Small Dragon hit you with its tail. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsTaken = rand.nextInt(3) + 3;
			target.takeGold(coinsTaken);
			GUI.println("The Small Dragon flew away with some of your gold. You have lost " + coinsTaken + " gold.");
			setFlee(true);
			setHP(0);
		}
		if(attackChance == 3)
		{
			int coinsTaken = rand.nextInt(3) + 3;
			int healthLost = rand.nextInt(10);
			target.takeGold(coinsTaken);
			target.takeHealth(healthLost);
			GUI.println("The Small Dragon hit you with its tail, then flew away with some of your gold.");
			GUI.println("You have lost " + healthLost + " health points and " + coinsTaken + " coins.");
			setFlee(true);
			setHP(0);
		}
		if(attackChance == 4)
		{
			int coinsGained = rand.nextInt(15) + 10;
			target.addGold(coinsGained);
			GUI.println("The Small Dragon flew away, and left behind some gold. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
}
