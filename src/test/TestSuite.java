package test;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import crm.AssignLeads;
import crm.Lead;
import crm.User;

public class TestSuite 
{
   int NUMBER_OF_LEADS = 4;
   int NUMBER_OF_USERS = 3;
   AssignLeads testAssignLeads = new AssignLeads("headless-test"); 

   @Test
   public void NoUsersTest()
   {
      testAssignLeads.getUsers().clear();
      assertEquals(0, testAssignLeads.getUsers().size());
      testAssignLeads.RollingAssignment();
      assertEquals(NUMBER_OF_LEADS, testAssignLeads.getLeads().size());
   } 
   /*
    * Start of the LOCATION ASSIGNMENT TESTS
    */
   @Test
   public void BeforeAnyAssignmentPreTestConditions()
   {
      // Tests there are Users and Leads created but no Assignment
      assertEquals(NUMBER_OF_LEADS, testAssignLeads.getLeads().size());
      assertEquals(NUMBER_OF_USERS, testAssignLeads.getUsers().size());
      // This Test is testing speficially that the first user has no assigned leads,
      // as this is a just instanicated object it should have zero
      assertEquals(0, testAssignLeads.getUsers().get(0).getLeadList().size());
   }

   @Test
   public void AssignLeadsByLocationMatchFoundTest()
   {
      testAssignLeads.AssignByLocation();
      // Based on the design for this test the User in this position should have two
      // Assigned lead as there are two leads with the location "USA" which match
      // this user
      assertEquals(2, testAssignLeads.getUsers().get(1).getLeadList().size());
   }

   @Test
   public void AssignLeadsByLocationMatchNotFoundTest()
   {
      testAssignLeads.AssignByLocation();
      // Based on the design for this test the User in this position should have zero
      // Assigned lead as there are no leads with the location "AU" which match
      // this user
      assertEquals(0, testAssignLeads.getUsers().get(2).getLeadList().size());
   }

   @Test
   public void AssignLeadsByLocationNoUserTest()
   {
      boolean found_assigned_lead = false;
      testAssignLeads.AssignByLocation();
      for ( User check_users : testAssignLeads.getUsers() )
      {
         // This runs through all the users looking for one that found not be assigned
         if (check_users.getLeadList().contains(3)) { found_assigned_lead = true; }
      }
      assertFalse(found_assigned_lead);
   }
   /*
    * Start of the INDUSTRY ASSIGNMENT TESTS
    */
   
    @Test
    public void IndustryAssignmentBasicTest()
    {
      // In this test we are looking for 2 of the users to be assigned to the 
      // third Lead in the list.
      int POSITION_OF_EXPECTED_LEAD = 2;
      int POSITION_OF_EXPECTED_USER_1 = 1;
      int POSITION_OF_EXPECTED_USER_2 = 2;
      testAssignLeads.AssignByIndustry();
      assertEquals(POSITION_OF_EXPECTED_LEAD, 
         testAssignLeads.getUsers().get(POSITION_OF_EXPECTED_USER_1).getLeadList().get(0));
      assertEquals(POSITION_OF_EXPECTED_LEAD,
         testAssignLeads.getUsers().get(POSITION_OF_EXPECTED_USER_2).getLeadList().get(0));
    }

    @Test
    public void IndustryAssignmentNoUsersTest()
    {
      // The Lead in position three has no matches to any
      // of the industies the users have
      int POSITION_OF_LEAD = 3;
      int POSITION_OF_USER_1 = 1;
      int POSITION_OF_USER_2 = 2;
      testAssignLeads.AssignByIndustry();
      assertFalse(POSITION_OF_LEAD == 
         testAssignLeads.getUsers().get(POSITION_OF_USER_1).getLeadList().get(0));
      assertFalse(POSITION_OF_LEAD ==
         testAssignLeads.getUsers().get(POSITION_OF_USER_2).getLeadList().get(0));
    }

    @Test
    public void IndustryAssignmentNoLeadsTest()
    {
      // No leads have the industry "Farming" so this user will not be assigned
      int POSITION_OF_USER = 0;
      testAssignLeads.getUsers().get(POSITION_OF_USER).setIndustry("Farming");
      testAssignLeads.AssignByIndustry();
      assertTrue( testAssignLeads.getUsers().get(POSITION_OF_USER).getLeadList().isEmpty() );
    }


}
