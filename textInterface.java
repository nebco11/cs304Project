
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
    public textInterface()
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
        System.out.print("1.   Insert Menu\n"); //DONE: NEEDS ERROR HANDLING
        System.out.print("2.   Delete Menu\n"); //DONE: NEEDS ERROR HANDLING
		System.out.print("3.   Show Menu\n");
        System.out.print("4.   Quit\n ");

		System.out.print("========================User Menu========================\n");
        System.out.print("5.   Government User Menu\n");
        System.out.print("6.   Taxpayer User Menu\n");
        System.out.print("7.   Support Branch User Menu\n");
        System.out.print("8.   Client User Menu\n");
        System.out.print("9.   Instructor User Menu\n");


        //System.out.print("8.   TODO ");
        //System.out.print("9.  TODO ");
        //System.out.print("10.  TODO ");

        //System.out.print("11.  TODO ");
        //System.out.print("12.  TODO ");
        //System.out.print("13.  TODO ");
        //System.out.print("14.  TODO ");
        //System.out.print("15.  TODO ");



		choice = Integer.parseInt(in.readLine());
		System.out.println(" ");
		switch(choice)
		{
		    case 1:  insert(); break;
		    case 2:  delete(); break;
		    case 3:  show(); break;
            case 5:  governmentQ(); break;
            case 6:  taxpayerQ(); break;
            case 7:  sbQ(); break;
            case 9:  instructorQ(); break;
            case 8:  clientQ(); break;

            case 4:  quit = true;
            //case 10: TODO
            //case 11: TODO
            //case 12: TODO
            //case 13: TODO
            //case 14: TODO
            //case 15: TODO
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
    private void insert()
    {
        boolean insertBack;
        int choice;
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

        }

    private void insertGovernment() {
        String  department;
        String head_of_dep;
        String division;
        PreparedStatement  ps;

	try
	{
	  ps = con.prepareStatement("INSERT INTO Government VALUES (?,?,?)");

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
            ps = con.prepareStatement("INSERT INTO Support_Branch VALUES (?,?,?,?)");

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
            ps = con.prepareStatement("INSERT INTO Client VALUES (?,?,?,?,?,?,?,?)");

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
            try {
            java.util.Date dateUtil = sdf1.parse(in.readLine());
            java.sql.Date d_requested = new java.sql.Date(dateUtil.getTime());
                ps.setDate(7, d_requested);
            }
            catch (Exception pe) {
                System.out.println("ParseException!");
            }



            System.out.print("\nType of aid requested: ");
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
            ps = con.prepareStatement("INSERT INTO Support VALUES (?,?,?)");

            System.out.print("\nSupport ID: ");
            supportID = Integer.parseInt(in.readLine());
            ps.setInt(1, supportID);

            System.out.print("\nBudget Given: ");
            budget_given = Float.valueOf(in.readLine());
            ps.setFloat(2, budget_given);

            System.out.print("\nGovernment Department: ");
            government_department = in.readLine();
            ps.setString(3, government_department);

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
            ps = con.prepareStatement("INSERT INTO isGiven VALUES (?,?,?)");

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
            try{


            java.util.Date dateUtil = sdf1.parse(in.readLine());
            java.sql.Date d_given = new java.sql.Date(dateUtil.getTime());
                ps.setDate(3, d_given);

            }
            catch (Exception pe) {
                System.out.println("ParseException!");
            }

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
            ps = con.prepareStatement("INSERT INTO Finance_Office VALUES (?,?,?,?)");

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
            ps = con.prepareStatement("INSERT INTO Partners VALUES (?,?,?,?)");

            System.out.print("\nGovernment department: ");
            g_department = in.readLine();
            ps.setString(1, g_department);

            System.out.print("\nCompany name: ");
            comp_name = in.readLine();
            ps.setString(2, comp_name);

            System.out.print("\nIndustry: ");
            industry = in.readLine();
            ps.setString(3, industry);

            System.out.print("\nPartner ID: ");
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
            ps = con.prepareStatement("INSERT INTO Taxpayer VALUES (?,?,?,?,?,?)");


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
            ps = con.prepareStatement("INSERT INTO Monetary_Assistance VALUES (?,?)");

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
            ps = con.prepareStatement("INSERT INTO Employment_Training VALUES (?,?,?)");

            System.out.print("\nSupport ID: ");
            supID = Integer.parseInt(in.readLine());
            ps.setInt(1, supID);

            System.out.print("\nType: ");
            type = in.readLine();
            ps.setString(2, type);

            System.out.print("\nInstructor: ");
            instructor = in.readLine();
            ps.setString(3, instructor);

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
            ps = con.prepareStatement("INSERT INTO Facilitate VALUES (?,?)");

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
    private void delete()
    {
        boolean deleteBack;
        int choice;
        deleteBack = false;

        try
        {
            while (!deleteBack)
            {
                System.out.print("Welcome to the delete menu! Please select one of the tables below to delete entries from!\n");
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
                    case 1:  deleteHelper("Government", "department", "NULL", "NULL"); break;
                    case 2:  deleteHelper("Support_Branch", "address", "city", "NULL"); break;
                    case 3:  deleteHelper("Client", "SIN", "NULL", "NULL"); break;
                    case 4:  deleteHelper("Support", "supportID", "NULL", "NULL"); break;
                    case 5:  deleteHelper("isGiven", "SIN", "supportID", "NULL"); break;
                    case 6:  deleteHelper("Finance_Office", "Government_department", "city", "address"); break;
                    case 7:  deleteHelper("Partners", "partnerID", "NULL", "NULL"); break;
                    case 8:  deleteHelper("Taxpayer", "SIN", "NULL", "NULL"); break;
                    case 9:  deleteHelper("Monetary_Assistance", "supportID", "NULL", "NULL"); break;
                    case 10:  deleteHelper("Employment_Training", "supportID", "NULL", "NULL"); break;
                    case 11:  deleteHelper("Facilitate", "partnerID", "supportID", "NULL"); break;

                    case 12:  deleteBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            deleteBack = true;

        }

    }
    private void deleteHelper(String tableDel, String primKey, String primKey2, String primKey3) {
        String primKeyData;
        String primKeyData2;
        String primKeyData3;
        PreparedStatement  ps;
        try {
            if (primKey2 == "NULL" && primKey3 == "NULL")
            {

                //try the above with the argument already filled in for delete, DELETE from government where department = 'finaltest'
            //ps = con.prepareStatement("DELETE FROM Government WHERE department = 'newTest'");
                System.out.print("\n" + tableDel + " " + primKey + ": ");
            primKeyData = in.readLine();
            ps = con.prepareStatement("DELETE FROM " + tableDel + " WHERE " + primKey + " = '" + primKeyData + "'");


            int rowCount = ps.executeUpdate();

            if (rowCount == 0)
            {
                System.out.println("\n" + tableDel + " " + primKey + ": " + primKeyData + " does not exist!");
            } else {
                System.out.println("Deleted");
            }

            con.commit();

            ps.close();
            }
            else if (primKey3 == "NULL") {
                System.out.print("\n" + tableDel + " " + primKey + ": ");
                primKeyData = in.readLine();
                //ps.setString(1, primKeyData);

                System.out.print("\n" + tableDel + " " + primKey2 + ": ");
                primKeyData2 = in.readLine();
                //ps.setString(2, primKeyData2);
                ps = con.prepareStatement("DELETE FROM " + tableDel + " WHERE " + primKey + " = '" + primKeyData + "' AND " + primKey2 + " = '" + primKeyData2 + "'");


                int rowCount = ps.executeUpdate();

                if (rowCount == 0)
                {
                    System.out.println("\n" + tableDel + " " + primKey + ": " + primKeyData + ", "+ primKey2 + ": "+ primKeyData2 + " does not exist!");
                } else {
                    System.out.println("Deleted");
                }

                con.commit();

                ps.close();

            } else {

                System.out.print("\n" + tableDel + " " + primKey + ": ");
                primKeyData = in.readLine();
                //ps.setString(1, primKeyData);

                System.out.print("\n" + tableDel + " " + primKey2 + ": ");
                primKeyData2 = in.readLine();
                //ps.setString(2, primKeyData2);

                System.out.print("\n" + tableDel + " " + primKey3 + ": ");
                primKeyData3 = in.readLine();
                //ps.setString(3, primKeyData3);

                ps = con.prepareStatement("DELETE FROM " + tableDel + " WHERE " + primKey + " = '" + primKeyData + "' AND " + primKey2 + " = '" + primKeyData2 + "' AND " + primKey3 + " = '" + primKeyData3 + "'");

                int rowCount = ps.executeUpdate();

                if (rowCount == 0)
                {
                    System.out.println("\n" + tableDel + " " + primKey + ": " + primKeyData + ", "+ primKey2 + ": "+ primKeyData2 + ", "+ primKey3 + ": "+ primKeyData3 + " does not exist!");
                } else {
                    System.out.println("Deleted");
                }

                con.commit();

                ps.close();


            }
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
                System.exit(-1);
                //TODO error handling
            }
        }
    }


    /*
     * display information about branches
     */
    private void show()
    {
        boolean showBack;
        int choice;
        showBack = false;

        try
        {
            while (!showBack)
            {
                System.out.print("Welcome to the Show menu! Please select one of the tables below to display!\n");
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
                    case 1:  showGovernment(); break;
                    case 2:  showSupport_Branch(); break;
                    case 3:  showClient(); break;
                    case 4:  showSupport(); break;
                    case 5:  showisGiven(); break;
                    case 6:  showFinance_Office(); break;
                    case 7:  showPartners(); break;
                    case 8:  showTaxpayer(); break;
                    case 9:  showMonetary_Assistance(); break;
                    case 10:  showEmployment_Training(); break;
                    case 11:  showFacilitate(); break;

                    case 12:  showBack = true;

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

    }
    private void showGovernment() {
        String department;
        String head_of_dep;
        String division;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Government");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                department = rs.getString("department");
                System.out.printf("%-30.30s", department);

                head_of_dep = rs.getString("head_of_department");
                System.out.printf("%-30.30s", head_of_dep);

                //if it can be null do this
                division = rs.getString("division");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s \n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s \n", division);
                }
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    private void showSupport_Branch() {
        String address;
        String city;
        String branchmanager;
        String g_department;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Support_Branch");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                address = rs.getString("address");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", address);
                }

                city = rs.getString("city");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", city);
                }

                //if it can be null do this
                branchmanager = rs.getString("BranchManager");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", branchmanager);
                }

                g_department = rs.getString("Government_department");
                System.out.printf("%-30.30s \n", g_department);

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showClient() {
        String name;
        String address;
        String phoneC;
        String sinC;
        String sb_city;
        String sb_address;
        String d_requested;
        String type;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Client");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                name = rs.getString("name");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", name);
                }

                address = rs.getString("address");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", address);
                }

                //if it can be null do this
                phoneC = rs.getString("phone#");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", phoneC);
                }

                sinC = rs.getString("SIN");
                System.out.printf("%-30.30s", sinC);

                sb_city = rs.getString("sb_city");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", sb_city);
                }
                sb_address = rs.getString("sb_address");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", sb_address);
                }
                d_requested = rs.getString("date_requested");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", d_requested);
                }
                type = rs.getString("type_requested");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s\n", type);
                }


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showSupport() {
        String supportID;
        String budget_given;
        String g_department;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Support");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s", supportID);

                budget_given = rs.getString("budget_given");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", budget_given);
                }

                //if it can be null do this
                g_department = rs.getString("Government_department");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s \n", g_department);
                }
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showisGiven() {
        String SINisGiven;
        String supportID;
        String date_given;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM isGiven");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                SINisGiven = rs.getString("SIN");
                System.out.printf("%-30.30s", SINisGiven);

                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s", supportID);

                //if it can be null do this
                date_given = rs.getString("date_Given");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s \n", date_given);
                }
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showFinance_Office() {
        String Government_department;
        String city;
        String address;
        String officeManager;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Finance_Office");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                Government_department = rs.getString("Government_department");
                System.out.printf("%-30.30s", Government_department);

                city = rs.getString("city");
                System.out.printf("%-30.30s", city);

                address = rs.getString("address");
                System.out.printf("%-30.30s", address);

                officeManager = rs.getString("officeManager");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s \n", officeManager);
                }

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showPartners() {
        String Government_department; //not null
        String comp_name;
        String industry;
        String partnerID;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Partners");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                Government_department = rs.getString("Government_department");
                System.out.printf("%-30.30s", Government_department);

                comp_name = rs.getString("companyName");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", comp_name);
                }

                industry = rs.getString("industry");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", industry);
                }

                partnerID = rs.getString("partnerID");
                    System.out.printf("%-30.30s \n", partnerID);

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showTaxpayer() {
        String sintp; //not null
        String government_department;
        String name;
        String address;
        String phoneT;
        String amount_paid;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Taxpayer");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                sintp = rs.getString("SIN");
                System.out.printf("%-30.30s", sintp);

                government_department = rs.getString("Government_department");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", government_department);
                }

                name = rs.getString("name");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", name);
                }

                address = rs.getString("address");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", address);
                }

                phoneT = rs.getString("phone#");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", phoneT);
                }

                amount_paid = rs.getString("amount_paid");if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s\n", amount_paid);
                }


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showMonetary_Assistance() {
        String supportID; //not null
        String amount;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Monetary_Assistance");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s", supportID);

                amount = rs.getString("amount");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s\n", amount);
                }

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showEmployment_Training() {
        String supportID; //not null
        String type;
        String instructor;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Employment_Training");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s", supportID);

                type = rs.getString("type");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", type);
                }

                instructor = rs.getString("instructor");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s\n", instructor);
                }

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void showFacilitate() {
        String partnerID; //not null
        String supportID;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Facilitate");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                partnerID = rs.getString("partnerID");
                System.out.printf("%-30.30s", partnerID);

                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s\n", supportID);

            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }



    private void governmentQ() {
        boolean governmentBack;
        int choice;
        governmentBack = false;

        try
        {
            while (!governmentBack)
            {
                System.out.print("Welcome to the Government Employee Menu! Please select one of the Queries!\n");
                System.out.print("1.   ------Find the Client receiving the highest amount of monetary assistance\n");
                System.out.print("2.   ------Change instructor for given course\n");
                System.out.print("3.   ------View Clients who are receiving training support from Partners\n");
                System.out.print("4.   ------View the address and city of support branches that are currently giving out monetary support\n");
                System.out.print("5.   ------View the total budget given to a specific branch\n");
                System.out.print("6.   ------View the monetary assistance a specific branch gives to its clients\n");
                System.out.print("7.   ------View budgets over a specific amount\n");

                System.out.print("8.  ------Back to menu\n ");


                choice = Integer.parseInt(in.readLine());
                System.out.println(" ");
                switch(choice)
                {
                    case 1:  gQ1HighestClient(); break;
                    case 2:  gQ2HUpdate(); break;
                    case 3:  gQ3ViewC(); break;
                    case 4:  gQ4ViewB(); break;
                    case 5:  gQ5TotalBudget(); break;
                    case 6:  gQ6ViewBranchOutflows(); break;
                    case 7:  gQ7ViewBudgetOver(); break;

                    case 8:  governmentBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            governmentBack = true;

        }
    }
    private void gQ1HighestClient() {
        String name;
        String amount;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, MAX(amount) FROM Client, Monetary_Assistance, isGiven  WHERE (Monetary_Assistance.supportID = isGiven.supportID) AND (Client.SIN = isGiven.SIN) GROUP BY Monetary_Assistance.supportID, name, amount HAVING MAX(amount)=(SELECT MAX(MAX(amount)) FROM Monetary_Assistance GROUP BY supportID)");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                name = rs.getString("name");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s", " ");
                }
                else
                {
                    System.out.printf("%-30.30s", name);
                }

                amount = rs.getString("MAX(amount)");
                if (rs.wasNull())
                {
                    System.out.printf("%-30.30s\n", " ");
                }
                else
                {
                    System.out.printf("%-30.30s\n", amount);
                }





            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    } //working
    private void gQ2HUpdate() {
        String  instructor;
        String course;
        PreparedStatement  ps;

        try
        {

            System.out.print("\nInstructor name: ");
            instructor = in.readLine();
            System.out.print("\nSupport ID value of course to change: ");
            course = in.readLine();



            ps = con.prepareStatement("update employment_training set instructor = '" + instructor + "' where supportid = '" + course + "'");


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
    } //working
    private void gQ3ViewC() {
        String cname;
        String cphone;
        String supportID;
        String pname;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select c.name, c.phone#, support.supportid, partners.companyname from client c join isgiven on c.sin=isgiven.sin inner join support on support.supportid=isgiven.supportid join facilitate on facilitate.supportid=support.supportid join partners on partners.partnerid=facilitate.partnerid order by c.name");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                cname = rs.getString("name");
                System.out.printf("%-30.30s", cname);

                cphone = rs.getString("phone#");
                System.out.printf("%-30.30s", cphone);

                supportID = rs.getString("supportid");
                System.out.printf("%-30.30s", supportID);

                pname = rs.getString("companyname");
                System.out.printf("%-30.30s\n", pname);
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void gQ4ViewB() {
        String sb_address;
        String sb_city;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT Client.sb_address, Client.sb_city FROM Client, isGiven, Monetary_Assistance WHERE (isGiven.SIN = Client.SIN) AND (isGiven.supportID = Monetary_Assistance.supportID)");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                sb_address = rs.getString("sb_address");
                System.out.printf("%-30.30s", sb_address);

                sb_city = rs.getString("sb_city");
                System.out.printf("%-30.30s\n", sb_city);


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void gQ5TotalBudget() {
        String sum;
        String address;
        String city;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            System.out.print("\nBranch City to view budget of: ");
            city = in.readLine();
            System.out.print("\nBranch Address to view budget of: ");
            address = in.readLine();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT SUM(budget_given) FROM Support, isGiven WHERE (Support.supportID = isGiven.supportID) AND SIN IN (SELECT SIN FROM Client WHERE (sb_city = '" + city + "') AND (sb_address = '" + address + "'))");

<<<<<<< HEAD
<<<<<<< HEAD
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                sum = rs.getString("SUM(budget_given)");
                System.out.printf("%-30.30s", sum);



            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (IOException ex)
        {
            System.out.println("Message: " + ex.getMessage());

        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void gQ6ViewBranchOutflows() {
        String amount;
        String sb_city;
        String sb_address;
        Statement  stmt;
        ResultSet  rs;
        
        try
        {
            stmt = con.createStatement();
            
            System.out.print("\nCity of Branch: ");
            sb_city = in.readLine();
            
            System.out.print("\nAddress of Branch: ");
            sb_address = in.readLine();
            
            
            rs = stmt.executeQuery("SELECT SUM (amount) FROM Monetary_Assistance, isGiven WHERE (Monetary_Assistance.supportID = isGiven.supportID) AND SIN IN (SELECT SIN FROM Client WHERE (sb_city = '" + sb_city + "') AND (sb_address = '" + sb_address + "'))");
=======
>>>>>>> 64b6b1660c9bcd7bce282635a0228e6c336a2e44
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur
<<<<<<< HEAD
                
                amount = rs.getString("SUM(amount)");
                System.out.printf("%-30.30s", amount);
                
            
                
                
=======

                sum = rs.getString("SUM(budget_given)");
                System.out.printf("%-30.30s", sum);



>>>>>>> 64b6b1660c9bcd7bce282635a0228e6c336a2e44
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
            
            
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    private void gQ7ViewBudgetOver() {
        String supportID;
        String budgetamount;
        Statement  stmt;
        ResultSet  rs;
        
        try
        {
            stmt = con.createStatement();
            
            System.out.print("\nView budgets over what amount ($): ");
            budgetamount = in.readLine();
            
            
            
            rs = stmt.executeQuery("SELECT supportID, budget_given FROM Support WHERE budget_given > '" + budgetamount + "'");
=======
>>>>>>> 64b6b1660c9bcd7bce282635a0228e6c336a2e44
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur
<<<<<<< HEAD
                
                supportID = rs.getString("supportID");
                System.out.printf("%-30.30s\n", supportID);
                
                
                
                
=======

                sum = rs.getString("SUM(budget_given)");
                System.out.printf("%-30.30s", sum);



>>>>>>> 64b6b1660c9bcd7bce282635a0228e6c336a2e44
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
            
            
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    private void taxpayerQ() {
        boolean taxpayerBack;
        int choice;
        taxpayerBack = false;

        try
        {
            while (!taxpayerBack)
            {
                System.out.print("Welcome to the Taxpayer Menu! Please select one of the Queries!\n");
                System.out.print("1.   ------View how much of your money is going towards government programs\n");

                System.out.print("5.  ------Back to menu\n ");


                choice = Integer.parseInt(in.readLine());
                System.out.println(" ");
                switch(choice)
                {
                    case 1:  tQ1ViewPayment(); break;
                    case 2:  ; break;
                    case 3:  ; break;
                    case 4:  ; break;

                    case 5:  taxpayerBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            taxpayerBack = true;

        }
    }
    private void tQ1ViewPayment() {
        String amount_paid;
        String nameT;
        Statement  stmt;
        ResultSet  rs;
        
        try
        {
            
            System.out.print("\nWhat is your name: ");
            nameT = in.readLine();
            
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT amount_paid FROM Taxpayer WHERE name = '" + nameT + "'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it
                
                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }
            
            System.out.println(" ");
            
            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string
                
                // simplified output formatting; truncation may occur
                
                amount_paid = rs.getString("amount_paid");
                System.out.printf("%-30.30s\n", amount_paid);
                
                
                
            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");
            
            stmt.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
            
            
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }

    }
    
    
    
    private void sbQ() {
        boolean sbBack;
        int choice;
        sbBack = false;

        try
        {
            while (!sbBack)
            {
                System.out.print("Welcome to the Support Branch Menu! Please select one of the Queries!\n");
                System.out.print("1.   ------View the Number of Instructors for each course\n");

                System.out.print("5.  ------Back to menu\n ");


                choice = Integer.parseInt(in.readLine());
                System.out.println(" ");
                switch(choice)
                {
                    case 1:  spQ1ViewClassCount(); break;
                    case 2:  ; break;
                    case 3:  ; break;
                    case 4:  ; break;

                    case 5:  sbBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            sbBack = true;

        }
    }
    private void spQ1ViewClassCount() {
        String type;
        String countI;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT type, count(instructor) FROM Employment_Training GROUP BY type ORDER BY count(instructor) DESC");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                type = rs.getString("type");
                System.out.printf("%-30.30s", type);

                countI = rs.getString("count(instructor)");
                System.out.printf("%-30.30s\n", countI);


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }

    }

    private void instructorQ() {
        boolean instructorBack;
        int choice;
        instructorBack = false;

        try
        {
            while (!instructorBack)
            {
                System.out.print("Welcome to the Instructor company Menu! Please select one of the Queries!\n");
                System.out.print("1.   ------View all current Students' names and phone numbers\n");
                System.out.print("5.  ------Back to menu\n ");


                choice = Integer.parseInt(in.readLine());
                System.out.println(" ");
                switch(choice)
                {
                    case 1:  iQ1ViewStudents(); break;
                    case 2:  ; break;
                    case 3:  ; break;
                    case 4:  ; break;

                    case 5:  instructorBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            instructorBack = true;

        }
    }
    private void iQ1ViewStudents() {
        String cname;
        String cphone;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from EMP_Training_Students");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                cname = rs.getString("name");
                System.out.printf("%-30.30s", cname);

                cphone = rs.getString("phone#");
                System.out.printf("%-30.30s\n", cphone);


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    private void clientQ() {
        boolean clientBack;
        int choice;
        clientBack = false;

        try
        {
            while (!clientBack)
            {
                System.out.print("Welcome to the Client Menu! Please select one of the Queries!\n");
                System.out.print("1.   ------Show all Tax Payments made to each government department\n");

                System.out.print("5.  ------Back to menu\n ");


                choice = Integer.parseInt(in.readLine());
                System.out.println(" ");
                switch(choice)
                {
                    case 1:  cQ1ViewTaxAmounts(); break;
                    case 2:  ; break;
                    case 3:  ; break;
                    case 4:  ; break;

                    case 5:  clientBack = true;

                }
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            clientBack = true;

        }
    }

    private void cQ1ViewTaxAmounts() {
        String amount;
        String department;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Taxpayer_Amounts");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            System.out.println(" ");
            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-30s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                amount = rs.getString("Amount_Paid");
                System.out.printf("%-30.30s", amount);

                department = rs.getString("Government_Department");
                System.out.printf("%-30.30s\n", department);


            }
            // close the statement;
            // the ResultSet will also be closed
            System.out.println("\n\n\n");

            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    /* TODO all of the following
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

    */

    public static void main(String args[])
    {
        //TODO main
      textInterface b = new textInterface();
    }
}
