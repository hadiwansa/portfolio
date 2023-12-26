package week5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import week5.observer.SewerObserver;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTest {

        static class ObserverSolution implements SewerObserver {
            public boolean notified = false;

            @Override
            public void update(Sewer sewer) {
                notified = true;
            }
        }

        MississaugaSewers ms;
        Maintainer m0,m1;

        @BeforeEach
        void init() throws IOException {
            ms = new MississaugaSewers(82323);
            m0 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}{ add("Catchbasin");}},43.5532409, -79.690821, 1000);
            m1 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}{ add("Catchbasin");}},43.5532409, -79.690821, 1000);
        }

        @Test
        void notifyTest(){
            ObserverSolution so = new ObserverSolution();
            ms.register(so);
            assertFalse(so.notified);
            ms.notifyObservers(ms.fetchSewer(65887));
            assertTrue(so.notified);
        }

        @Test
        void updateTest(){
            ms.register(m1);

            Sewer s = ms.fetchSewer(65887);
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);

            assertEquals(1,m1.getTodoList().size());
        }

        @Test
        void unregisterTest(){
            ms.register(m0);

            Sewer s = ms.fetchSewer(65887);
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);

            ms.unregister(m0);
            s.setStatus(Sewer.Status.ALL_GOOD);
            ms.setObservableState(s);

            assertEquals(1,m0.getTodoList().size());
        }

        @Test
        void testSetObservableStateAllGood() {
            ms.register(m0);
            Sewer s = ms.fetchSewer(65887);
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);
            assertEquals(1, m0.getTodoList().size());

            // Change the status to ALL_GOOD
            s.setStatus(Sewer.Status.ALL_GOOD);
            ms.setObservableState(s);
            assertEquals(0, m0.getTodoList().size());
        }

        @Test
        void testRegisterNewMaintainer() {
            Maintainer m2 = new Maintainer(new ArrayList<>() {{add("Double Catchbasin");}}, 43.5532409, -79.690821, 1000);
            ms.register(m2);
            Sewer s = ms.fetchSewer(65887);
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);
            assertEquals(1, m2.getTodoList().size());
        }

        @Test
        void testUnregisterNoUpdates() {
            ms.register(m0);
            ms.unregister(m0);

            Sewer s = ms.fetchSewer(65887);
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);

            assertEquals(0, m0.getTodoList().size());
        }

        @Test
        void testUpdateSpecialization() {
            Maintainer m2 = new Maintainer(new ArrayList<>() {{add("Manhole");}}, 43.5532409, -79.690821, 1000);
            ms.register(m2);

            Sewer s = ms.fetchSewer(65887);  // Assuming this is a "Double Catchbasin"
            s.setStatus(Sewer.Status.NEEDS_REPAIR);
            ms.setObservableState(s);

            assertEquals(0, m2.getTodoList().size());  // m2 is specialized for "Manhole", not "Double Catchbasin"
        }
}