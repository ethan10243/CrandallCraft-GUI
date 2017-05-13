package WOW_Project;

import java.util.Random;

public class RoadGiant extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadGiant()
	{
		super("Giant");
		setHP(71);
		super.setEnemyType("Giant");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(20) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Giant smashed into you. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(6) + 1;
			target.takeHealth(attackDamage2);
			GUI.println("The Giant hit you. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(25) + 1;
			target.addGold(coinsGained);
			GUI.println("The Giant fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
}
