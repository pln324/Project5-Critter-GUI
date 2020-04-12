/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Spring 2020
 */

package assignment5;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

    /* START --- NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }
    

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    protected final String look(int direction, boolean steps) {
        energy -= Params.LOOK_ENERGY_COST;
    	int numSteps = 1;
    	int x_view = x_coord;
    	int y_view = y_coord;
    	if (steps) {
    		numSteps = 2;
    	}
    	switch(direction) {
 		case 0:
 			x_view +=numSteps;	//right
 			break;
 		case 1:
 			x_view +=numSteps;	//diagonally up right
 			y_view -=numSteps;
 			break;
 		case 2:
 			y_view -=numSteps;	//up
 			break;
 		case 3:
 			x_view -=numSteps;	//diagonally up left
 			y_view -=numSteps;
 			break;
 		case 4:
 			x_view -=numSteps;   //left
 			break;
 		case 5:
 			x_view -=numSteps;	//diagonally down left
 			y_view +=numSteps;
 			break;
 		case 6:
 			y_view +=numSteps; 	//down
 			break;
 		case 7:
 			x_view +=numSteps;	//diagonally down right
 			y_view +=numSteps;
 			break;
 		}
    	
    	for (int i=0; i<population.size(); i++) {
			if (population.get(i).x_coord == x_view && population.get(i).y_coord == y_view) {
				return population.get(i).toString();
			}
		}
    	
    	return null;
    }

    public static String runStats(List<Critter> critters) {
    	//System.out.print("" + critters.size() + " critters as follows -- ");
    	String stats = "" + critters.size() + " critters as follows -- "; 
        Map<String, Integer> critter_count = new HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            critter_count.put(crit_string,
                    critter_count.getOrDefault(crit_string, 0) + 1);
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            //System.out.print(prefix + s + ":" + critter_count.get(s));
        	stats += prefix + s + ":" + critter_count.get(s);
            prefix = ", ";
        }
        //System.out.println();
        return stats;
    }


    public static void displayWorld(Object pane) {
        GridPane world = (GridPane)pane;
        for(int i=0; i<Params.WORLD_HEIGHT;i++) {
        	for(int j=0; j<Params.WORLD_WIDTH; j++) {
        		world.add(null, j, i);
        	}
        }
        for(int i=0;i<population.size();i++) {
    		world.add(population.get(i).viewShape(),population.get(i).x_coord,population.get(i).y_coord);
    	}
        
    }

	/* END --- NEW FOR PROJECT 5
			rest is unchanged from Project 4 */

    private int energy = 0;

    private int x_coord;
    private int y_coord;

    private static List<Critter> population = new ArrayList<Critter>();
    private static List<Critter> babies = new ArrayList<Critter>();

    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    
    
    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name)
            throws InvalidCritterException {
    	
    	Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;

		try {
			/*Class object of specified name*/
			myCritter = Class.forName(myPackage+"."+critter_class_name); 	
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			/*No-parameter constructor object*/
			constructor = myCritter.getConstructor();		
			/*Create new object using constructor*/
			instanceOfMyCritter = constructor.newInstance();	
		} catch (InstantiationException|IllegalAccessException|IllegalArgumentException|NoSuchMethodException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		catch(InvocationTargetException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		Critter me = (Critter)instanceOfMyCritter;	
		me.energy = Params.START_ENERGY;
		/*critter will have a random (x,y) coordinate*/
		me.x_coord = Critter.getRandomInt(Params.WORLD_WIDTH);
		me.y_coord = Critter.getRandomInt(Params.WORLD_HEIGHT);
		/*add the newly created critter to the population*/
		population.add(me);

    }

    
    
    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.
     *                           Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name)
            throws InvalidCritterException {

    	List<Critter> sameKind = new ArrayList<Critter>();
    	Class<?> newCri;
    	try {
    		newCri = Class.forName(myPackage+"."+critter_class_name);
    	}
    	catch(ClassNotFoundException e) {
    		throw new InvalidCritterException(critter_class_name);
    	}
    	for(Critter critter : population) {
    		if(newCri.isInstance(critter)) {
    			sameKind.add(critter);
    		}
    	}
        return sameKind;
    }

    
    
    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
    	population.clear();
    	babies.clear();
    }

    public static void worldTimeStep() {

    	for (int i=0; i<population.size(); i++) {
    		population.get(i).hasMoved = false;
    		population.get(i).doTimeStep();
    	}
    	for (int i=0; i<population.size()-1; i++) {
    		for (int j=i+1; j<population.size(); j++) {
    			if (population.get(i).x_coord == population.get(j).x_coord && population.get(i).y_coord == population.get(j).y_coord) {
    				doEncounter(population.get(i), population.get(j));
    			}
    		}
    	}
    	/*Remove dead critter from the world*/
    	for (int i=0; i<population.size(); i++) {
    		population.get(i).energy -= Params.REST_ENERGY_COST;
    		if (population.get(i).energy <= 0) {
    			population.remove(i);
    			i--;
    		}
    	}
    	/*create clover in each world time step*/
    	for (int i=0; i<Params.REFRESH_CLOVER_COUNT; i++) {
    		try {
				createCritter("Clover");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
    	}
    	/*all all babies into the world*/ 
    	population.addAll(babies);
    	babies.clear();

    }
    
    /**
     * Calls fight for both critters in the encounter, 
     * and calculates the appropriate value for the 
     * fight.
     * 
     * @param a First critter that is encountered
     * @param b Second critter that is encountered
     */
    private static void doEncounter(Critter a, Critter b) {
    	int currentx = a.x_coord;
    	int currenty = b.y_coord;
    	/*if a wants to fight*/
    	boolean A = a.fight(b.toString());
    	/*if b wants to fight*/
    	boolean B = b.fight(a.toString());
    	int fightA = 0;
    	int fightB = 0;
    		
    	if(!A) {
    		for (int i=0; i<population.size(); i++) {
    			if(collision(a, population.get(i)) && !a.equals(population.get(i))) {
    				a.x_coord = currentx;
    				a.y_coord = currenty;
    				i = population.size();
   				}
  			}
  		}
    	if(!B) {
   			for (int i=0; i<population.size(); i++) {
  				if(collision(b, population.get(i)) && !b.equals(population.get(i))) {
 					b.x_coord = currentx;
   					b.y_coord = currenty;
  					i = population.size();
  				}
  			}
  		}
    	if(a.x_coord != b.x_coord || a.y_coord != b.y_coord) {
    			return;
    	}
    	else {
    		if(A&&a.energy>0) fightA = Math.abs(getRandomInt(a.energy));
    		if(B&&b.energy>0) fightB = Math.abs(getRandomInt(b.energy));
   			if (fightA>fightB) {
   				a.energy = a.energy + b.energy/2;
   				b.energy = 0;
   			}
   			else {
   				b.energy = b.energy + a.energy/2;
    			a.energy = 0;
    		}
    	}
    }

    /**
     * returns true if two critters are in the same spot
     * 
     * @param a First critter
     * @param b Second critter
     * @return
     */
    private static boolean collision(Critter a, Critter b) {
    	if (a.x_coord == b.x_coord && a.y_coord == b.y_coord && b.energy>0) return true;
    	else return false;

    }
    
    public abstract void doTimeStep();

    public abstract boolean fight(String oponent);
    
    private boolean hasMoved=false;

    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }


    /**
     * Allows critter to move one step in any direction, called in do time step
     * 
     * @param direction Indicates direction traveled
     */
    protected final void walk(int direction) {
    	
        if (hasMoved == false) {
        	this.energy -= Params.WALK_ENERGY_COST;

        		switch(direction) {
        		case 0:
        			this.x_coord +=1;	//right
        			break;
        		case 1:
        			this.x_coord +=1;	//diagonally up right
        			this.y_coord -=1;
        			break;
        		case 2:
        			this.y_coord -=1;	//up
        			break;
        		case 3:
        			this.x_coord -=1;	//diagonally up left
        			this.y_coord -=1;
        			break;
        		case 4:
        			this.x_coord -=1;   //left
        			break;
        		case 5:
        			this.x_coord -=1;	//diagonally down left
        			this.y_coord +=1;
        			break;
        		case 6:
        			this.y_coord +=1; 	//down
        			break;
        		case 7:
        			this.x_coord +=1;	//diagonally down right
        			this.y_coord +=1;
        			break;
        		}

        	
        		//wrap around cases
        		if(this.x_coord>Params.WORLD_WIDTH-1) {				
        			this.x_coord -= Params.WORLD_WIDTH;
        		}
        		if(this.x_coord<0) {
        			this.x_coord += Params.WORLD_WIDTH;
        		}
        		if(this.y_coord>Params.WORLD_HEIGHT-1) {
        			this.y_coord -= Params.WORLD_HEIGHT;
        		}
        		if(this.y_coord<0) {
        			this.y_coord += Params.WORLD_HEIGHT;
        		}
        		hasMoved = true;
        	}
    }
    
    /**
     * Allows critter to move two spaces in any direction, called from doTimeStep
     * 
     * @param direction Indicates direction traveled
     */
    protected final void run(int direction) {

    	if (hasMoved == false) {
    		this.energy -= Params.RUN_ENERGY_COST;

        		switch(direction) {
        		case 0:
        			this.x_coord +=2;	//right
        			break;
        		case 1:
        			this.x_coord +=2;	//diagonally up right
        			this.y_coord -=2;
        			break;
        		case 2:
        			this.y_coord -=2;	//up
        			break;
        		case 3:
        			this.x_coord -=2;	//diagonally up left
        			this.y_coord -=2;
        			break;
        		case 4:
        			this.x_coord -=2;   //left
        			break;
        		case 5:
        			this.x_coord -=2;	//diagonally down left
        			this.y_coord +=2;
        			break;
        		case 6:
        			this.y_coord +=2; 	//down
        			break;
        		case 7:
        			this.x_coord +=2;	//diagonally down right
        			this.y_coord +=2;
        			break;
        		}
        	
        		//wrap around cases
        		if(this.x_coord>Params.WORLD_WIDTH-1) {				
        			this.x_coord -= Params.WORLD_WIDTH;
        		}
        		if(this.x_coord<0) {
        			this.x_coord += Params.WORLD_WIDTH;
        		}
        		if(this.y_coord>Params.WORLD_HEIGHT-1) {
        			this.y_coord -= Params.WORLD_HEIGHT;
        		}
        		if(this.y_coord<0) {
        			this.y_coord += Params.WORLD_HEIGHT;
        		}
        		hasMoved = true;
    	}
    }
    
    /**
     * Allows critter to reproduce. Gives offspring appropriate 
     * amount of energy and adds it to babies List. Checks to
     * make sure parent has enough energy first.
     * 
     * @param offspring "Child" to be created if possible
     * @param direction Space where child will be after creation (within one space of parent)
     */
    protected final void reproduce(Critter offspring, int direction) {

    	if(this.energy < Params.MIN_REPRODUCE_ENERGY) {
    		return;
    	}
    	offspring.energy = this.energy/2;
    	this.energy = (this.energy+1)/2;
    	offspring.x_coord = this.x_coord;
    	offspring.y_coord = this.y_coord;
    	offspring.walk(direction);
    	offspring.energy +=1; 				//no energy should be deducted for newborn
    	babies.add(offspring);
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }
}
