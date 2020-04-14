/* CRITTERS Critter1.java
 * EE422C Project 5 submission by
 * Kangji Chen
 * kc36498
 * 16295
 * Slip days used: <0>
 * Spring 2020
 */
package assignment5;

/**
 * This critter will only walk in one direction and look to see if there is a clover it can run to, if there is a clover it can run to, 
 * or when it's energy is below 50, it will start to run in a different direction
 * It will not fight when it is walking; it will always fight when is running
 * @author Kangji Chen
 *
 */
public class Critter1 extends Critter {
	/**
	 * make this critter be represented by "1"
	 */
	@Override
    public String toString() {
        return "1";
    }
	
	private int dirWalk;
	private int dirRun;
	
	/**
	 * constructor to get a unique direction for run and one for walk, for each Critter1
	 */
	public Critter1() {
		dirWalk = Critter.getRandomInt(8);
		dirRun = Critter.getRandomInt(8);
	}
	
	/**
	 * if energy is above 50, walk 
	 * if not, run
	 */
	@Override
	public void doTimeStep() {
		
		if(getEnergy()>50&&look(dirRun, true)!="@") {
			walk(dirWalk);
		}else {
			run(dirRun);
		}
		
	}
	
	/**
	 * if energy is above 50, no fighting
	 * if not, fight
	 */
	@Override
	public boolean fight(String oponent) {

		if(getEnergy()>50) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	
	public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.BLACK;
    }

    public javafx.scene.paint.Color viewFillColor() {
        return javafx.scene.paint.Color.PINK;
    }
}



