package WOW_Project;

import java.util.Random;

public class DragonBoss extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	Random rand = new Random();
	private int bossHP;
	
	public DragonBoss()
	{
		super("Dragon Boss");
		setEnemyType("Dragon Boss");
		int hpSet = rand.nextInt(32) + 190;
		bossHP = hpSet;
		GUI.println("A Boss has emerged to battle you. It is a large Dragon Boss.");
	}
	
	public void bossAttack(Player target)
	{
		int attackNumber = rand.nextInt(11);
		if(attackNumber == 0)
		{
			target.takeBattleHP(1);
			GUI.println("The large Dragon attempted to hit you, but you dodged. You only lost 1 HP. (Your HP: " + target.getBattleHP() + ")");
		}
		if(attackNumber == 1 || attackNumber == 2 || attackNumber == 3 || attackNumber == 4 || attackNumber == 5)
		{
			int damageAmount = rand.nextInt(10) + 20;
			target.takeBattleHP(damageAmount);
			GUI.println("The dragon stabbed you with its claws. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
		}
		if(attackNumber == 6 || attackNumber == 7 || attackNumber == 8 || attackNumber == 9)
		{
			int damageAmount = rand.nextInt(15) + 20;
			target.takeBattleHP(damageAmount);
			GUI.println("The dragon launched a huge fireball at you. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
		}
		if(attackNumber == 10)
		{
			int damageAmount = rand.nextInt(11) + 30;
			target.takeBattleHP(damageAmount);
			GUI.println("The dragon knocked you over with its wings. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
		}
	}
	
	public void addHP(int points) { bossHP += points; }
	public void takeHP(int points) { bossHP -= points; if(bossHP < 1) bossHP = 0; }
	public int getHP()
	{
		if(bossHP < 1) return 0;
		else return bossHP;
	}
	public boolean checkBossHP() { return (bossHP < 1); }
}
