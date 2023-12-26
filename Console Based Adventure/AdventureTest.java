import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import AdventureModel.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class AdventureTest.
 * Course code from the CSC207 instructional
 * team at UTM, contributors to tests include:
 *
 * @author iselein
 * @author anshag01
 *  */
public class AdventureTest {
    @Test
    void roomPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals(12, game.getRooms().size());
    }

    @Test
    void introPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertTrue(game.getIntroText().length() > 0);
    }

    @Test
    void helpPopulatedTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertTrue(game.getHelpText().length() > 0);
    }

    @Test
    void createCommandTest() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] command = game.convertCommand("Q");
        assertEquals("QUIT", command[0]);

        String[] command2 = game.convertCommand("F J");
        assertEquals("FRIENDLY", command2[0]);
        assertEquals("JAVA", command2[1]);
    }

    @Test
    void basicSynonymTest() throws IOException, FormattingException {
        HashMap<String, String> expectedSynonyms = new HashMap<String, String>();
        expectedSynonyms.put("F", "FRIENDLY");
        expectedSynonyms.put("T", "TINY");
        expectedSynonyms.put("B", "BIG");
        expectedSynonyms.put("J", "JAVA");
        expectedSynonyms.put("W", "WEST");
        expectedSynonyms.put("E", "EAST");
        expectedSynonyms.put("N", "NORTH");
        expectedSynonyms.put("S", "SOUTH");
        expectedSynonyms.put("U", "UP");
        expectedSynonyms.put("D", "DOWN");
        expectedSynonyms.put("Q", "QUIT");
        expectedSynonyms.put("I", "INVENTORY");
        expectedSynonyms.put("L", "LOOK");
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals(expectedSynonyms, game.getSynonyms());
    }

    @Test
    void initialPlayerRoomSet() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        assertEquals("Outside building", game.player.getCurrentRoom().getRoomName());
    }

    @Test
    void testConvertCommand() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] expected = {"TAKE", "KEYS"};
        assertArrayEquals(expected, game.convertCommand("TAKE KEYS"));
    }

    @Test
    void testConvertCommandWithSynonyms() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        String[] expected = {"TINY"};
        assertArrayEquals(expected, game.convertCommand("t"));
    }

    @Test
    void testMovePlayerValidMove() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        boolean result = game.movePlayer("in");
        assertTrue(result);
        assertEquals(3, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovePlayerLockedPassage() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        boolean result = game.movePlayer("south");
        assertEquals(4, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testMovePlayerLockedPassageWithKey() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN"); // room 3
        game.player.takeObject("KEYS"); // pick keys
        game.movePlayer("OUT"); // room 1
        game.movePlayer("SOUTH"); // room 4
        game.movePlayer("SOUTH"); // room 5
        game.movePlayer("SOUTH"); // room 6
        game.movePlayer("DOWN"); // room (expected room 8)
        assertEquals(8, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testReadRoom() throws IOException, FormattingException {
        BufferedReader buff = new BufferedReader(new FileReader("SmallGame/rooms.txt"));
        Room room = Room.readRoom("SmallGame", buff);
        assertEquals(1, room.getRoomNumber());
        assertEquals("Outside building", room.getRoomName());
        String intro = "You are standing at the end of a road before a small brick\n"
            + "building.  A small stream flows out of the building and\n"
            + "down a gully to the south.  A road runs up a small hill\n"
            + "to the west.";
        assertEquals(intro, room.getDescription().strip());
        assertEquals(8, room.getPassageTable().getPassages().size());
    }

    @Test
    void testReadObject() throws IOException, FormattingException {
        // populate the rooms array first
        BufferedReader roomsBuff = new BufferedReader(new FileReader("SmallGame/rooms.txt"));
        BufferedReader buff = new BufferedReader(new FileReader("SmallGame/objects.txt"));
        HashMap<Integer, Room> rooms = new HashMap<>();
        while (roomsBuff.ready()) {
            Room room = Room.readRoom("SmallGame", roomsBuff);
            rooms.put(room.getRoomNumber(), room);
        }
        AdventureObject.readObject(buff, rooms);
        Room room = rooms.get(3);
        assertEquals(1, room.objectsInRoom.size());
    }

    @Test
    void testTakeObject() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        boolean result = game.player.takeObject("KEYS");
        assertTrue(result);
        assertEquals(1, game.player.inventory.size());
        assertEquals("KEYS", game.player.inventory.get(0).getName());
        assertFalse(game.player.getCurrentRoom().objectsInRoom.contains("KEYS"));
    }

    @Test
    void testDropObject() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        game.movePlayer("IN");
        game.player.takeObject("KEYS");
        game.movePlayer("OUT");
        game.player.dropObject("KEYS");
        assertEquals(0, game.player.inventory.size());
        assertFalse(game.player.getCurrentRoom().objectsInRoom.contains("KEYS"));
    }

    @Test
    void testSetUpGame() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        HashMap<String, String> expectedSynonyms = new HashMap<String, String>();
        expectedSynonyms.put("F", "FRIENDLY");
        expectedSynonyms.put("T", "TINY");
        expectedSynonyms.put("B", "BIG");
        expectedSynonyms.put("J", "JAVA");
        expectedSynonyms.put("W", "WEST");
        expectedSynonyms.put("E", "EAST");
        expectedSynonyms.put("N", "NORTH");
        expectedSynonyms.put("S", "SOUTH");
        expectedSynonyms.put("U", "UP");
        expectedSynonyms.put("D", "DOWN");
        expectedSynonyms.put("Q", "QUIT");
        expectedSynonyms.put("I", "INVENTORY");
        expectedSynonyms.put("L", "LOOK");

        assertEquals(expectedSynonyms, game.getSynonyms());

        assertFalse(game.getRooms().isEmpty());

        assertFalse(game.getIntroText().isEmpty());

        assertFalse(game.getHelpText().isEmpty());

        assertNotNull(game.player.getCurrentRoom());
    }

    @Test
    void testConvertCommandWhitespaceAndMultipleSynonyms() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        String[] expected = {"FRIENDLY", "JAVA", "EAST", "UP"};
        assertArrayEquals(expected, game.convertCommand("  F J E U "));
    }

    @Test
    void testMovePlayerSimpleMoveSouth() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");
        boolean result = game.movePlayer("SOUTH");
        assertTrue(result);  // Game should continue after the move
        assertEquals(4, game.player.getCurrentRoom().getRoomNumber());
    }

    @Test
    void testExecuteActionTakeAndDrop() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        game.movePlayer("IN");

        boolean continueGame = game.executeAction("TAKE KEYS");
        assertTrue(continueGame);
        assertTrue(game.getPlayer().inventory.stream().anyMatch(obj -> obj.getName().equalsIgnoreCase("KEYS")));

        continueGame = game.executeAction("DROP KEYS");
        assertTrue(continueGame);
        assertFalse(game.getPlayer().inventory.stream().anyMatch(obj -> obj.getName().equalsIgnoreCase("KEYS")));
        assertTrue(game.getPlayer().getCurrentRoom().objectsInRoom.stream().anyMatch(obj -> obj.getName().equalsIgnoreCase("KEYS")));
    }


    @Test
    void testTakeNonExistentObject() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        game.movePlayer("IN");

        boolean taken = game.getPlayer().takeObject("PENCIL");
        assertFalse(taken);
    }

    @Test
    void testDropObjectNotInInventory() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        int initialInventorySize = game.getPlayer().inventory.size();
        game.getPlayer().dropObject("PENCIL");
        int postDropInventorySize = game.getPlayer().inventory.size();

        assertEquals(initialInventorySize, postDropInventorySize);
    }

    @Test
    void testReadObjectKeys() throws IOException, FormattingException {
        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        BufferedReader bufferedReader = new BufferedReader(new FileReader("SmallGame/objects.txt"));

        AdventureObject.readObject(bufferedReader, game.getRooms());
        Room roomForKey = game.getRooms().get(3);
        boolean hasKeys = false;
        for (AdventureObject obj : roomForKey.objectsInRoom) {
            if (obj.getName().equals("KEYS")) {
                hasKeys = true;
                break;
            }
        }

        assertTrue(hasKeys);

        bufferedReader.close();
    }

    @Test
    void testReadRoomEndOfRoad() throws IOException, FormattingException {

        AdventureGame game = new AdventureGame(true);
        game.setUpGame("SmallGame");

        BufferedReader bufferedReader = new BufferedReader(new FileReader("SmallGame/rooms.txt"));

        Room.readRoom("SmallGame", bufferedReader);

        Room endOfRoadRoom = Room.readRoom("SmallGame", bufferedReader);

        assertEquals(2, endOfRoadRoom.getRoomNumber());
        assertEquals("End of road", endOfRoadRoom.getRoomName());

        bufferedReader.close();
    }

}

