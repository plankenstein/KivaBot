import java.util.*;
/**
 *KivaCommands tell the kiva what to do. Each command has a corresponding keyboard key for the
 *user to enter when prompted.
 */
public enum KivaCommand {
    FORWARD('F'), TURN_LEFT('L'), TURN_RIGHT('R'), TAKE('T'), DROP('D');
    
    public char directionKey;
    /**
     * Takes the user's key input.
     * @param directionKey
     */
    private KivaCommand(char directionKey){
        this.directionKey = directionKey;
        
    }
    /**
     * Prints the key entered by the user.
     * @return directionKey
     */
    public char getDirectionKey(){
        
        return directionKey;
    }
    
}