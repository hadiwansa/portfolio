package week5;

import java.io.IOException;
import java.util.ArrayList;

/* Main class */
public class Main {

    public static void main(String[] args) throws IOException {

        MississaugaSewers ms = new MississaugaSewers(82323);

        // register some maintainers
        Maintainer m0 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}{add("Catchbasin");}}, 43.5532409, -79.690821, 1000);
        Maintainer m1 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}{add("Catchbasin");}}, 43.5532409, -79.690821, 1000);
        Maintainer m2 = new Maintainer(new ArrayList<>() {{add("Manhole");}}, 43.5532409, -79.690821, 1000);
        Maintainer m3 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}{add("Catchbasin");}}, 43.5287897, -79.6793471, 1000);

        ms.register(m0);
        ms.register(m1);
        ms.register(m2);
        ms.register(m3);

        Sewer s = ms.fetchSewer(65887);
        s.setStatus(Sewer.Status.NEEDS_REPAIR); //o no! needs repair!
        ms.setObservableState(s); //let everyone know!

        System.out.println("m0's to do list contains this many items: " + m0.getTodoList().size());
        System.out.println("m1's to do list contains this many items: " + m1.getTodoList().size());
        System.out.println("m2's to do list contains this many items: " + m2.getTodoList().size());
        System.out.println("m3's to do list contains this many items: " + m3.getTodoList().size());
        System.out.println("***");

        ms.unregister(m0);
        s.setStatus(Sewer.Status.ALL_GOOD);
        ms.setObservableState(s);

        System.out.println("m0's to do list contains this many items: " + m0.getTodoList().size());
        System.out.println("m1's to do list contains this many items: " + m1.getTodoList().size());
        System.out.println("m2's to do list contains this many items: " + m2.getTodoList().size());
        System.out.println("m3's to do list contains this many items: " + m3.getTodoList().size());

    }
}
