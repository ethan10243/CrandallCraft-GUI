package WOW_Project;

import java.util.Random;

public class RoadElemental extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadElemental()
	{
		super("Elemental");
		setHP(55);
		super.setEnemyType("Elemental");
	}
	
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(5);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(15) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Elemental shot you with a ice-shard. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage = rand.nextInt(15) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Elemental attacked you with a sandstorm. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 2)
		{
			int attackDamage = rand.nextInt(15) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Elemental knocked you over with a air blast. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 3)
		{
			int attackDamage = rand.nextInt(15) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Elemental attacked you with a fireball. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 4)
		{
			int coinsGained = rand.nextInt(30) + 1;
			target.addGold(coinsGained);
			GUI.println("The Elemental fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
	
}
