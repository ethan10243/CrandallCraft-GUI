package WOW_Project;

import java.util.Random;

public class WarlockBoss extends RoadEnemy
{
	private GameDisplay GUI = PlayGame.GUI;
	
	Random rand = new Random();
	private int bossHP;
	
	public WarlockBoss()
	{
		super("Powerful Warlock");
		setEnemyType("Warlock Boss");
		int hpSet = rand.nextInt(41) + 140;
		bossHP = hpSet;
		GUI.println("A Boss has emerged to battle you. It is a powerful Warlock Boss.");
	}
	
	public void bossAttack(Player target)
	{
		int attackNumber = rand.nextInt(11);
		if(attackNumber == 0 && bossHP > 50)
		{
			int pChance = rand.nextInt(5);
			target.takeBattleHP(2);
			GUI.println("The warlock barely hit you with a daggar. You only lost 2 HP. (Your HP: " + target.getBattleHP() + ")");
			if(pChance == 0) { target.warlockSetP(true); }
		}
		else if(attackNumber == 0 || attackNumber == 1 || attackNumber == 2 || attackNumber == 3 || attackNumber == 4 || attackNumber == 5)
		{
			int damageAmount = rand.nextInt(4) + 13;
			target.takeBattleHP(damageAmount);
			GUI.println("The warlock stunned and hit you. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
		}
		else if(attackNumber == 6 || attackNumber == 7 || attackNumber == 8 || attackNumber == 9)
		{
			int damageAmount = rand.nextInt(8) + 28;
			target.takeBattleHP(damageAmount);
			GUI.println("The warlock launched a dark energy blast at you. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
		}
		else if(attackNumber == 10)
		{
			int damageAmount = rand.nextInt(10) + 45;
			target.takeBattleHP(damageAmount);
			GUI.println("The warlock summonded a powerful elemental to attack you. You lost " + damageAmount + "HP. (Your HP: " + target.getBattleHP() + ")");
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
