package WOW_Project;

import java.util.Random;

public class RoadRobber extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadRobber()
	{
		super("Robber");
		setHP(15);
		super.setEnemyType("Robber");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(6) + 2;
			target.takeHealth(attackDamage);
			GUI.println("The Robber hit you with his club. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int coinsTaken = rand.nextInt(3) + 9;
			target.takeGold(coinsTaken);
			GUI.println("The Robber knocked you out and robbed you. You lost " + coinsTaken + " gold.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(3) + 1;
			target.addGold(coinsGained);
			GUI.println("The Robber fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
}
