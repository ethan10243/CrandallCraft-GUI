package WOW_Project;

import java.util.Random;

public class RoadDarkElf extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadDarkElf()
	{
		super("Dark Elf");
		setHP(60);
		super.setEnemyType("Dark Elf");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(3);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(18) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Dark Elf stabbed you with a spear. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int attackDamage = rand.nextInt(24) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Dark Elf shot you with a volley of arrows. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 2)
		{
			int coinsGained = rand.nextInt(12) + 1;
			target.addGold(coinsGained);
			GUI.println("The Dark Elf fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}

}
