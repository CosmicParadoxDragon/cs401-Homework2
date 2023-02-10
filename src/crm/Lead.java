package crm;

import java.util.Scanner;
import java.util.ArrayList;

public class Lead {
    private String m_name, m_email, m_location, m_industry = "";
    Scanner key_scan = new Scanner(System.in);
    protected ArrayList<Integer> m_userAssignments = new ArrayList<Integer>();
    /**
     * Function:
     * Lead, constructor for the Lead object,
     * prompts and accepts user input for the member fields
     */
    public Lead()
    {
        System.out.print("Please enter a name for the lead: ");
        m_name = key_scan.nextLine();
        System.out.print("Please enter an email contact for the lead: ");
        m_email = key_scan.nextLine();
        System.out.print("Please enter the country where the lead is located: ");
        m_location = key_scan.nextLine();
        //key_scan.close();
    }
    /**
     * Basic (non-defualt) constructor -- for testing purposes
     */
    public Lead(String name, String email, String location, String industry)
    {
        m_name = name;
        m_email = email;
        m_location = location;
        m_industry = industry;
    }

    /**
     * Getters for the Lead Objects
     */
    public String getName() { return m_name; }
    public String getEmail() { return m_email; }
    public String getLocation() { return m_location; }
    public String getIndustry() { return m_industry; }
    protected void setLocation(String new_location) { m_industry = new_location; }
    protected void setIndustry(String new_industry) { m_industry = new_industry; }
}
