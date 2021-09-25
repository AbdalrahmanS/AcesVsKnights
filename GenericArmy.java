/*
 * Project: Mamluk V Knight
 * Author: github.com/AbdalrahmanS
 * Last edited: 7/15/2021
 * Brief Description: It's important that your read the GeneralInfo.txt file to get a general
 * view of the game, before diving into the code. 
 * An instance of this class isn't possible, but it's subclasses can have instances created.
 * This class is essentially made to mimic the basic concepts of an army, it's created to be 
 * similar to linked a list. In the sense that soldiers are nodes, and the entire list of nodes
 * is called the army. The head node is called the field marshal.
 * Extra Resources: GeneralInfo.txt
 */

public abstract class GenericArmy {
	
	/*
	 * Variable : Use
	 * fieldMarshal: this variable acts as the head node
	 * fieldMarshalName: This is just the name of the fieldMarshal
	 * armyName: This is the name of the army
	 * armySize: This is the size of the army
	 */
	private Soldier fieldMarshal = null;
	private String fieldMarshalName = "";
	private String armyName = "";
	private int armySize = 0;
	private int aceCount = 3;
	
	/*
	 * Class: subclass
	 * With a linked list, there is usually a private node class that holds the data.
	 * That same principle applies here, the node is just changed to "Soldier", the data 
	 * is the soldier's attack, defense, and health. Similar to other node classes, there is a
	 * "next" object, that is meant to represent the next node. Same principle, different name, the
	 * next of this Subclass is variable "nextSolider". The variables uses are as followed
	 * attack: This will be used to reduce the health of other soldier nodes
	 * defense: This will be used to lower the attack from other soldier nodes 
	 * health: This is the health of a soldier, once this hits zero, the soldier is essentially
	 * dead, in the context of this game, this means the soldier can't attack.
	 * number: This is just the number of the soldier, when an army is generated, each soldier
	 * is assigned a number. It's important to note that the field marshal's number will always be
	 * 0
	 */
	private class Soldier {
		int attack;
		int defense;
		int health;
		int number;
		Soldier nextSolider = null;
		Soldier(int a, int d, int h) {
			attack = a;
			defense = d;
			health = h;
		}
		
	}

	/*
	 * Method: mutator
	 * This method sets attack, defense, and health for the fieldMarshal (head) node. It also
	 * sets the number for the field marshal, which is 0.
	 */
	public void setFieldMarshal(int a, int d, int h) {
		fieldMarshal = new Soldier(a, d, h);
		fieldMarshal.number = 0;
	}
	
	/*
	 * Method: mutator
	 * This method sets the field Marshal's name
	 */
	public void setfieldMarshalName(String name) {
		String temp = name;
		fieldMarshalName = temp;
	}
	
	
	/*
	 * Method: mutator
	 * This method sets the name of the army
	 */
	public void setArmyName(String name) {
		String temp = name;
		this.armyName = temp;
	}
	
	/*
	 * Method: accessor
	 * This returns the name of the field Marshal 
	 */
	public String getFieldMarshalName() {
		String temp = this.fieldMarshalName;
		return temp;
	}
	
	/*
	 * Method: accessor
	 * This returns the name of the army
	 */
	public String getArmyName() {
		String temp = this.armyName;
		return temp;
	}
	
	/*
	 * Method: accessor
	 * This returns the size of the army, before returning the size, it does a sizeAdjust, this
	 * allows the armysize to take account of dead soldiers
	 */
	public int getArmySize() {
		sizeAdjust();
		return this.armySize;
	}
	
	/*
	 * Method: accessor
	 * This returns the amount of Aces left
	 */
	public int getAceCount() {
		return this.aceCount;
	}
	
	/*
	 * Method: accessor
	 * This returns the health of the field marshal
	 */
	public int getFieldMarshalHealth() {
		return this.fieldMarshal.health;
	}
	
	/*
	 * Method: accessor
	 * This method returns the health of a soldier, this method is a bit
	 * strange because it needs an input, the input is just the soldier number.
	 */
	public int getSoliderHealth(int soliderNumber) {
		Soldier current = this.fieldMarshal;
		while (soliderNumber != 0) {
			current = current.nextSolider;
			soliderNumber--;
		}
		return current.health;
	}
	
	/*
	 * Method: void
	 * This method receives an input meant to be the number of soldiers desired in an army. Given
	 * an input of 5, in will generate an army with 5 soldiers, not including the field marshal.
	 * This method starts with creating a Soldier node that is set to the fieldMarshal head node.
	 * From there, a for-loop is used to generate the army: first, the current soldier node changes
	 * its .nextSolider from null into a new soldier with the attack, defense, and health of the
	 * field marshal. Then the .nextSolider is given the previous number of the soldier + 1. Then
	 * the current soldier node is changed to the next soldier node in the army. 
	 */
	public void generateArmy(int soliderCount) {
		armySize = soliderCount;
		Soldier current = fieldMarshal; 
		for (int i = 0; i < soliderCount; i++) {
			current.nextSolider = new Soldier(fieldMarshal.attack, fieldMarshal.defense, 
					fieldMarshal.health);
			current.nextSolider.number = i + 1;
			current = current.nextSolider;
		}
	}
	
	/*
	 * Method: void
	 * This method adds soldiers to the army. This code works by first moving the
	 * current sold node to the last soldier.
	 */
	public void addSoldiers(int count) {
		if (count != 0) {
			Soldier current = this.fieldMarshal;
			while (current.nextSolider != null) {
				current = current.nextSolider;
			}
			
			while (count != 0 && count > 0) {
				current.nextSolider = new Soldier(fieldMarshal.attack, 
					fieldMarshal.defense, fieldMarshal.health);
				this.armySize++;
				current.nextSolider.number = this.armySize;
				count--;
				current = current.nextSolider;
			}
		}
	}
	
	/*
	 * Method: void
	 * This method adjusts the size to reflect the size of an army once soldiers die
	 */
	private void sizeAdjust() {
		Soldier current = this.fieldMarshal;
		while (current != null) {
			if (current.health == 0) {
				this.armySize -= 1;
			}
			current = current.nextSolider;
		}
	}
	/*
	 * Method: void
	 * Important note: the the public main attack method, however, it mostly redirects to private
	 * attack method, if you want to further understand how the attack in this game works, check
	 * the private baseAttack method. This method receives two variables: the first variable, is a
	 * GenericArmy object, this represents the army that is to be attacked. In this game, each soldier
	 * of one army, attacks another soldier in the opposing army. The other variable, is a boolean
	 * called ace, if this boolean is TRUE, then the chance of a direct attack landing goes from 50%
	 * to 83%. Before, attacking, the method does a checking by running the death() method,
	 * it is essentially checking to see if the field marshal node's health is bellow 0, if the 
	 * health is bellow 0, then the army can't attack, and for all intensive purposes is dead. If
	 * the army is dead, then it will print out a message instead of attacking. If the health is 
	 * above zero, then an integer variable called numDecider will receive a random number between
	 * 1-6 from the numberGenerator() method. Then a Soldier node called current is generated to
	 * represent the invader's field marshal head node. If the ace is false, then it's forwarded
	 * to the base attack method with integer 3, if the ace boolean is true, then the integer 5, 
	 * is then forwarded to the base attack private method. These number represents a probability of
	 * a direct hit landing. 
	 */
	public void attack(GenericArmy invader, boolean ace) {
		if (death() == false) {
			int numDecider = numberGenerator();
			Soldier current = invader.fieldMarshal;
			if (ace == true && aceCount > 0) {
				aceCount--;
				baseAttack(current, numDecider, 5);
			} else {
				baseAttack(current, numDecider, 3);
			}
		} else {
			sizeAdjust();
			System.out.println("DEAD ARMIES CAN'T ATTACK");
		}
	}
	
	/*
	 * Method: private void
	 * This is the main attack method, this method receives 3 variables: the first is the soldier
	 * node current, that will mostly likely be the opposing (army being attacked) army's 
	 * field marshal head node, the second variable is the numDecider integer value, this is a
	 * random integer value, generated from the public attack method, and the last variable
	 * represents the probability, how this works will be explained later on. So this method
	 * is essentially a while-loop, that keeps in going until the current equals null, this
	 * indicates that there are no more soldiers to be attacked. In the while-loop, there
	 * is an if-else statement. This is where the probability come in, in the normal mode, the max
	 * probability is 3, so since the numDecider has to be between 1-6, if the probability is 3,
	 * it creates a 50% chance of hitting a direct attack, in ace mode, the chance of hitting a
	 * direct attack is 83%, this is because the probability goes to 5. So, if the probability lands
	 * in favor of the attacker, then the health of the opposing soldier decreases by the attack
	 * of the current soldier. Remember, how this attack method works is that each soldier in 
	 * the opposing army attacked. Essentially, the while-loop loops through all the soldiers
	 * decreasing their health. If the probability falls in favor of the attacker, then the soldiers
	 * of the opposing army have their health decreased by the attack of attacker's 
	 * the field marshal. If the probability is in the favor of the defender, then the attack of 
	 * attacker's field marshal is first decreased by the defenders defense, then the rest of 
	 * the attack goes to the deffender's health. The armyCount variable makes sure
	 * that each soldier only attacks another soldier. It does this by stopping the
	 * while-loop from counting attacking the opposing armies soldiers if the attacking
	 * army is smaller then the larger one, basically if and army of 3 attacks an army
	 * of 5,only 3 soldiers of the army of the 5 can be attacked. 
	 * 	
	 * 
	 */
	private void baseAttack(Soldier current, int numDecider, int probability) {
		int armyCount = 0;
		while(current != null && armyCount <=  armySize) {
			if (numDecider <= probability) {
				current.health -= fieldMarshal.attack;
			} else {
				current.health -= (fieldMarshal.attack - current.defense);
			}
			armyCount ++;
			current = current.nextSolider;
		}
	}
	
	
	/*
	 * Method: string
	 * This is the toString method for this class. First, a string called entireArmy is created, then
	 * the name of the army is first added to that string. Then a string of the filed marshal is added.
	 * However, this done via a private method called assembleSoliderString. This method just formats
	 * the soldier string. Then a while loop is created to add each soldier and their number to the
	 * entire army string. Once everything is compiled, the toString method returns the entireArmy
	 * string. 
	 */
	public String toString() {
		healthAdjust();
		String entireArmy = this.armyName + "\n";
		entireArmy += assembleSoliderString(fieldMarshal) + "\n";
		Soldier current = fieldMarshal.nextSolider;
		while(current != null) {
			entireArmy += assembleSoliderString(current) + "\n";
			current = current.nextSolider;
		}
		return entireArmy;
	}
	
	/*
	 * Method: boolean
	 * This method checks the health of the field marshal, once the the health of the field marshal
	 * hits zero, the death boolean will be true, meaning the army is dead, and can no longer attack.
	 */
	public boolean death() {
		sizeAdjust();
		if (fieldMarshal.health > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * Method: private string
	 * This method is pretty simple, the input for this method is the soldier that is 
	 * picked to make a string. First, a variable called soliderString is created, this variable
	 * will contain the complete string. First, there is an if-else-statement this is to see
	 * if the soldier is a field marshal, since there is a special format for the field marshal.
	 * Once that is completed all other stuff for this string is created. The format is
	 * Soldier #: health = ### attacked = ### defense = ###
	 *  
	 */
	private String assembleSoliderString(Soldier person) {
		String soliderString = "";
		if (person.number != 0) {
			soliderString += "Solider " + person.number;
		} else {
			soliderString += "Field Marshal " + getFieldMarshalName();
		}
		
		soliderString += ": health = " + person.health;
		soliderString += " attack = " + person.attack;
		soliderString += " defence = " + person.defense;
		return soliderString;
	}
	
	/*
	 * Method: private integer
	 * This method generates a number between 1 and 6. 
	 */
	private int numberGenerator() {
		return (int) (Math.random() * 6) + 1;
	}
	
	/*
	 * Method: private void
	 * Once the health of an army goes bellow zero, essentially into the negatives, this method
	 * changes the health of all the soldiers to zero. 
	 */
	private void healthAdjust() {
		if (fieldMarshal.health < 0) {
		Soldier current = this.fieldMarshal;
			while(current != null) {
				current.health = 0;
				current = current.nextSolider;
			}
		}
	}
}
