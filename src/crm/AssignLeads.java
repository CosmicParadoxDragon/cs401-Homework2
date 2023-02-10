package crm;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.InputMismatchException;

import java.util.ArrayList;
import java.util.List;

import test.Rule;

public class AssignLeads {
    final int   EXIT = 0,
                ADD_LEAD = 1,
                ADD_USER = 2,
                ASSIGN = 3,
                DISPLAY = 4,
                NO_STATE = -1;

    Scanner key_scan = new Scanner(System.in);
    private ArrayList<Lead> leads;
    private ArrayList<User> users;
    int command = -1;
    /**
     * Function: Constructor for the AssignLeads Object
     * Serves as the main entry point for the program
     * Calls MainLoop
     * 
     * @param mode This is a param to activate the headless mode for 
     * the purposes of testing only, any other string does nothing.
     */
    public AssignLeads( )
    {
        leads = new ArrayList<Lead>();
        users = new ArrayList<User>();
        command = NO_STATE;
        MainLoop();
    }

    public AssignLeads( String mode )
    {
        leads = new ArrayList<Lead>();
        users = new ArrayList<User>();
        command = NO_STATE;
        if (mode.equals("headless-test"))
        {
            getLeads().add(new Lead("John Smith", "j_smith@company.com", "USA", "Boats"));
            getLeads().add(new Lead("Apple Morn", "a_morn@company.com", "UK", "Music"));
            getLeads().add(new Lead("Clark Kent", "c_kent@dailyplanet.us", "USA", "Cars"));
            getLeads().add(new Lead("Lesterteroph", "l_astlan@hotmail.com", "Abyss", "General"));

            getUsers().add(new User("Mary Jane", "UK", "Music"));
            getUsers().add(new User("Henry Roberts", "USA", "Cars"));
            getUsers().add(new User("Luke Cage", "AU", "Cars"));
        }
    }
    public ArrayList<Lead> getLeads() { return leads; }
    public ArrayList<User> getUsers() { return users; }

    /**
     * Function:
     * MainLoop serves as the main driver for the logic of the program
     * class and references the entire package
     * Asks for input, checks for vaild input and repeats
     */
    void MainLoop()
    {
        // This is a test lead and user build for easy testing only
        getLeads().add(new Lead("John Smith", "j_smith@company.com", "USA", "Boats"));
        getLeads().add(new Lead("Apple Morn", "a_morn@company.com", "UK", "Music"));
        getLeads().add(new Lead("Clark Kent", "c_kent@dailyplanet.us", "USA", "Cars"));
        getLeads().add(new Lead("Lesterteroph", "l_astlan@hotmail.com", "Abyss", "General"));

        getUsers().add(new User("Mary Jane", "UK", "Music"));
        getUsers().add(new User("Henry Roberts", "USA", "Cars"));
        getUsers().add(new User("Luke Cage", "AU", "Cars"));

        while (command != 0)
        {
            boolean valid = false;
            while (!valid)
            {
                try { 
                    System.out.print(   "0. Quit\n1. Add a lead\n2. Add a user\n3. Assign leads\n4. Display leads\n" + 
                    "Please choose a cmd (0-4): ");
                    command = key_scan.nextInt();
                    valid = true;
                }
                catch (InputMismatchException err) {
                    System.out.println("Invalid cmd.");
                    key_scan.next();
                }
            }
            switch (command) {
                case EXIT:
                    key_scan.close();
                    System.exit(0);
                    break;
                case ADD_LEAD:
                    Lead new_lead = new Lead();
                    leads.add(new_lead);
                    break;
                case ADD_USER:
                    User new_user = new User();
                    users.add(new_user);
                    break;
                case ASSIGN:
                    Assign(leads, users);
                    break;
                case DISPLAY:
                    DisplayLeads(leads, users);
                    break;
                default:
                    System.out.println("Invalid Cmd.");
                    break;
            }
        }
    }
    /**
     * Function:
     * Assign, This function is the way to attach users to leads will loop through leads if 
     * number of users > leads
     * @param leads A list of leads in an ArrayList object
     * @param users A list of users in an ArrayList object
     */
    private void Assign(ArrayList<Lead> leads, ArrayList<User> users)
    {
        final int   ONLINE_UERS = 0,
                    ROLLING     = 1,
                    BY_LOCATION = 2,
                    MANUAL      = 3;

        System.out.print(   "Please Select a method for User Assignment:\n0. Online Users\n" + 
                            "1. Rolling Assignment\n2. By Location\n3. Manual\nCMD: ");
        int assign_command = key_scan.nextInt();
        
        int numberOfLeads = leads.size();
        switch (assign_command)
        {
            case ONLINE_UERS:
                AssignOnlineUsers( ); break;
            case ROLLING:
                RollingAssignment( ); break;
            case BY_LOCATION:
                AssignByLocation( ); break;
            case MANUAL:
                ManualAssignment(numberOfLeads); break;
        }
    }
    public void ManualAssignment(int numberOfLeads)
    {
        ClearAssignments();
        DisplayLeads( leads, users );
        System.out.println("");
        DisplayUsers( users );
        System.out.println("");
        System.out.print("Please enter the number of the Lead to Select: ");
        int selected_lead = key_scan.nextInt();
        System.out.print("Please enter the number of the User to assign: ");
        int selected_user = key_scan.nextInt();
        if ( selected_lead > numberOfLeads || selected_user > users.size() ) { System.out.println("Entry Invalid."); }
        users.get(selected_user).AssignUserTo(selected_lead);
    }

    public void AssignOnlineUsers( )
    {
        ClearAssignments();
        int leadNumber = 0;
        List<User> onlineUsers = users.stream().filter(Rule.userOnline).collect(Collectors.toList());
        for (User user : onlineUsers)
        {
            user.AssignUserTo(leadNumber);
            leadNumber++;
            // Assign one lead per, until there is more Users than Leads then rolls to the top
            // of the Leads list.
            if ( leadNumber > onlineUsers.size() ) { leadNumber = 0; }
        }
    }
    public void RollingAssignment()
    {
        ClearAssignments();
        int numberOfLeads = leads.size();
        int leadNumber = 0;
        for ( User user : users )
        {
            user.AssignUserTo( leadNumber );
            leadNumber++;
            if ( leadNumber > numberOfLeads ) { leadNumber = 0; }
            
        }
    }

    public void AssignByLocation()
    {
        ClearAssignments();
        List<User> onlineUsers = users.stream().filter(Rule.userOnline).collect(Collectors.toList());
        for ( User user : onlineUsers )
        {
            List<Lead> location_leads = leads.stream().filter(l -> l.getLocation() == user.getLocation()).toList();
            for (Lead leadInALocation : location_leads)
            {
                user.m_leadAssignments.add(leads.indexOf(leadInALocation));
            }
        }
    }

    public void AssignByIndustry()
    {
        ClearAssignments();
        List<User> onlineUsers = users.stream().filter(Rule.userOnline).collect(Collectors.toList());
        for ( User user : onlineUsers )
        {
            List<Lead> industry_leads = leads.stream().filter(l -> l.getIndustry() == user.getIndustry()).toList();
            for (Lead leadInAnIndustry : industry_leads)
            {
                user.m_leadAssignments.add(leads.indexOf(leadInAnIndustry));
            }
        }
    }
    
    /**
     * Function:
     * DisplayLeads, this function pretty prints the information for all the leads in the lead
     * list and if any user is associated to that lead
     * @param leads
     * @param users
     */
    private static void DisplayLeads(ArrayList<Lead> leads, ArrayList<User> users)
    {
        System.out.format("%-4s%-28s%-18s%-28s%-28s%-28s", "#", "Name", "Country", "Email", "Industry", "Assigned User" + "\n" +
                            "------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (Lead lead : leads)
        {
            int currentLead = leads.indexOf( lead );
            System.out.format("%-4d%-28s%-18s%-28s%-28s", currentLead, lead.getName(), lead.getLocation(), lead.getEmail(), lead.getIndustry());
            for ( User user : users )
            {
                for ( int assignedLead : user.m_leadAssignments )
                {
                    if ( assignedLead == currentLead )
                    {
                        System.out.print( user.getName() + "\t" );
                    }
                }
            }
            System.out.print( "\n" );
        }
        System.out.print( "\n" );
    }
    private static void DisplayUsers( ArrayList<User> users )
    {
        System.out.format("%-4s%-28s%-18s%-28s", "#", "Name", "Country", "Online" + "\n" +
                            "---------------------------------------------------------------------------------------------------------------------------\n");
        for (User user : users)
        {
            int currentUser = users.indexOf(user);
            System.out.format("%-4d%-28s%-18s%-28s%n",currentUser, user.getName(), user.getLocation(), user.getOnline());
        }
        System.out.print("\n");
    }

    private void ClearAssignments()
    {
        for (User user_to_clear : users)
        {
            user_to_clear.m_leadAssignments.clear();
        };
    }
}
