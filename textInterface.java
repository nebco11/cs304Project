
// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;



/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 
public class textInterface implements ActionListener
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
    //TODO Finished just needs to be tested and error handled
    private void insertBranch()
    {
        boolean insertBack;
        insertBack = false;
        
    try
        {
        while (!insertBack)
        {
            System.out.print("Welcome to the insert menu! Please select one of the tables below to insert into!\n");
            System.out.print("1.   ------Government\n");
            System.out.print("2.   ------Support_Branch\n");
            System.out.print("3.   ------Client\n");
            System.out.print("4.   ------Support\n");
            System.out.print("5.   ------isGiven\n");
            System.out.print("6.   ------Finance_Office\n");
            System.out.print("7.   ------Partners\n");
            System.out.print("8.   ------Taxpayer\n");
            System.out.print("9.   ------Monetary_Assistance\n");
            System.out.print("10.  ------Employment_Training\n");
            System.out.print("11.  ------Facilitate\n");

            System.out.print("12.  ------Back to main menu\n ");
            
            
            choice = Integer.parseInt(in.readLine());
            System.out.println(" ");
            switch(choice)
            {
                case 1:  insertGovernment(); break;
                case 2:  insertSupport_Branch(); break;
                case 3:  insertClient(); break;
                case 4:  insertSupport(); break;
                case 5:  insertisGiven(); break;
                case 6:  insertFinance_Office(); break;
                case 7:  insertPartners(); break;
                case 8:  insertTaxpayer(); break;
                case 9:  insertMonetary_Assistance(); break;
                case 10:  insertEmployment_Training(); break;
                case 11:  insertFacilitate(); break;

                case 12:  insertBack = true;
                    
            }
        }
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
    
    private void insertGovernment() {
        String  department;
        String head_of_dep;
        String division;
        PreparedStatement  ps;
    
	try
	{
	  ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?)");
        
        System.out.print("\nGovernment department: ");
        department = in.readLine();
        ps.setString(1, department);
        System.out.print("\nGovernment head of deparment: ");
        head_of_dep = in.readLine();
        ps.setString(2, head_of_dep);
        System.out.print("\nGovernment division: ");
        division = in.readLine();
        ps.setString(3, division);
	    ps.executeUpdate();

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
    private void insertSupport_Branch() {
        String address;
        String city;
        String branchmanager;
        String g_department;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?)");
            
            System.out.print("\nSupport Branch address: ");
            address = in.readLine();
            ps.setString(1, address);
            System.out.print("\nSupport Branch city: ");
            city = in.readLine();
            ps.setString(2, city);
            System.out.print("\nSupport Branch manager: ");
            branchmanager = in.readLine();
            ps.setString(3, branchmanager);
            System.out.print("\nSupport Branch Government deparment (can't be null): ");
            g_department = in.readLine();
            ps.setString(4, g_department);
            
    
            ps.executeUpdate();
            
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
    private void insertClient() {
        String nameC;
        String addressC;
        String phoneC;
        int sinC;
        String sb_city;
        String sb_address;
        //date d_requested; this is declared below
        String t_requested;
        PreparedStatement  ps;
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?,?,?,?)");
            
            System.out.print("\nClient name: ");
            nameC = in.readLine();
            ps.setString(1, nameC);
            
            System.out.print("\nClient address: ");
            addressC = in.readLine();
            ps.setString(2, addressC);
            
            System.out.print("\nClient phone: ");
            phoneC = in.readLine();
            ps.setString(3, phoneC);
            
            System.out.print("\nClient SIN: ");
            sinC = Integer.parseInt(in.readLine());
            ps.setInt(4, sinC);
            
            System.out.print("\nCity of Client's support branch: ");
            sb_city = in.readLine();
            ps.setString(5, sb_city);
            
            System.out.print("\nAddress of Client's support branch: ");
            sb_address = in.readLine();
            ps.setString(6, sb_address);
            
            //TODO this might not be working...
            //UPDATE is working!
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            System.out.print("\nDate requested: ");
            java.util.Date dateUtil = sdf1.parse(in.readLine());
            java.sql.Date d_requested = new java.sql.Date(dateUtil.getTime());
            
            ps.setDate(7, d_requested);
            
            System.out.print("\nTime requested: ");
            t_requested = in.readLine();
            ps.setString(8, t_requested);
            
            
            ps.executeUpdate();
            
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
    private void insertSupport() {
        int supportID;
        float budget_given; //not null or error in setFloat
        String government_department; //not null
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?)");
            
            System.out.print("\nSupport ID: ");
            supportID = Integer.parseInt(in.readLine());
            ps.setInt(1, supportID);
            
            System.out.print("\nBudget Given: ");
            budget_given = Float.valueOf(in.readLine());
            ps.setFloat(2, budget_given);
            
            System.out.print("\nGovernment Department: ");
            branchmanager = in.readLine();
            ps.setString(3, branchmanager);
            
            ps.executeUpdate();
            
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
    private void insertisGiven() {
        int SINisGiven;
        int supIDisGiven;
        //DATE declared below
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?)");
            
            System.out.print("\nSIN: ");
            SINisGiven = Integer.parseInt(in.readLine());
            ps.setInt(1, SINisGiven);
            
            System.out.print("\nSupport ID: ");
            supIDisGiven = Integer.parseInt(in.readLine());
            ps.setInt(2, supIDisGiven);
            
            
            //TODO this might not be working...
            //UPDATE is working!
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            System.out.print("\nDate given: ");
            java.util.Date dateUtil = sdf1.parse(in.readLine());
            java.sql.Date d_given = new java.sql.Date(dateUtil.getTime());
            ps.setDate(3, d_given);
            
            ps.executeUpdate();
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
    private void insertFinance_Office() {
        String  g_department;
        String fo_city;
        String fo_address;
        String fo_omanager;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?)");
            
            System.out.print("\nGovernment department: ");
            g_department = in.readLine();
            ps.setString(1, g_department);
            
            System.out.print("\nCity: ");
            fo_city = in.readLine();
            ps.setString(2, fo_city);
            
            System.out.print("\nAddress: ");
            fo_address = in.readLine();
            ps.setString(3, fo_address);
            
            System.out.print("\nOffice manager: ");
            fo_omanager = in.readLine();
            ps.setString(4, fo_omanager);
            
            ps.executeUpdate();
            
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
    private void insertPartners() {
        String  g_department; //not null
        String comp_name;
        String industry;
        int partnerID;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?)");
            
            System.out.print("\nGovernment department: ");
            g_department = in.readLine();
            ps.setString(1, g_department);
            
            System.out.print("\nCompany name: ");
            comp_name = in.readLine();
            ps.setString(2, comp_name);
            
            System.out.print("\nIndustry: ");
            industry = in.readLine();
            ps.setString(3, industry);
            
            System.out.print("\nOffice manager: ");
            partnerID = Integer.parseInt(in.readLine());
            ps.setInt(4, partnerID);
            
            ps.executeUpdate();
            
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
    private void insertTaxpayer() {
        int sintp;
        String g_department; //not null
        String name;
        String addressT;
        String phoneT;
        float amount_paid;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?,?)");
            
            
            System.out.print("\nSIN: ");
            sintp = Integer.parseInt(in.readLine());
            ps.setInt(1, sintp);
            
            System.out.print("\nGovernment department: ");
            g_department = in.readLine();
            ps.setString(2, g_department);
            
            System.out.print("\nName: ");
            name = in.readLine();
            ps.setString(3, name);
            
            System.out.print("\nAddress: ");
            addressT = in.readLine();
            ps.setString(4, addressT);
            
            System.out.print("\nPhone: ");
            phoneT = in.readLine();
            ps.setString(5, phoneT);
            
            System.out.print("\nAmount paid: ");
            amount_paid = Float.valueOf(in.readLine());
            ps.setFloat(6, amount_paid);
            
            ps.executeUpdate();
            
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
    private void insertMonetary_Assistance() {
        int  supID;
        float amount;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?)");
            
            System.out.print("\nSupport ID: ");
            supID = Integer.parseInt(in.readLine());
            ps.setInt(1, supID);
            
            System.out.print("\nAmount in $: ");
            amount = Float.valueOf(in.readLine());
            ps.setFloat(2, amount);
            
            
            ps.executeUpdate();
            
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
    private void insertEmployment_Training() {
        int  supID;
        String type;
        String instructor;
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?)");
            
            System.out.print("\nSupport ID: ");
            supID = Integer.parseInt(in.readLine());
            ps.setInt(1, supID);
            
            System.out.print("\nType: ");
            type = in.readLine();
            ps.setString(2, type);
            
            System.out.print("\nInstructor: ");
            fo_address = in.readLine();
            ps.setString(3, fo_address);
            
            ps.executeUpdate();
            
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
    private void insertFacilitate() {
        int  partnerID;
        int supID;
        
        PreparedStatement  ps;
        
        try
        {
            ps = con.prepareStatement("INSERT INTO branch VALUES (?,?)");
            
            System.out.print("\nPartner ID: ");
            partnerID = Integer.parseInt(in.readLine());
            ps.setInt(1, partnerID);
            
            System.out.print("\nSupport ID: ");
            supID = Integer.parseInt(in.readLine());
            ps.setInt(2, supID);
            
            ps.executeUpdate();
            
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
