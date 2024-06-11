import java.util.Arrays;
/**
 * Write a description of printKivaCommandtest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class printKivaCommandtest {
    public void printTest(){
        int i = 0;
        System.out.println(Arrays.toString(KivaCommand.values()));
        for(i = 0; i<5; i++){
            System.out.println(KivaCommand.values()[i].getDirectionKey());
            System.out.println(KivaCommand.values()[i].name());
        }
        System.out.println(KivaCommand.values()[1].name());
    }
}
