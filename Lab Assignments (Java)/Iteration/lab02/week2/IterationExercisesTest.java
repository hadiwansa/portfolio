package week2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class IterationExercisesTest {

    @Test
    void sortIntervalsTest(){
        ArrayList<IterationExercises.Interval> input = new ArrayList<>(Arrays.asList(
                new IterationExercises.Interval(1,17),
                new IterationExercises.Interval(4,5),
                new IterationExercises.Interval(14,18),
                new IterationExercises.Interval(5,11),
                new IterationExercises.Interval(15,16)));
        ArrayList<IterationExercises.Interval> answer = new ArrayList<>(Arrays.asList(
                new IterationExercises.Interval(4,5),
                new IterationExercises.Interval(5,11),
                new IterationExercises.Interval(15,16),
                new IterationExercises.Interval(1,17),
                new IterationExercises.Interval(14,18)));

        ArrayList<IterationExercises.Interval> res = (ArrayList<IterationExercises.Interval>) IterationExercises.sortIntervals(input);
        for (int i = 0; i < res.size(); i++) {
            assertEquals(answer.get(i).getL(),res.get(i).getL(), "Wrong Left Interval!");
            assertEquals(answer.get(i).getR(), res.get(i).getR(), "Wrong Right Interval!");
            assertNotEquals(input.get(i).getR(), res.get(i).getR(), "Mutated Input!");
        }
    }

    @Test
    void numRocksTest(){
        String inp =  "|||..|.|.|.|...|";
        List<IterationExercises.Interval> queries = new ArrayList<>(Arrays.asList(
                new IterationExercises.Interval(0,2),
                new IterationExercises.Interval(0,3),
                new IterationExercises.Interval(1,4),
                new IterationExercises.Interval(1,5),
                new IterationExercises.Interval(4,12),
                new IterationExercises.Interval(6,10),
                new IterationExercises.Interval(4,13),
                new IterationExercises.Interval(0,15)));
        String answer = "0,0,0,2,3,1,3,8";
        assertEquals(answer,IterationExercises.numRocks(inp,queries));
    }

    @Test
    void withdrawTest(){
        IterationExercises.ATM atm = new IterationExercises.ATM();

        atm.deposit(new int[] {1,1,1,1,1});
        assertArrayEquals(atm.withdraw(750) , new int[]{0,1,0,1,1});

        assertArrayEquals(atm.withdraw(130) , new int[]{-1});
    }


    @Test
    void countCharactersTest(){
        String s = "I like CSC207";
        HashMap<Character, Integer> answer = new HashMap<>();
        answer.put('c',2);
        answer.put('s',1);
        answer.put('a',1);
        answer.put('i',2);
        answer.put('2',1);
        answer.put('0',1);
        answer.put('7',1);
        answer.put('l',1);
        answer.put('k',1);
        answer.put('e',1);

        HashMap<Character, Integer> guess = IterationExercises.countCharacters(s);
        for (Map.Entry<Character, Integer> entry : guess.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            assertEquals(answer.get(key), value, "Value for key " + key + " is wrong!");
        }

    }

    @Test
    void twiceTest(){
        int[] inp = new int[]{10,6,5,8,10,-1,-1};
        ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(10));
        ArrayList<Integer> res = IterationExercises.twice(inp);
        assertTrue(answer.size() == res.size() && answer.containsAll(res) && res.containsAll(answer));
    }

    //write your own JUnit Test(s) here!

}