
package test;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import crm.*;

public class Rule {
    String rule = " Rules Book ";
    public static Predicate<Lead> lead_predicate;
    // public static Predicate<Lead> lead_location = (l) -> l.getLocation() == "l";
    public static Predicate<User> userOnline = u -> u.getOnline() == true;

    public static BiConsumer<Lead, String> validateLocation = (l, u) -> l.getLocation().equals(u);
    public static BiConsumer<Lead, User> validateIndustry = (l, u) -> l.getIndustry().equals(u.getIndustry());
}
