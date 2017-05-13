package WOW_Project;

import java.util.Random;

public class RoadSoldier extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadSoldier()
	{
		super("Soldier");
		setHP(80);
		super.setEnemyType("Rebel Soldier");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(16) + 1;
			int coinsTaken = rand.nextInt(9) + 1;
			target.takeHealth(attackDamage);
			target.takeGold(coinsTaken);
			GUI.println("The Soldier knocked you over with his sword, robbed you, and fled.");
			GUI.println("You lost " + attackDamage + " health points and " + coinsTaken + " gold.");
			setFlee(true);
			setHP(0);
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(4) + 1;
			target.takeHealth(attackDamage2);
			GUI.println("The Soldier ran into you. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(14) + 1;
			target.addGold(coinsGained);
			GUI.println("The Soldier fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
	
}
