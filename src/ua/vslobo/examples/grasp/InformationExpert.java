package ua.vslobo.examples.grasp;

import java.util.Date;

public class InformationExpert {

    class A {
        Date start;
        Date end;
    }

    class B {
        A a;

        // Method should be in A class!
        // Violates Information Expert
        boolean isWithinDateRange(Date date) {
            return date.after(a.start) && date.before(a.end);
        }
    }

}
