package AdventureModel;

import Trolls.GameTroll;
import Trolls.NoteTroll;
import Trolls.Troll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 * Inspired by assignments created by Eric Roberts
 * and John Estell. Course code tailored by the CSC207
 * instructional team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class AdventureGame {
    private final boolean audible;
    private String introText; //An attribute to store the Introductory text of the game.
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    private HashMap<Integer, Room> rooms; //A list of all the rooms in the game.
    private HashMap<String,String> synonyms = new HashMap<>(); //A HashMap to store synonyms of commands.
    private String[] actionVerbs = {"QUIT","HELP","LOOK","INVENTORY","TAKE","DROP"}; //List of action verbs (other than motions) that exist in all games. Motion vary depending on the room and game.
    public Player player; //The Player of the game.

    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGame(boolean audible){
        this.synonyms = new HashMap<>();
        this.rooms = new HashMap<>();
        this.audible = audible; //audible game?
    }

    /**
     * setUpGame
     * __________________________
     * This method will do a lot of File I/O!  It should:
     *
     * 1. Populate the synonyms HashMap with data in synonyms.txt
     * 2. Populate the rooms HashMap with data in rooms.txt
     * 3. Initialize the introduction attribute with the text in introduction.txt
     * 4. Initialize the help attribute with the text in help.txt
     * 5. Place the objects in objects.txt in their initial room locations
     * 6. Assign the position of the player to the first room listed in the rooms.txt file.
     *
     * Your code should utilize the methods Room.readRoom and Object.readObject
     * to read individual Objects and Rooms from the file
     *
     * @param directoryName name of directory containing input files (corresponds to the name of the adventure)
     *
     * @throws IOException in the case of a file I/O error
     * @throws FormattingException in the case of a file formatting error
     *
     * Formatting exceptions should be received by this method
     * from Room.readRoom and Object.readObject. We ask that you
     * throw in response to the following formatting errors:
     * A. Room Numbers in files that are NOT NUMBERS.
     * B. Room Descriptions in files longer than TEN LINES.
     * C. Destination rooms for passages that are NOT NUMBERS
     * D. Location rooms for objects that are NOT NUMBERS
     * E. Location rooms for objects that do NOT EXIST
     */
    public void setUpGame(String directoryName) throws IOException, FormattingException {

        if (Files.exists(Paths.get(directoryName, "synonyms.txt"))) {
            // Initialize the BufferedReader
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(new File(directoryName, "synonyms.txt")));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        synonyms.put(key, value);
                    } else {
                        // FormattingException for improper format
                        throw new FormattingException("Improper format in synonyms.txt");
                    }
                }
            } catch (IOException e) {
                // Handle exceptions related to file I/O here
                System.out.println("An error occurred while reading synonyms.txt");
                throw e;
            } finally {
                // Close reader
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // Handle exceptions related to closing the reader here
                        System.out.println("An error occurred while closing the reader for synonyms.txt");
                    }
                }
            }
        } else {
            // If the file doesn't exist, assume there are no synonyms in the game.
            System.out.println("No synonyms file found. Continuing without synonyms.");
        }

        // Check if the rooms.txt file exists
        if (Files.exists(Paths.get(directoryName, "rooms.txt"))) {
            // Initialize the BufferedReader
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(new File(directoryName, "rooms.txt")));

                Room room;
                while ((room = Room.readRoom(directoryName, reader)) != null) {
                    int roomNumber = room.getRoomNumber();
                    rooms.put(roomNumber, room);
                }
            } catch (IOException | FormattingException e) {
                // Handle exceptions related to file I/O and formatting here
                System.out.println("An error occurred while reading rooms.txt");
                throw e;
            } finally {
                // Close reader
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // Handle exceptions related to closing the reader here
                        System.out.println("An error occurred while closing the reader for rooms.txt");
                    }
                }
            }
        } else {
            // If the file doesn't exist, print an appropriate error message
            System.out.println("No rooms file found. Cannot continue the game without rooms.");
            throw new IOException("rooms.txt file missing");
        }

        // Initialize the introduction attribute
        if (Files.exists(Paths.get(directoryName, "introduction.txt"))) {
            introText = new String(Files.readAllBytes(Paths.get(directoryName, "introduction.txt")));
        } else {
            System.out.println("No introduction file found. Cannot continue the game without an introduction.");
            throw new IOException("introduction.txt file missing");
        }

        // Initialize the help attribute
        if (Files.exists(Paths.get(directoryName, "help.txt"))) {
            helpText = new String(Files.readAllBytes(Paths.get(directoryName, "help.txt")));
        } else {
            System.out.println("No help file found. Cannot continue the game without help.");
            throw new IOException("help.txt file missing");
        }


        // Initialize objects
        if (Files.exists(Paths.get(directoryName, "objects.txt"))) {
            BufferedReader objectReader = new BufferedReader(new FileReader(new File(directoryName, "objects.txt")));
            try {
                while (true) {
                    AdventureObject.readObject(objectReader, rooms);
                }
            } catch (IOException | FormattingException e) {
                // Break out of the loop if we reach the end of the file or encounter a formatting issue
            }
            objectReader.close();
        }

        // Assign the position of the player to the first room listed in the rooms.txt file

        // Initialize the player object after rooms have been set up
        if (!rooms.isEmpty()) {
            int firstRoomNumber = rooms.keySet().iterator().next();
            Room firstRoom = rooms.get(firstRoomNumber);
            this.player = new Player(firstRoom);  // Initialize player with the first room
            this.player.setCurrentRoom(firstRoom);
        } else {
            System.out.println("No rooms have been initialized. Cannot continue the game without rooms.");
            throw new FormattingException("No rooms initialized");
        }

    }

    /**
     * convertCommand
     * __________________________
     * This method should translate strings from the user into an array of string tokens.
     * Tokens in the array should be in a standardized format (i.e. with synonyms applied).
     * For example, assume GRAB is a synonym of TAKE as indicated in the synonyms file.
     * In this case, the string command "GRAB KEYS" should be converted to the following
     * array of command tokens: {"TAKE", "KEYS"}
     *
     * Note that this method should strip white space from the beginning and end of the
     * user input string before tokenization.
     *
     * @param command use input string from the command line
     * @return a string array of tokens that represents the command.
     */
    public String[] convertCommand(String command){
        String trimmedCommand = command.trim().toUpperCase();
        String[] tokens = trimmedCommand.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (synonyms.containsKey(tokens[i])) {
                tokens[i] = synonyms.get(tokens[i]);
            }
        }
        return tokens;
    }


    /**
     * movePlayer
     * __________________________
     * Moves the player in the given direction, if possible.
     * Return false if the player wins or dies as a result of the move.
     * Else, return true (so the game can continue).
     *
     * To implement this method you will need to:
     *
     * 1. Determine the moves that are possible from a given room.
     *    If the user's move is not in the list of possible moves,
     *    print an error message and return TRUE.
     *    The player will then remain in the same room and the game will continue.
     * 3. If the move is in the list of possible moves, and the path is BLOCKED
     *    by a missing object, determine if the user has an OBJECT required to
     *    remove the BLOCK:
     *    -- If YES, set the current room to the destination made accessible by the OBJECT.
     *    -- If NO, check for an UNBLOCKED path and continue.
     * 3. If the move is in the list of possible moves, and the path is BLOCKED
     *    by a TROLL:
     *    -- If the game is "audible" Instantiate a SoundTroll; if not Instantiate the Troll
     *       of your choice (a NoteTroll or GameTroll).
     *    -- Call the TROLL's "playGame" method.
     *    -- If "playGame" returns FALSE, the player has LOST. This method should return TRUE.
     *       The player will remain in the same room and the game will continue.
     *    -- If "playGame" returns TRUE, set the current room to the destination made accessible by the WIN.
     * 4. If the move is in the list of possible moves, and the path is NOT BLOCKED,
     *    set the current room to the destination associated with the move.
     * 5. Finally, If the current room has changed, check to see if the move from this new room is "FORCED".
     *    -- If yes, print the description of the room and mark it as "visited"
     *    -- Set the current room to the destination that is FORCED
     *    -- If this destination == 0, return FALSE (meaning the player has died or won).
     *    -- Otherwise, return TRUE  (the player will proceed from the new current room).
     *
     * @param direction the move command
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction) {
        // Step 1: Check if the move is possible from the current room
        Room currentRoom = this.player.getCurrentRoom();
        List<Passage> availablePassages = currentRoom.getPassageTable().getPassages();
        Passage selectedPassage = null;

        for (Passage passage : availablePassages) {
            if (passage.getDirection().equalsIgnoreCase(direction)) {
                selectedPassage = passage;
                break;
            }
        }

        if (selectedPassage == null) {
            System.out.println("Invalid move. Try again.");
            return true;
        }

        // Step 2: Handle locked passages requiring a key
        if (selectedPassage.getIsBlocked() && selectedPassage.getKeyName() != null) {
            boolean hasKey = false;
            for (AdventureObject obj : this.player.inventory) {
                if (obj.getName().equalsIgnoreCase(selectedPassage.getKeyName())) {
                    hasKey = true;
                    break;
                }
            }

            if (!hasKey) {
                System.out.println("You need the key: " + selectedPassage.getKeyName() + " to proceed.");
            }
        } else if (selectedPassage.getIsBlocked() && selectedPassage.getKeyName() == null) {
            // Step 3: Handle blocked passages with a Troll
            Troll troll = this.audible ? new NoteTroll() : new GameTroll();  // Instantiate Troll
            boolean gameResult = troll.playGame();

            if (!gameResult) {
                System.out.println("You lost to the troll. Try again.");
                return true;
            }
        }

        // Step 4: Make move
        Room newRoom = this.rooms.get(selectedPassage.getDestinationRoom());
        this.player.setCurrentRoom(newRoom);
        newRoom.setVisited();  // Mark the room as visited
        System.out.println(newRoom.getDescription());

        // Step 5: Handle forced moves
        if (newRoom.getRoomNumber() == 0) {  // If the room number is 0, it's a forced move
            return false;  // End the game
        }

        return true;  // Continue the game
    }


    /**
     * executeAction
     * __________________________
     * Given a string, check if it is a valid action.
     * Then, perform the action.  Return true if the game continues, else false if it is over.
     *
     * To implement this method you will first convert the input to a standard vocabulary
     * by consulting the synonyms table.
     *
     * If the player's command is:
     * 1. QUIT: print "GAME OVER"
     * 2. HELP: print the help text
     * 3. LOOK: print the description of the room
     * 4. INVENTORY: print the items in the player's inventory items or "INVENTORY EMPTY",
     *    if the player has no objects
     * 5. TAKE <obj>:
     * If a player does not provide an object, print "THE TAKE COMMAND REQUIRES AN OBJECT".
     * If a player provides an object that exists in the room, print "<obj> HAS BEEN TAKEN"
     * If a player provides an object that does not exist in the room, print "<obj> IS NOT IN ROOM"
     * Replace "<obj>" with the name of the object in the strings above.
     * If an object is taken as a result of this command, add the object to the player's inventory.
     * 6. DROP <obj>:
     * If a player does not provide an object, print "THE DROP COMMAND REQUIRES AN OBJECT".
     * If a player provides an object that exists in their inventory, print "<obj> HAS BEEN DROPPED"
     * If a player provides an object that does not exist in their inventory, print "<obj> IS NOT IN INVENTORY"
     * Replace "<obj>" with the name of the object in the strings above.
     * If an object is dropped as a result of this command, remove it from the player's inventory and add it to the current room.
     * 7. Any other commands:
     * Delegate the command to the movePlayer method.
     * If the result is true, the move is valid and the player may proceed;
     * If the result is false, print "GAME OVER" (and return FALSE);
     *
     * @param command: String representation of the command.
     * @return true, if game can continue after the move is complete.  Else, false.
     */
    public boolean executeAction(String command){

        //first, look up synonyms and convert the user's input string to standard tokens
        String[] inputArray = convertCommand(command);

        String action = inputArray[0].toUpperCase();
        String object = inputArray.length > 1 ? inputArray[1] : null;

        switch (action) {
            case "QUIT":
                System.out.println("GAME OVER");
                return false;
            case "HELP":
                System.out.println(getHelpText());
                return true;
            case "LOOK":
                System.out.println(getPlayer().getCurrentRoom().getDescription());
                return true;
            case "INVENTORY":
                if (getPlayer().inventory.isEmpty()) {
                    System.out.println("INVENTORY EMPTY");
                } else {
                    System.out.println("Inventory: " + getPlayer().inventory.toString());
                }
                return true;
            case "TAKE":
                if (object == null) {
                    System.out.println("THE TAKE COMMAND REQUIRES AN OBJECT");
                } else if (getPlayer().takeObject(object)) {
                    System.out.println(object + " HAS BEEN TAKEN");
                } else {
                    System.out.println(object + " IS NOT IN ROOM");
                }
                return true;
            case "DROP":
                if (object == null) {
                    System.out.println("THE DROP COMMAND REQUIRES AN OBJECT");
                } else {
                    int initialSize = getPlayer().inventory.size();
                    getPlayer().dropObject(object);
                    if (getPlayer().inventory.size() < initialSize) {
                        System.out.println(object + " HAS BEEN DROPPED");
                    } else {
                        System.out.println(object + " IS NOT IN INVENTORY");
                    }
                }
                return true;
            default:
                boolean result = movePlayer(action);
                if (!result) {
                    System.out.println("GAME OVER");
                    return false;
                }
                return true;
        }

    }

    /**
     * playGame
     * __________________________
     * This function is the Game Loop.
     *
     * It keeps track of the player's input and performs actions requested by the player.
     * The game loop ends when the player types "QUIT" or when the player dies.
     */
    public void playGame(){

        System.out.println(this.introText); //print out the introduction
        String input = "";
        while(true){
            if (!input.equals("LOOK") && !input.equals("L")) { //if the command is to LOOK, printing the description is redundant.
                System.out.println(this.player.getCurrentRoom().getDescription()); //print where we are to the console
                this.player.getCurrentRoom().articulateDescription(); //state where we are as well, audibly
            }
            this.player.getCurrentRoom().setVisited(); //mark the room as visited
            if (this.player.getCurrentRoom().hasObjects()) { //are there objects here?
                System.out.println("The following object(s) are here:");
                this.player.getCurrentRoom().printObjects();
            }
            System.out.print("> "); //prompt for the user
            Scanner scanner = new Scanner(System.in); //read input from the user
            input = scanner.nextLine().toUpperCase(); //convert input to upper case
            if (this.audible) this.player.getCurrentRoom().stopDescription(); //stop voice, we are moving on!
            if (!executeAction(input)) return; //execute the command, if possible.
        }
    }

    /**
     * Getters and Setters
     * __________________________
     * Some potentially useful getter and setter methods for class attributes are below.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public void setRooms(HashMap<Integer, Room> rooms) { this.rooms = rooms; }
    public HashMap<Integer, Room> getRooms() { return rooms;}
    public void setIntroText(String introText) {
        this.introText = introText;
    }
    public String getIntroText() {
        return this.introText;
    }
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }
    public String getHelpText() {
        return this.helpText;
    }
    public void setSynonyms(HashMap<String, String> synonyms) {
        this.synonyms = synonyms;
    }
    public HashMap<String, String> getSynonyms() {
        return this.synonyms;
    }

}

