/*
 * Project: Mamluk V Knight
 * Author: github.com/AbdalrahmanS
 * Last edited: 7/16/2021
 * Brief Description: This class is where all the classes come together to make the multiplier game.
 * Extra Resources: GeneralInfo.txt, GenericArmy.java, Knight.java, Mamluk.java
 */

import java.util.*;
import java.io.*;

public class Multiplayer {
	public static void main(String[] args) {

		/*
		 * Chunk 1: Introduction This code welcomes the player into the game, it also
		 * gives them the option of choosing whether or not they want to read the
		 * instructions. A try-catch-loop is used to read the file with the instructions
		 */
		Scanner help = new Scanner(System.in);

		System.out.println("Welcome to Mamluk vs Knight");
		System.out.println("Do you want to know the instructions, type yes or no");

		Scanner input = null;
		if (help.next().equals("yes")) {
			try {
				int sentenceReader = 0;
				input = new Scanner(new BufferedReader(new FileReader("MamlukVKnight/GeneralInfo")));

				while (input.hasNextLine() && sentenceReader <= 47) {
					System.out.println(input.nextLine());
					sentenceReader++;
				}
			} catch (FileNotFoundException e) {

			}
		}

		/*
		 * Chunk 2: This is were the armies are assembled, the users are given prompts
		 * to help assemble there armies these prompts include options to name generals,
		 * armies, and to assign soldiers for each army. Then those armies are put into
		 * an array-list. The playerOneArmies contain all the armies of the Mamluks,
		 * while the playerTwoArmies contain all the cursader players. 
		 */

		Mamluk playerOne = new Mamluk();
		Knight playerTwo = new Knight();
		Mamluk[] playerOneArmies = new Mamluk[5];
		Knight[] playerTwoArmies = new Knight[5];

		Scanner generalInput1 = new Scanner(System.in);
		Scanner generalInput2 = new Scanner(System.in);

		System.out.println("\nMamluk Player");
		fullArmyNameSetup(playerOne, generalInput1, playerOneArmies);
		System.out.println("\nKnight Player");
		fullArmyNameSetup(playerTwo, generalInput2, playerTwoArmies);

		/*
		 * Chunk 3: This is the attack phase, the all encomopsing for-loop is in charge of looping the 
		 * 5 attack phases, in addition, it tells the player the current attack phase. Then there is the while-loop,
		 * this loop is in charge off each individual attack phase. It does this my looping untill the one of Field Marshal
		 * dies. In the While-loop it asks each player wheather or  not they want to use the Ace. After, inputing the 
		 * attacks from each player. The while-loop prints out each army.  Once an army dies, the final if statement comes
		 * into play, it checks to make sure that one field marshal is dead, once this check passes, then it will add
		 * all solider alive into the next army, however, if the final army is playing then once the final field marshal
		 * dies the game ends and those soliders can't be put anywhere else. Once the last Field Marshal
		 * is killed, the game ends and the programs sends a victory message to the winning player. 
		 */
		for (int i = 0; i < 5; i++) {
			System.out.println("\nAttack Phase " + (i + 1));
			while (playerOneArmies[i].getFieldMarshalHealth() > 0 && playerTwoArmies[i].getFieldMarshalHealth() > 0) {
				System.out.println("Mamluk player, would you like to use your ace? \nType yes or no");
				if (generalInput1.next().equals("yes")) {
					playerOneArmies[i].attack(playerTwoArmies[i], true);
				} else {
					playerOneArmies[i].attack(playerTwoArmies[i], false);
				}

				System.out.println("Knight player, would you like to use your ace? \nType yes or no");
				if (generalInput2.next().equals("yes")) {
					playerTwoArmies[i].attack(playerOneArmies[i], true);
				} else {
					playerTwoArmies[i].attack(playerOneArmies[i], false);
				}
				System.out.println(playerOneArmies[i].toString() + "\n" + playerTwoArmies[i].toString());

				if ((playerOneArmies[i].getFieldMarshalHealth() == 0 || playerTwoArmies[i].getFieldMarshalHealth() == 0) 
				&& i != 4) {
					playerOneArmies[i + 1].addSoldiers(playerOneArmies[i].getArmySize());
					playerTwoArmies[i + 1].addSoldiers(playerTwoArmies[i].getArmySize());
				}
			}

		}

		boolean playerOneDeath = false;
		for (int i = 0; i < 5; i++) {
			if (playerOneArmies[i].death() == true) {
				playerOneDeath = true;
			}
		}

		if (playerOneDeath == false) {
			System.out.println("Mamluks, you are victorious");
		} else {
			System.out.println("Knights, you are victorious");
		}

	}

	/*
	 * Method: static void 
	 * This method assembles the armies. It first gives the
	 * prompt to the player to give names to the general. The user inputs a line of
	 * names in the given format. This string is then split into an array. The same
	 * process works for assembling the names of the armies. The user then is given
	 * the option on how many soldiers they want to put per army. Those value are
	 * then put into an integer array. Once all those values are done, using a
	 * for-loop, those values are then used to as a parameter to create the armies.
	 * Those armies are then put into an array called armyArray (the user
	 * parameter).
	 */
	public static void fullArmyNameSetup(GenericArmy type, Scanner generalInput, GenericArmy[] armyArray) {

		String[] fieldMarshalName = new String[5];
		String[] armyName = new String[5];
		int[] soldierCountPerArmy = new int[5];

		System.out.println("Name your generals");
		System.out.println("Please format your response like this: \"Name, Name, " + "Name, Name, Name\"");
		String fieldMarshalNameString = generalInput.nextLine();
		fieldMarshalName = fieldMarshalNameString.split(", ");

		System.out.println("\nName your armies");
		System.out.println("Please format your response like this: \"Name, Name, " + "Name, Name, Name\"");
		String armyNameString = generalInput.nextLine();
		armyName = armyNameString.split(", ");

		System.out.println("\nInput how many soldiers you want in each army");
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			if (i + 1 == 1) {
				System.out.println("What is your 1st army soldier count?");
			} else if (i + 1 == 2) {
				System.out.println("What is your 2nd army soldier count");
			} else if (i + 1 == 3) {
				System.out.println("What is your 3rd army soldier count");
			} else {
				System.out.println("What is your " + (i + 1) + "th army soldier count");
			}
			int soliderTotalCount = 0;
			try {
				soliderTotalCount = generalInput.nextInt();
				sum += soliderTotalCount;
				if (sum > 50) {
					throw new MultiplayerException();
				} else {
					soldierCountPerArmy[i] = soliderTotalCount;
				}
			} catch (MultiplayerException e) {
				System.out.println("Your solider count can't exceed 50!");
			}
		}

		for (int i = 0; i < 5; i++) {
			if (type instanceof Mamluk) {
				armyArray[i] = new Mamluk(fieldMarshalName[i], armyName[i], soldierCountPerArmy[i]);
			} else {
				armyArray[i] = new Knight(fieldMarshalName[i], armyName[i], soldierCountPerArmy[i]);
			}
		}
	}
}
