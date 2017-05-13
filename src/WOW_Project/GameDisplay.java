package WOW_Project;
import java.awt.*;        // Using AWT containers and components
import java.awt.event.*;  // Using AWT events classes and listener interfaces
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.net.URL;

//import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
//import javax.swing.text.DefaultCaret;
import javax.swing.JTextArea;
//import javax.swing.JViewport;
 
// An AWT GUI program inherits the top-level container java.awt.Frame
public class GameDisplay extends Frame implements ActionListener, WindowListener, KeyListener
{
      // This class acts as listener for ActionEvent and WindowEvent
      // A Java class can extend one superclass, but can implement multiple interfaces.
 
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
   public TextField playerIn;  // Declare a TextField component
   private JTextArea stats;  // Declare a TextField component
   private JTextArea gamePrompt;  // Declare a TextField component
   private JScrollPane gamePane;
   private JButton playerActionButton;    // Declare a Button component
   
   private String playerActionString;
   
   private Image myPicture1;
   private JLabel picLabel1;
   private Image myPicture2;
   private JLabel picLabel2;
   
   private String nextPrompt;
   private String recordPrompt;
   private String lastPromptLine;
   public volatile boolean click;
   
   public Stopwatch gameTimer;
   
   public final String Picture_GameAlert = "/WOW_Project/GameAlert_Icon.gif";
   public final String Picture_InputError = "/WOW_Project/InputError_Icon.gif";
   public final String Picture_GamePlayError = "/WOW_Project/GamePlayError_Icon.gif";
   public final String Picture_PlayerError = "/WOW_Project/PlayerError_Icon.gif";
   public final String Picture_UnknownError = "/WOW_Project/UnclassifiedError_Icon.gif";
   
   public final String Picture_BLANK = "/WOW_Project/BLANK_Icon.gif";
   public final String Picture_PEACE = "/WOW_Project/PEACE_Icon.gif";
   public final String Picture_PLAYER = "/WOW_Project/PLAYER_Icon.gif";
   
   public final String Picture_Mage = "/WOW_Project/Mage_Icon.gif";
   public final String Picture_Rogue = "/WOW_Project/Rogue_Icon.gif";
   public final String Picture_Hunter = "/WOW_Project/Hunter_Icon.gif";
   public final String Picture_King = "/WOW_Project/King_Icon.gif";
   public final String Picture_AdminPlayer = "/WOW_Project/Admin_Icon.gif";
   
   public final String Picture_DragonBoss = "/WOW_Project/DragonBoss_Icon.gif";
   public final String Picture_WarlockBoss = "/WOW_Project/WarlockBoss_Icon.gif";
   
   public final String Picture_RoadElf = "/WOW_Project/RoadElf_Icon.gif";
   public final String Picture_RoadDarkElf = "/WOW_Project/RoadDarkElf_Icon.gif";
   public final String Picture_RoadDragon = "/WOW_Project/RoadDragon_Icon.gif";
   public final String Picture_RoadElemental = "/WOW_Project/RoadElemental_Icon.gif";
   public final String Picture_RoadMage = "/WOW_Project/RoadMage_Icon.gif";
   public final String Picture_RoadDarkMage = "/WOW_Project/RoadDarkMage_Icon.gif";
   public final String Picture_RoadEvolvedDarkMage = "/WOW_Project/RoadEvolvedDarkMage_Icon.gif";
   public final String Picture_RoadSoldier = "/WOW_Project/RoadSoldier_Icon.gif";
   public final String Picture_RoadBeast = "/WOW_Project/RoadBeast_Icon.gif";
   public final String Picture_RoadOrc = "/WOW_Project/RoadOrc_Icon.gif";
   public final String Picture_RoadGiant = "/WOW_Project/RoadGiant.gif";
   public final String Picture_RoadRobber = "/WOW_Project/RoadGiant_Icon.gif";
   
   public final String Picture_Shop = "/WOW_Project/PlayerShop_Icon.gif";
   
   public final String Picture_DarkForest = "/WOW_Project/DarkForest_Icon.gif";
   
   public final String Picture_Gold = "/WOW_Project/Gold_Icon.gif";
   public final String Picture_LevelUp = "/WOW_Project/LevelUp_Icon.gif";
   
   private static Player player1;
 
   // Constructor to setup the GUI components and event handlers
   /**
    * Constructs main GameDisplay Window
    */
   public GameDisplay()
   {  
      setLayout(new FlowLayout());
     //setLayout(null);
      
      setBackground(Color.DARK_GRAY);
      
      nextPrompt = "";
      recordPrompt = "";
      lastPromptLine = "";
      click = false;
      gameTimer = new Stopwatch();
      
      gamePrompt = new JTextArea(20, 70) {
    		  /**
		 * 
		 */
		private static final long serialVersionUID = -6630600362121008788L;
			@Override
    	         public boolean getScrollableTracksViewportHeight() {
    	            return false;
    	         }
    	         @Override
    	         public boolean getScrollableTracksViewportWidth() {
    	            return false;
    	         }
    	      };
    	      
      gamePrompt.setEditable(false);
      gamePane = new JScrollPane(gamePrompt);
      gamePane.setMinimumSize(new Dimension(20, 70));
      //gamePane.setLocation(20, 20);
      add(gamePane);
      
      Label playerInLABEL = new Label("Player Input: ");
      //playerInLABEL.setLocation(20, 20);
      add(playerInLABEL);
      
      playerIn = new TextField("Type Here!", 10);
      playerIn.setEditable(true);
      //playerIn.setLocation(20, 20);
      add(playerIn);
      
      playerActionButton = new JButton("Go!");
      add(playerActionButton);
      playerActionButton.addActionListener(this);
      
      Label playerStatsLABEL = new Label("Player Stats: ");
      //playerInLABEL.setLocation(20, 20);
      add(playerStatsLABEL);
      
      stats = new JTextArea(20, 45);
      stats.setEditable(false);
      //stats.setLocation(20, 20);
      add(stats);
      
      URL imageurl1 = getClass().getResource(Picture_PLAYER); 
      myPicture1 = Toolkit.getDefaultToolkit().getImage(imageurl1);
//      picLabel1 = new JLabel(new ImageIcon( myPicture1 ));
//      picLabel1.setBounds(0,0,myPicture1.getWidth(null),myPicture1.getHeight(null));
      picLabel1 = new JLabel(new ImageIcon(myPicture1));
      //picLabel1.setLocation(20, 20);
      add(picLabel1);
      
      URL imageurl2 = getClass().getResource(Picture_PEACE); 
      myPicture2 = Toolkit.getDefaultToolkit().getImage(imageurl2);
//      picLabel2 = new JLabel(new ImageIcon( myPicture2 ));
//      picLabel2.setBounds(0,0,myPicture2.getWidth(null),myPicture2.getHeight(null));
      picLabel2 = new JLabel(new ImageIcon(myPicture2));
      //picLabel2.setLocation(20, 20);
      add(picLabel2);
 
      playerIn.addKeyListener(this);
      addWindowListener(this);
        // "super" Frame (source object) fires WindowEvent.
        // "super" Frame adds "this" object as a WindowEvent listener.
 
      setTitle("World Of CrandallCraft");
      setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Picture_PLAYER)));
      
      setSize(1175, 750);
      setVisible(true);
      //gamePane.getRootPane().setDefaultButton(playerActionButton);
      
      //pack();
      
      gameTimer.start();
   }
   
   /**
    * Sets player1 in the GameDisplay Window
    * @param p – Player1 (in the GameManager / PlayGame)
    */
   public static void setPlayer(Player p) { player1 = p; }
   
   /**
    * Clears the on-screen GamePlay record. </br>
    * (Resets <code>nextPrompt</code> to an empty String, then adds one line break.) </br>
    * <b>NOTE: Does not clear the hidden game record.</b>
    */
   public void clearGamePrompt()
   {
	   //nextPrompt = lastPromptLine;
	   nextPrompt = "\n";
	   recordprint("\n" + "GameManager Command Input Alert: The on-screen GamePlay record was cleared." + "\n");
	   updatePrompt();
   }
   
   /**
    * Reads and interprets player input from the main GameDisplay Window: <code>PlayerIn</code>
    */
   public void readInput()
   {
	   playerActionString = playerIn.getText();
	   recordPrompt += "\n" + "---" + "\n" + "**PLAYER INPUT:  " + playerCommand() + "\n" + "---" + "\n";
   }
   
   /**
    * Retrieves the current <code>Player Action</code>.
    * @return playerActionString
    */
   public String playerCommand()
   {
	   return playerActionString;
   }
   
   /**
    * Adds the given line to the on-screen GameDisplay Window: <code>nextPrompt</code>, then inserts a line break.
    * @param prompt – Next line to add to <code>nextPrompt</code>
    */
   public void println(String prompt)
   {
	   print(prompt + "\n");
   }
   
   
   public void hiddenprintln(String prompt)
   {
	   hiddenprint(prompt + "\n");
   }
   
   
   public void recordprintln(String prompt)
   {
	   recordprint(prompt + "\n");
   }
   
   /**
    * Inserts a line break to the end of the on-screen GameDisplay Window: <code>nextPrompt</code>.
    * @param prompt – Next line to add to <code>nextPrompt</code>
    */
   public void println()
   {
	   print("\n");
   }
   
   /**
    * Adds the given line to the on-screen GameDisplay Window: <code>nextPrompt</code>.
    * @param prompt – Next line to add to <code>nextPrompt</code>
    */
   public void print(String prompt)
   {
	   nextPrompt += prompt;
	   recordPrompt += prompt;
	   lastPromptLine = prompt;
	   updatePrompt();
   }
   
   public void hiddenprint(String prompt)
   {
	   nextPrompt += prompt;
	   updatePrompt();
   }
   
   public void recordprint(String prompt)
   {
	   recordPrompt += prompt;
	   updatePrompt();
   }
   
   public static void main(String[] args)
   {
//	   Player p1 = new Mage("Mage");
//	   new GameDisplay();
//	   setPlayer(p1);
	  boolean testPrint = false;
	  boolean testIcons = false;
	  boolean testStats = false;
	  //boolean testPlayers = false;
	  
	  Player p1 = new Mage("Mage");
      GameDisplay GUItest = new GameDisplay();
      setPlayer(p1);
      
      GUItest.println("–– GameEnvironment Alert: Initializing Test Setup Sequence ––");
      GUItest.println("GameEnvironment Console Message: 'Do not close the other GameDisplay Window during testing.'");
      boolean testChoice = true;
      while(testChoice)
      {
	      GUItest.println("\n" + "Enter test command  >>" + "\n\n");
	      
	      while(!GUItest.click) try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	      String testThis = GUItest.playerCommand();
	      if(testThis.equals("print")) { testPrint = true; testChoice = false; }
	      else if(testThis.equals("icons")) { testIcons = true; testChoice = false; }
	      else if(testThis.equals("stats")) { testStats = true; testChoice = false; }
	      //else if(testThis.equals("players")) testPlayers = true;
	      else if(testThis.equals("full"))
	      {
	    	  testPrint = true; testIcons = true; testStats = true; //testPlayers = true;
	    	  testChoice = false;
	      }
	      else
	      {
	    	  GUItest.println("ENVIRONMENT INPUT ERROR: INVALID TEST COMMAND");
	    	  GUItest.setIcon1(GUItest.Picture_InputError);
	    	  GUItest.setIcon2(GUItest.Picture_InputError);
	    	  GUItest.println("ENTER..."); GUItest.println("icons"); GUItest.println("print"); GUItest.println("stats"); GUItest.println("full"); GUItest.println();
	      }
      }
      
      
      while(testPrint)
      {
    	  GUItest.println("Enter message to print back, enter 'end' to conclude test." + "\n");
    	  while(!GUItest.click) try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); GUItest.println("Environment Error: Thread Delay error."); }
    	  String printMe = GUItest.playerCommand();
    	  GUItest.println(printMe);
    	  if(!GUItest.playerCommand().equals("end")) GUItest.println("Enter 'end' if your input matched the Environment Output above" + "\n");
    	  if(GUItest.playerCommand().equals("end")) { testPrint = false; GUItest.println("GameEnvironment Result: PRINT TEST CONFIRMED" + "\n"); }
      }
      if(testStats)
      {
    	  GUItest.println("Beginning playerStats test." + "\n");
    	  
    	  p1.setNewName("PLAYER NAME (test)");
    	  p1.levelUp();
    	  p1.heal();
    	  p1.takeHealth(40);
    	  p1.addGold(49);
    	  p1.addKills(4);
    	  p1.addRealm("Test Realm");
    	  GUItest.println("Check below for correct Stats confirmation" + "\n");
    	  GUItest.println(p1.getStatsString());
    	  
    	  GUItest.println("Check if the STATS pane below matches the STATS listing above");
    	  
    	  GUItest.println("GameEnvironment Result: STATS TEST COMPLETE" + "\n");
    	  testStats = false;
      }
      if(testIcons)
      {
    	  GUItest.println("Testing Icons." + "\n");
    	  try
    	  {
	    	  GUItest.println("Begin Icon Switch Test" + "\n");
	    	  GUItest.setIcon1(GUItest.Picture_WarlockBoss);
	    	  try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	    	  GUItest.setIcon2(GUItest.Picture_Mage);
	    	  try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	    	  GUItest.setIcon1(GUItest.Picture_InputError);
	    	  try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	    	  GUItest.setIcon2(GUItest.Picture_BLANK);
	    	  try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	    	  GUItest.setIcon1(GUItest.Picture_LevelUp);
	    	  GUItest.setIcon2(GUItest.Picture_LevelUp);
	    	  try{ Thread.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
	    	  GUItest.println("GameEnvironment Result: ICONS TEST CONFIRMED");
	    	  GUItest.println("(both icons should match the level up alert icon)" + "\n");
    	  }
    	  catch(Exception e)
    	  {
    		  e.printStackTrace();
    		  GUItest.setIcon1(GUItest.Picture_UnknownError);
	    	  GUItest.setIcon2(GUItest.Picture_UnknownError);
	    	  GUItest.println("Environment Error: Icon test error.");
    	  }
    	  
    	  testIcons = false;
      }
      
      GUItest.println("-- TESTING COMPLETE, END GAME ENVIRONMENT --");
   }
   
   public void setIcon1(String newIcon)
   {
	   try
	   {
		   URL imageurl1 = getClass().getResource(newIcon);
		   picLabel1.setIcon(new ImageIcon(imageurl1));
	   }
	   catch(NullPointerException e)
	   {
		   println("GameEnvironment ERROR: Unregistered IconURL grab failure (GameDisplay.setIcon1())");
	   }
   }
   
   public void setIcon2(String newIcon)
   {
	   try
	   {
	   		URL imageurl2 = getClass().getResource(newIcon);
	   		picLabel2.setIcon(new ImageIcon(imageurl2));
	   }
	   catch(NullPointerException e)
	   {
		   println("GameEnvironment ERROR: Unregistered IconURL grab failure (GameDisplay.setIcon2())");
	   }
   }
   
   public void updatePrompt()
   {
	   click = false;
	   gamePrompt.setText(nextPrompt);
	   gamePane.getViewport().setViewPosition(new Point(0, gamePrompt.getSize().height));
	   
	   if(player1 == null) stats.setText("GAME ALERT: No Stats Available Yet.");
	   else if(GameManager.inBossBattle) stats.setText(player1.getBattleStatsString());
	   else stats.setText(player1.getStatsString());
   }
   
   public double getGameTime()
   {
	   return gameTimer.getElapsedTime();
   }
   
   public String retrieveGameRecord()
   {
	   return recordPrompt;
   }
   
   public void playerResponds()
   {
	   readInput();
	   click = true;
	   gamePrompt.setText(nextPrompt);
	   playerIn.setText("");
   }
   
   /* ActionEvent handler */
   @Override
   public void actionPerformed(ActionEvent evt)
   {
	  playerResponds();
   }
 
   /* WindowEvent handlers */
   // Called back upon clicking close-window button
   @Override
   public void windowClosing(WindowEvent evt)
   {
      System.exit(0);  // Terminate the program
   }
 
   // Not Used, but need to provide an empty body to compile.
   @Override public void windowOpened(WindowEvent evt) { }
   @Override public void windowClosed(WindowEvent evt) { }
   @Override public void windowIconified(WindowEvent evt) { }
   @Override public void windowDeiconified(WindowEvent evt) { }
   @Override public void windowActivated(WindowEvent evt) { }
   @Override public void windowDeactivated(WindowEvent evt) { }

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			playerResponds();
		}
		//VK_ENTER
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyTyped(KeyEvent e) { }
}