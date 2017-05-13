package WOW_Project;

import java.util.Random;

public class RoadDarkMage extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadDarkMage()
	{
		super("DarkMage");
		setHP(40);
		super.setEnemyType("Dark Mage");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(18) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Dark Mage attacked you with a huge fireball. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage2 = rand.nextInt(11) + 1;
			target.takeHealth(attackDamage2);
			GUI.println("The Dark Mage hit you with an energy blast. You lost " + attackDamage2 + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(15) + 1;
			target.addGold(coinsGained);
			GUI.println("The Dark Mage fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}
}
