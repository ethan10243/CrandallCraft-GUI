package WOW_Project;

import java.util.Random;

public class RoadMage extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadMage()
	{
		super("Mage");
		setHP(30);
		super.setEnemyType("Mage");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(12) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Mage attacked you with a fireball. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(6) + 1;
			target.takeHealth(attackDamage2);
			GUI.println("The Mage hit you with a curse. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(10) + 1;
			target.addGold(coinsGained);
			GUI.println("The Mage fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
	
}
