package WOW_Project;

import java.util.Random;

public class RoadBeast extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	private Random rand = new Random();
	
	public RoadBeast()
	{
		super("Magical Beast");
		setHP(30);
		super.setEnemyType("Magical Beast");
	}
	public void enemyAttack(Player target)
	{
		int attackChance = rand.nextInt(2);
		if(attackChance == 0)
		{
			int attackDamage = rand.nextInt(32) + 1;
			target.takeHealth(attackDamage);
			GUI.println("The Beast ran into you. You lost " + attackDamage + " health points.");
		}
		if(attackChance == 1)
		{
			int coinsGained = rand.nextInt(32) + 1;
			target.addGold(coinsGained);
			GUI.println("The Beast fled. You have gained " + coinsGained + " gold.");
			setFlee(true);
			setHP(0);
		}
	}

}

//if(enemy.getHP() < 1 || enemy.getFlee() == true) fleeStatus = true;
//ENEMY ATTACKS:
//if(((enemy.getHP() < 1) && (player1.getHP() > 1))) { GUI.println("Congratulations! You have won againt your enemy! \n"); player1.addKills(1); }
//else if(fleeStatus == true && enemy.getHP() > 0) GUI.println("");
//else if(fleeStatus == true) { GUI.println("Congratulations! You have won againt your enemy! \n"); player1.addKills(1); }
//else if(enemy.getHP() > 0 && fleeStatus == false) { enemy.enemyAttack(player1); GUI.println("  (Player HP: " + player1.getHP() + ") \n"); }
