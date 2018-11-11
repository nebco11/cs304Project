
// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class branch implements ActionListener
{
    // command line reader 
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;


    /*
     * constructs login window and loads JDBC driver
     */ 
    public branch()
    {
      mainFrame = new JFrame("User Login");

      JLabel usernameLabel = new JLabel("Enter username: ");
      JLabel passwordLabel = new JLabel("Enter password: ");

      usernameField = new JTextField(10);
      passwordField = new JPasswordField(10);
      passwordField.setEchoChar('*');

      JButton loginButton = new JButton("Log In");

      JPanel contentPane = new JPanel();
      mainFrame.setContentPane(contentPane);


      // layout components using the GridBag layout manager

      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();

      contentPane.setLayout(gb);
      contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      // place the username label 
      c.gridwidth = GridBagConstraints.RELATIVE;
      c.insets = new Insets(10, 10, 5, 0);
      gb.setConstraints(usernameLabel, c);
      contentPane.add(usernameLabel);

      // place the text field for the username 
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.insets = new Insets(10, 0, 5, 10);
      gb.setConstraints(usernameField, c);
      contentPane.add(usernameField);

      // place password label
      c.gridwidth = GridBagConstraints.RELATIVE;
      c.insets = new Insets(0, 10, 10, 0);
      gb.setConstraints(passwordLabel, c);
      contentPane.add(passwordLabel);

      // place the password field 
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.insets = new Insets(0, 0, 10, 10);
      gb.setConstraints(passwordField, c);
      contentPane.add(passwordField);

      // place the login button
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.insets = new Insets(5, 10, 10, 10);
      c.anchor = GridBagConstraints.CENTER;
      gb.setConstraints(loginButton, c);
      contentPane.add(loginButton);

      // register password field and OK button with action event handler
      passwordField.addActionListener(this);
      loginButton.addActionListener(this);

      // anonymous inner class for closing the window
      mainFrame.addWindowListener(new WindowAdapter() 
      {
	public void windowClosing(WindowEvent e) 
	{ 
	  System.exit(0); 
	}
      });

      // size the window to obtain a best fit for the components
      mainFrame.pack();

      // center the frame
      Dimension d = mainFrame.getToolkit().getScreenSize();
      Rectangle r = mainFrame.getBounds();
      mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

      // make the window visible
      mainFrame.setVisible(true);

      // place the cursor in the text field for the username
      usernameField.requestFocus();

      try 
      {
	// Load the Oracle JDBC driver
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		// may be oracle.jdbc.driver.OracleDriver as of Oracle 11g
      }
      catch (SQLException ex)
      {
	System.out.println("Message: " + ex.getMessage());
	System.exit(-1);
      }
    }


    /*
     * connects to Oracle database named ug using user supplied username and password
     */ 
    private boolean connect(String username, String password)
    {
      String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

      try 
      {
	con = DriverManager.getConnection(connectURL,username,password);

	System.out.println("\nConnected to Oracle!");
	return true;
      }
      catch (SQLException ex)
      {
	System.out.println("Message: " + ex.getMessage());
	return false;
      }
    }


    /*
     * event handler for login window
     */ 
    public void actionPerformed(ActionEvent e) 
    {
	if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
	{
	  // if the username and password are valid, 
	  // remove the login window and display a text menu 
	  mainFrame.dispose();
          showMenu();     
	}
	else
	{
	  loginAttempts++;
	  
	  if (loginAttempts >= 3)
	  {
	      mainFrame.dispose();
	      System.exit(-1);
	  }
	  else
	  {
	      // clear the password
	      passwordField.setText("");
	  }
	}             
                    
    }


    /*
     * displays simple text interface
     */ 
    private void showMenu()
    {
	int choice;
	boolean quit;

	quit = false;
	
	try 
	{
	    // disable auto commit mode
	    con.setAutoCommit(false);

	    while (!quit)
	    {
        System.out.print("\n\nWelcome to the interactive support system database! \n\n");

		System.out.print("\n\nPlease choose one of the following by using the number as input: \n");
        System.out.print("=====================Basic Commands=====================\n");
		System.out.print("1.   Insert branch\n");
		System.out.print("2.   Delete branch\n");
		System.out.print("3.   Update branch\n");
		System.out.print("4.   Show branch\n");
        System.out.print("5.   Quit\n ");

		System.out.print("========================Queries========================\n");
        //System.out.print("6.   TODO ");
        //System.out.print("7.   TODO ");
        //System.out.print("8.   TODO ");
        //System.out.print("9.   TODO ");
        //System.out.print("10.  TODO ");
        //System.out.print("11.  TODO ");
        //System.out.print("12.  TODO ");
        //System.out.print("13.  TODO ");
        //System.out.print("14.  TODO ");
        //System.out.print("15.  TODO ");
        //System.out.print("16.  TODO ");



		choice = Integer.parseInt(in.readLine());
		
		System.out.println(" ");

		switch(choice)
		{
		    case 1:  insertBranch(); break;
		    case 2:  deleteBranch(); break;
		    case 3:  updateBranch(); break;
		    case 4:  showBranch(); break;
		    case 5:  quit = true;
            //case 6:  TODO
            //case 7:  TODO
            //case 8:  TODO
            //case 9:  TODO
            //case 10: TODO
            //case 11: TODO
            //case 12: TODO
            //case 13: TODO
            //case 14: TODO
            //case 15: TODO
            //case 16: TODO
		}
	    }

	    con.close();
            in.close();
	    System.out.println("\nGood Bye!\n\n");
	    System.exit(0);
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");

	    try
	    {
		con.close();
		System.exit(-1);
            //TODO error handling so it doesn't close on error
            //needed for a good demo
	    }
	    catch (SQLException ex)
	    {
		 System.out.println("Message: " + ex.getMessage());
	    }
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	}
    }


    /*
     * inserts a branch
     */
    //TODO Finish
    private void insertBranch()
    {
	//TODO params for insert
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
	//TODO body for insert

	  ps.executeUpdate();

	  // commit work 
	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	    try 
	    {
		// undo the insert
		con.rollback();	
	    }
	    catch (SQLException ex2)
	    {
		System.out.println("Message: " + ex2.getMessage());
            //TODO error handling
		System.exit(-1);
	    }
	}
    }


    /*
     * deletes a branch
     */
    //TODO Finish
    private void deleteBranch()
    {
	//TODO params for delete
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
	 //TODO body for delete
	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());

            try 
	    {
		con.rollback();	
	    }
	    catch (SQLException ex2)
	    {
		System.out.println("Message: " + ex2.getMessage());
            //TODO error handling
		System.exit(-1);
	    }
	}
    }
    

    /*
     * updates the name of a branch
     */
    //TODO Finish
    private void updateBranch()
    {
	//TODO params for update
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
	 //TODO body for update

	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	    
	    try 
	    {
		con.rollback();	
	    }
	    catch (SQLException ex2)
	    {
		System.out.println("Message: " + ex2.getMessage());
            //TODO error handling
		System.exit(-1);
	    }
	}	
    }

    
    /*
     * display information about branches
     */ 
    //TODO finish
    private void showBranch()
    {
	//TODO params for show
	Statement  stmt;
	ResultSet  rs;
	   
	try
	{
	  System.out.println(" ");
	  while(rs.next())
	  {
          //TODO finish while loop for results
	      // for display purposes get everything from Oracle 
	      // as a string
	      // simplified output formatting; truncation may occur
	  }
 
	  // close the statement; 
	  // the ResultSet will also be closed
	  stmt.close();
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	}	
    }
    
    
    //TODO all of following
    private void query6() {
        
    }
    private void query7() {
        
    }
    private void query8() {
        
    }
    private void query9() {
        
    }
    private void query10() {
        
    }
    private void query11() {
        
    }
    private void query12() {
        
    }
    private void query13() {
        
    }
    private void query14() {
        
    }
    private void query15() {
        
    }
    private void query16() {
        
    }
    
    
 
    public static void main(String args[])
    {
        //TODO main
      branch b = new branch();
    }
}

