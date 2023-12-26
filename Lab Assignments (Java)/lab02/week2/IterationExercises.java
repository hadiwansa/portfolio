package week2;

import java.util.*;

/**
 * IterationExercises. A class designed for you to experiment with iterators and comparisons.
 */
public class IterationExercises {

    /**
     * Pair is a class designed to hold intervals ranging between a right index (r) and a left one (l).
     */
    public static class Interval implements Comparable<Interval> {
        private Integer l; //beginning of interval
        private Integer r; //end of interval

        /**
         * Interval Constructor
         *
         * @param l, the beginning of an interval
         * @param r, the end of interval
         */
        public Interval(Integer l, Integer r) {
            this.l = l;
            this.r = r;
        }

        /**
         * Getter and Setter methods for Pair attributes
         */
        public Integer getL() {
            return l;
        }

        public Integer getR() {
            return r;
        }

        public void setL(Integer l) {
            this.l = l;
        }

        public void setR(Integer r) {
            this.r = r;
        }


        /**
         * Compare one Interval to another Interval.
         * Use this to help you implement sortIntervals
         *
         * @return 0 if Interval are equal, 1 if Interval o is less than this Interval, else -1.
         */
        @Override
        public int compareTo(Interval o) {
            System.out.println("Comparing " + this + " to " + o);
            if (this.r.equals(o.r)) {
                return 0;
            } else if (this.r > o.r) {
                return 1;
            } else {
                return -1;
            }

        }
    }


    /**
     * Given a list of intervals, returns a new list of intervals sorted by their
     * end time. Do not alter the original input list!
     *
     * @param intervals is a list of Interval objects
     * @return a list of intervals, sorted in increasing order based on their ending time.
     */
    public static List<Interval> sortIntervals(List<Interval> intervals){
        List<Interval> sortedIntervals = new ArrayList<>(intervals);
        sortedIntervals.sort(Interval::compareTo);
        return sortedIntervals;
    }

    /**
     * Given an input string representing trees and rocks and a list of
     * Pair objects (each of which references a substring). Find the number
     * of rocks that lie between two trees in each substring.
     *
     * For example, if the input s is '..|..|..' and the referenced substring is '|..|..',
     * you should find 2 rocks between 2 trees. If the references substring is '|..',
     * no rocks are between two trees.
     *
     * @param s a String of characters '|' and '.' only, representing trees and rocks.
     * @param queries a list of Pairs. Each Pair defines a sub-string s[l:r) (inclusive)
     * @return a space-free comma-delimited string of numbers (e.g. "i1,i2,i3,...")
     * representing the answers to the queries.
     */
    public static String numRocks(String s, List<Interval> queries) {
        StringBuilder result = new StringBuilder();

        for (Interval query : queries) {
            int rockCount = 0;
            int totalRockCount = 0;
            boolean betweenTrees = false;

            for (int i = query.getL(); i <= query.getR(); i++) {
                char currentChar = s.charAt(i);

                if (currentChar == '|') {
                    if (betweenTrees) {
                        totalRockCount += rockCount;
                        rockCount = 0;
                    } else {
                        betweenTrees = true;
                    }
                } else if (currentChar == '.' && betweenTrees) {
                    rockCount++;
                }
            }
            result.append(totalRockCount).append(",");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    /**
     * Return a HashMap that includes characters in an input string
     * as keys and the number of times they occur in the
     * string as the value. Convert characters to lower
     * case before inserting them in the hashmap. Include
     * characters that are alphanumeric but exclude
     * characters that are whitespaces.
     *
     * @param input	an input string
     *
     * @return a hashmap that shows frequency count of alphanumeric characters in given string
     */
    public static HashMap<Character, Integer> countCharacters(String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : input.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                ch = Character.toLowerCase(ch);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }
        return map;
    }

    /**
     * ATM is a class designed to hold banking information
     */
    public static class ATM {

        long[] banknotes = {20, 50, 100, 200, 500}; //denomination of bank notes
        long[] stores; //counts of each kind of banknote

        /**
         * ATM Constructor
         */
        public ATM() {
            stores = new long[5]; //by default java initializes long array values to zero.
        }

        /**
         * deposit banknotes
         *
         * @param inputcounts a 1x5 array of banknote counts; one for each denomination
         */
        public void deposit(int[] inputcounts) {
            for (int i = 0; i < 5; i++) {
                this.stores[i] += inputcounts[i];
            }
        }

        /**
         * withdraw some amount
         * 
         * @param amount in dollars to withdraw
         * @return a 1x5 integer array representing the number of banknotes
         * of each denomination to dispense. While there may be multiple
         * combinations that dispense the same cash value, return the combination
         * containing the least number of banknotes overall.
         */
        public int[] withdraw(int amount) {
            int[] result = new int[5];
            for (int i = 4; i >= 0; i--) {
                int notes = (int) Math.min(amount / banknotes[i], stores[i]);
                result[i] = notes;
                amount -= notes * banknotes[i];
            }
            if (amount != 0) {
                return new int[]{-1};
            }
            return result;
        }
    }


    /**
     * @param nums an array of integers
     * @return an ArrayList containing all the positive integer values that are
     * represented twice (no more, no less) in the input array.
     */
    public static ArrayList<Integer> twice(int[] nums) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == 2 && entry.getKey() > 0) {
                result.add(entry.getKey());
            }
        }

        return result;
    }


}

