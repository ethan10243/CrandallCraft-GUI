package WOW_Project;

import java.util.Random;

public class RoadEvolvedDarkMage extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadEvolvedDarkMage()
	{
		super("Evolved Dark Mage");
		setHP(52);
		super.setEnemyType("Evolved Dark Mage");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(13) + 20;
			target.takeHealth(attackDamage);
			GUI.println("The Dark Mage attacked you with a volley of fireballs. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(12) + 10;
			target.takeHealth(attackDamage2);
			GUI.println("The Dark Mage hit you with a dark curse. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int attackDamage2 = rand.nextInt(10) + 2;
			target.takeHealth(attackDamage2);
			GUI.println("The Dark Mage hit you with its staff. You lost " + attackDamage2 + " health points.");
		}
	}
}
