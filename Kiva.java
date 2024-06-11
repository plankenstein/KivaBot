import edu.duke.Point;
/**
 * Represents a kiva bot and all its attributes.
 * Kiva bot can work with valid String floor map layouts.
 */

public class Kiva {
    private Point currentLocation;
    private FacingDirection directionFacing;
    private FloorMap map;
    private boolean carryingPod;
    private boolean successfullyDropped;
    private long motorLifetime;
    /**
     * Gets the location of a kiva bot.
     * @return currentLocation
     */
    public Point getInitialKivaLocation(){
        return currentLocation;
    }
    /**
     * Tells which way the kiva is facing.
     * @return directionFacing
     */
    public FacingDirection getDirectionFacing(){
        return directionFacing;
    }
    
    public boolean isCarryingPod(){
        return carryingPod;
    }
    
    public boolean isSuccessfullyDropped(){
        return successfullyDropped;
    }
    /**
     * Creates a kiva bot and applies initial attributes.
     * @param FloorMap map
     */
    public Kiva(FloorMap map){
        this.currentLocation = map.getInitialKivaLocation();
        this.directionFacing = FacingDirection.UP;
        this.map = map;
        this.carryingPod = false;
        this.successfullyDropped = false;
        this.motorLifetime = 0;
    }
    /**
     * Constructs kiva using the assigned starting location.
     * @param FloorMap map @Point currentLocation
     */
    public Kiva(FloorMap map, Point currentLocation){
        this.currentLocation = currentLocation;
        this.directionFacing = FacingDirection.UP;
        this.map = map;
        this.carryingPod = false;
        this.successfullyDropped = false;
        this.motorLifetime = 0;
    }
    
    /**
     * Tells the kiva which way to turn and what to do.
     * @KivaCommand command
     */
    public void move(KivaCommand command){
            switch(command){
                case FORWARD:
                    moveForward();
                    break;
                case TURN_LEFT:
                    moveTurnLeft();
                    break;
                case TURN_RIGHT:
                    moveTurnRight();
                    break;
                case TAKE:
                    takePod();
                    break;
                case DROP:
                    dropPod();
                    break;
            }
    }
    /**
     * Moves the kiva forward in the direction it's facing and sets its new location point.
     */
    public void moveForward(){
        int x1 = currentLocation.getX();//x1 to update
        int y1 = currentLocation.getY();//y1 to update
        int x2 = directionFacing.getDelta().getX();//x2 facing dir to add
        int y2 = directionFacing.getDelta().getY();//y2 facing dir to add
        int x3 = x1 + x2;
        int y3 = y1 + y2;
        Point updatedLocation = new Point(x3,y3);
        
        //exception code block
        if((x3 < 0 || x3 > map.getMaxColNum()) || (y3 < 0 || y3 > map.getMaxRowNum())){
        throw new IllegalMoveException("MOVE OUT OF BOUNDS-Can't do that!");
        } else if (map.getObjectAtLocation(updatedLocation) == FloorMapObject.OBSTACLE){
        throw new IllegalMoveException(String.format("OBSTACLE at Location %s", updatedLocation));
        } else if (carryingPod == true && map.getObjectAtLocation(updatedLocation) == FloorMapObject.POD){
        throw new IllegalMoveException(String.format("POD COLLISION!Location %s has a Pod", updatedLocation));
        } else {
            this.currentLocation = updatedLocation;
            incrementMotorLifetime();
        }
    }
    /**
     * Turns the kiva left, updating its facingDirection.
     */
    public void moveTurnLeft(){
        if(directionFacing == FacingDirection.UP){
            this.directionFacing = FacingDirection.LEFT;
        } else if(directionFacing == FacingDirection.LEFT){
            this.directionFacing = FacingDirection.DOWN;
        } else if(directionFacing == FacingDirection.DOWN){
            this.directionFacing = FacingDirection.RIGHT;
        } else if(directionFacing == FacingDirection.RIGHT){
            this.directionFacing = FacingDirection.UP;
        }
        incrementMotorLifetime();
    }
    /**
     * Turns the kiva right, updating its facingDirection.
     */
    public void moveTurnRight(){
        if(directionFacing == FacingDirection.UP){
            this.directionFacing = FacingDirection.RIGHT;
        } else if(directionFacing == FacingDirection.RIGHT){
            this.directionFacing = FacingDirection.DOWN;
        } else if(directionFacing == FacingDirection.DOWN){
            this.directionFacing = FacingDirection.LEFT;
        } else if(directionFacing == FacingDirection.LEFT){
            this.directionFacing = FacingDirection.UP;
        }
        incrementMotorLifetime();
    }
    /**
     * Has the kiva pick a pod if possible.
     */
    public void takePod(){
        FloorMapObject terrain = map.getObjectAtLocation(currentLocation);
        if(map.getObjectAtLocation(currentLocation) != FloorMapObject.POD){
            throw new NoPodException(String.format("NO POD TO TAKE. Location %s is %s", currentLocation, terrain));
        } else {
            carryingPod = true;
        }
    }
    /**
     * Has the kiva drop a pod if possible.
     */
    public void dropPod(){
        FloorMapObject terrain = map.getObjectAtLocation(currentLocation);
        if (carryingPod == false){
            throw new IllegalMoveException("NO POD TO DROP");
        } else if(map.getObjectAtLocation(currentLocation) != FloorMapObject.DROP_ZONE){
            throw new IllegalDropZoneException(String.format("CANT DROP HERE. Location %s is %s", currentLocation, terrain));
        } else {
            successfullyDropped = true;
            this.carryingPod = false;
        }
    }
    /**
     * Gets the kiva motor's lifetime in milliseconds.
     * @return motorLifetime
     */
    public long getMotorLifetime(){
        //System.out.print(String.format("Motor Lifetime: %s", motorLifetime));
        return motorLifetime;
    }
    /**
     * Updates the kiva's motor lifetime after moving.
     */
    public long incrementMotorLifetime(){
        if(motorLifetime >= 72000000000L){
            throw new IllegalDropZoneException(String.format("KIVA NEEDS MAINTENANCE"));
        } else {
            return motorLifetime = motorLifetime + 1000;
        }
    }
}