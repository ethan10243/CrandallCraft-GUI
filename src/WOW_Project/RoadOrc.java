package WOW_Project;

import java.util.Random;

public class RoadOrc extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadOrc()
	{
		super("Orc");
		int orcHealth = rand.nextInt(57) + 30;
		setHP(orcHealth);
		super.setEnemyType("Orc");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(5);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(10) + 30;
			target.takeHealth(attackDamage);
			GUI.println("The Orc attacked you with an axe. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1 || attackChance == 3 || attackChance == 4)
		{
			int attackDamage2 = rand.nextInt(10) + 5;
			target.takeHealth(attackDamage2);
			GUI.println("The Orc launched an arrow at you. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(5) + 2;
			target.addGold(coinsGained);
			GUI.println("The Orc fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}

}
