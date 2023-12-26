package AdventureModel;

import java.util.ArrayList;

/**
 * Player Class.
 * This class keeps track of the progress of the player in the game.
 * Code inspired by assignments created by Eric Roberts
 * and John Estell. Code tailored by the CSC207
 * instructional team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class Player {
    private Room currentRoom; //The current room that the player is located in.
    public ArrayList<AdventureObject> inventory; //The list of items that the player is carrying at the moment.

    /**
     * Player Constructor
     * __________________________
     * Initializes attributes
     *
     * @param currentRoom the room in which the Player begins the game
     */
    public Player(Room currentRoom) {
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
    }

    /**
     * takeObject
     * _________________________
     * This method adds an object to a player's inventory (and removes it from the room)
     * if the object is present in the room.  It then returns true.
     * If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to take
     * @return true if object is taken, false otherwise
     */
    public boolean takeObject(String object){
        for (AdventureObject obj : currentRoom.objectsInRoom) {
            if (obj.getName().equals(object)) {
                // Remove object from the current room
                currentRoom.removeObject(obj);

                // Add object to the player's inventory
                this.inventory.add(obj);

                return true; // Successfully taken
            }
        }
        return false; // Object not found in the room
    }

    /**
     * dropObject
     * _________________________
     * This method removes an object from the inventory of the player, if it exists.
     * The object, once dropped, should be added to the current room.
     * If the object is not in the inventory, this method will do nothing.
     *
     * @param s String name of prop or object to be removed to the inventory.
     */
    public void dropObject(String s) {
        AdventureObject objectToDrop = null;
        // Loop through the inventory to find the object to drop
        for (AdventureObject obj : inventory) {
            if (obj.getName().equals(s)) {
                objectToDrop = obj;
                break;
            }
        }
        // If found, remove it from the inventory and add it to the room
        if (objectToDrop != null) {
            inventory.remove(objectToDrop);
            currentRoom.addObject(objectToDrop);
        }
    }



    /**
     * setCurrentRoom
     * _________________________
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * getCurrentRoom
     * _________________________
     * Getter method for the current room attribute.
     *
     * @return current room the player is in.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

}
