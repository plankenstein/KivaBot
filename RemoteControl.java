import edu.duke.FileResource;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;
/**
 * This is the class that controls Kiva's actions. Implement the <code>run()</code>
 * method to deliver the pod and avoid the obstacles.
 *
 * This is starter code that may or may not work. You will need to update the code to
 * complete the project.
 */
public class RemoteControl {
    KeyboardResource keyboardResource;
    ArrayList<KivaCommand> output = new ArrayList<KivaCommand>();

    public RemoteControl() {
        keyboardResource = new KeyboardResource();
    }
    
    public void convertToKivaCommands(String directions){
        int i;
        int j;
        
        KeyboardResource keyboardResource;
        keyboardResource = new KeyboardResource();
        
        directions = keyboardResource.getLine();//directions input frt = "frt";
        char[] directionsArray = directions.toCharArray();//input frt = directionsarray['f','r','t'];
        ArrayList<KivaCommand> KivaVals = new ArrayList<KivaCommand>();
        //puts enum values into an arraylist for for-each loop
        for(j = 0; j < KivaCommand.values().length; j++){
                    KivaVals.add(KivaCommand.values()[j]);
                }
        //compares user input with valid commands
        for(i = 0; i < directionsArray.length; i++){
            boolean found = false;
            for(KivaCommand val : KivaVals){
                if(val.getDirectionKey() == directionsArray[i]){ 
                    output.add(val);
                    found = true;
                    break;
                 }
            }
            if(!found){
                throw new IllegalArgumentException("Invalid Instruction");
            }
        }
        System.out.print(output);
    }

    /**
     * The controller that directs Kiva's activity. Prompts the user for the floor map
     * to load, displays the map, and asks the user for the commands for Kiva to execute.
     *
     * [Here's the method you'll execute from within BlueJ. It may or may not run successfully
     * as-is, but you'll definitely need to add more to complete the project.]
     */
    public void run() {
        System.out.println("Please select a map file.");
        FileResource fileResource = new FileResource();
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        System.out.println(floorMap);
        
        // create kiva with user's map
        Kiva kiva = new Kiva(floorMap);
        
        //give user starting Kiva status
        System.out.println("Your Kiva's startup status: " + kiva.getInitialKivaLocation() + " " + kiva.getDirectionFacing());
        
        //input directions
        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        convertToKivaCommands(directions);
        
        //uses directions to call move()
        for(KivaCommand o : output){
             switch(o){
                 case FORWARD:
                    kiva.move(KivaCommand.FORWARD);
                    break;
                 case TURN_RIGHT:
                    kiva.move(KivaCommand.TURN_RIGHT);
                    break;
                 case TURN_LEFT:
                    kiva.move(KivaCommand.TURN_LEFT);
                    break;
                 case TAKE:
                    kiva.move(KivaCommand.TAKE);
                    break;
                 case DROP:
                    kiva.move(KivaCommand.DROP);
                    break;
                }
        }
        
        
        kiva.isSuccessfullyDropped();
        
        if(kiva.isSuccessfullyDropped() == true && output.get(output.size() - 1) == KivaCommand.DROP){
            System.out.print("\nSuccessfully picked up and dropped off the pod. Thanks!");
        } else if(kiva.isSuccessfullyDropped() == true && output.get(output.size() - 1) != KivaCommand.DROP){
            System.out.print("\nThe kiva shouldn't move after successfully dropping off pod.");
        } else {
            System.out.print("\nSorry. The kiva didn't pick up the pod or drop it off in the right place.");
        }
        
        
    }
}