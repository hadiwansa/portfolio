package AdventureModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Room Class.
 * This class contains the information about a room in the Adventure Game.
 * Inspired by assignments created by Eric Roberts
 * and John Estell. Course code tailored by the CSC207
 * instructional team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class Room {

    private String adventureName; //The name of the adventure, also name of room asset folder (for sounds).
    private int roomNumber; //The number of the room.
    private String roomName; //The name of the room.
    private String roomDescription; //The description of the room.
    private PassageTable passageTable = new PassageTable(); //The passage table for the room.
    private boolean isVisited; //A boolean to store if the room has been visited or not
    private Clip clip;
    public ArrayList<AdventureObject> objectsInRoom = new ArrayList<AdventureObject>(); //The list of objects in the room.

    /**
     * Room constructor.
     * __________________________
     * @param roomName The name of the room.
     * @param roomNumber The number of the room.
     * @param roomDescription The description of the room.
     * @param adventureName The name of the adventure, which corresponds to the name of the folder in
     *                      which audio descriptions of rooms can be found.
     */
    public Room(String roomName, int roomNumber, String roomDescription, String adventureName){
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.adventureName = adventureName;
        this.isVisited = false;
        this.clip = null;
    }


    /**
     * readRoom
     * __________________________
     * Read a single room from the BufferedReader.
     *
     * @param buff the BufferedReader pointing to the rooms file
     * @param adventureName the name of the adventure.  This corresponds to the
     *                      name of the folder in which the adventure assets are located.
     *                      Each room has audio files in this folder! You will need this information
     *                      to use the Room constructor.
     *
     * @return the room object that has been read
     * @throws FormattingException if formatting error occurs
     * @throws IOException if a file I/O error occurs
     * We ask that this method throw errors in response to the
     * following formatting errors, specifically:
     * A. Room Numbers in files that are NOT NUMBERS.
     * B. Room Descriptions in files longer than TEN LINES.
     * C. Destination rooms for passages that are NOT NUMBERS
     */
    public static Room readRoom(String adventureName, BufferedReader buff) throws IOException, FormattingException {
        String roomNumberStr = buff.readLine();
        if (roomNumberStr == null) {
            return null;
        }
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr);
        } catch (NumberFormatException e) {
            throw new FormattingException("Room number is not a valid number.");
        }

        String roomName = buff.readLine();
        if (roomName == null) {
            throw new FormattingException("Room name missing.");
        }

        StringBuilder roomDescription = new StringBuilder();
        String line;
        int lineCount = 0;
        while (!(line = buff.readLine()).equals("-----")) {
            roomDescription.append(line).append("\n");
            lineCount++;
            if (lineCount > 10) {
                throw new FormattingException("Room description is longer than ten lines.");
            }
        }

        Room room = new Room(roomName, roomNumber, roomDescription.toString().strip(), adventureName);

        while ((line = buff.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split("\\s+");
            if (parts.length < 2) {
                throw new FormattingException("Incomplete passage information.");
            }

            String direction = parts[0];
            String destination = parts[1];
            int destinationNumber;
            String key = null;

            try {
                if (destination.contains("/")) {
                    String[] destinationParts = destination.split("/");
                    destinationNumber = Integer.parseInt(destinationParts[0]);
                    key = destinationParts[1];
                } else {
                    destinationNumber = Integer.parseInt(destination);
                }
            } catch (NumberFormatException e) {
                throw new FormattingException("Destination room for passage is not a valid number.");
            }

            Passage passage = (key == null) ? new Passage(direction, String.valueOf(destinationNumber)) : new Passage(direction, String.valueOf(destinationNumber), key);
            room.passageTable.addDirection(passage);
        }

        return room;
    }

    /**
     * addObject
     * __________________________
     * This method adds a game object to the room.
     *
     * @param object to be added to the room.
     */
    public void addObject(AdventureObject object){
        this.objectsInRoom.add(object);
    }

    /**
     * removeObject
     * __________________________
     * This method removes a game object from the room.
     *
     * @param object to be removed from the room.
     */
    public void removeObject(AdventureObject object){
        this.objectsInRoom.remove(object);
    }

    /**
     * setVisited
     * __________________________
     * This method sets the isVisited attribute to true.
     */
    public void setVisited(){
        this.isVisited = true;
    }

    /**
     * getVisited
     * __________________________
     * Getter for isVisited attribute.
     */
    public boolean getVisited(){
        return this.isVisited;
    }

    /**
     * getRoomName
     * __________________________
     * Getter method for the name attribute.
     *
     * @return name of the room
     */
    public String getRoomName(){
        return this.roomName;
    }

    /**
     * getRoomNumber
     * __________________________
     * Getter method for the number attribute.
     *
     * @return number of the room
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }

    /**
     * getDescription
     * __________________________
     *
     * @return long description of the room if not visited, else short description.
     */
    public String getDescription(){
        if (!this.isVisited) return this.roomDescription;
        else return "You are here: " + this.roomName +".";
    }

    /**
     * getPassageTable
     * __________________________
     * Getter method for the PassageTable.
     *
     * @return passage table of the room
     */
    public PassageTable getPassageTable(){
        return this.passageTable;
    }

    /**
     * hasObjects
     * __________________________
     * Determines if the room has objects
     *
     * @return true if the room contains objects, else false
     */
    public boolean hasObjects() {
        if (this.objectsInRoom.size() > 0) return true;
        else return false;
    }

    /**
     * printObjects
     * __________________________
     * Pretty print the names of objects in the room.
     */
    public void printObjects() {
        for (AdventureObject o: this.objectsInRoom)
            System.out.println(o.getDescription());
    }

    /**
     * articulateDescription
     * __________________________
     * Use this method to make the description of the given room audible.
     */
    public void articulateDescription() {

        try {
            String filename;

            //shall we play the long or the short audio description of this room?
            if (!this.isVisited) filename = "./" + this.adventureName + "/sounds/" + this.roomName.toLowerCase() + "-long.wav" ;
            else filename = "./" + this.adventureName + "/sounds/" + this.roomName.toLowerCase() + "-short.wav" ;

            filename = filename.replace(" ","-");

            this.clip = AudioSystem.getClip(); //We can use this to play a .wav file
            this.clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            this.clip.start();

        } catch (Exception e) {
            System.out.println(e.getMessage()); //what happened?
        }

        return;
    }

    /**
     * stopDescription
     * __________________________
     * Use this method to stop voicing the description of the given room.
     * You will need want to stop the clip whenever you exit a room, or else
     * you'll end up with a cacophony of sounds!
     */
    public void stopDescription() {
        if (this.clip != null) this.clip.stop();
    }
}
