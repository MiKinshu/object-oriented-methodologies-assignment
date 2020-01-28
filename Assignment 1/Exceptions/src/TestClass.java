import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.abs;

class InvalidLandmarkException extends Exception {
    InvalidLandmarkException() {
        super();
    }
}

class InvalidTLLandmarkException extends Exception {
    InvalidTLLandmarkException() {
        super();
    }
}

class InvalidBLandmarkException extends Exception {
    InvalidBLandmarkException() {
        super();
    }
}

class InvalidSLandmarkException extends Exception {
    InvalidSLandmarkException() {
        super();
    }
}

class InvalidWLandmarkException extends Exception {
    InvalidWLandmarkException() {
        super();
    }
}

class InvalidEdgeException extends Exception {
    InvalidEdgeException() {
        super();
    }
}

class InvalidMotorwayEdgeException extends Exception{
    InvalidMotorwayEdgeException() {
        super();
    }
}

class InvalidPedestrianRoadEdgeException extends Exception{
    InvalidPedestrianRoadEdgeException() {
        super();
    }
}

class InvalidCyclistRoadEdgeException extends Exception{
    InvalidCyclistRoadEdgeException() {
        super();
    }
}

class InvalidSwampsEdgeException extends Exception{
    InvalidSwampsEdgeException() {
        super();
    }
}

class InvalidLakesEdgeException extends Exception{
    InvalidLakesEdgeException() {
        super();
    }
}

class LandmarkDNEException extends Exception{
    LandmarkDNEException(){
        super();
    }
}

class CheckRoadException extends Exception{
    CheckRoadException(){
        super();
    }
}

class Vertex{
    private String name;
    private String road;
    private String type;
    private String Fromv;
    private LinkedList<Landmark> landmarkLinkedList= new LinkedList<>();

    boolean CheckInputFather(String name, int length, int MaxSpeed, String FromV, String ToV) throws InvalidEdgeException{
        int accept=1;
        if(name.length()>24||name.length()<4||FromV.length()>24||FromV.length()<4||ToV.length()>24||ToV.length()<4){
            accept=0;
        }
        if(length<0||length>150||MaxSpeed<0||MaxSpeed>150) {
            accept = 0;
        }
        if(accept==1)
            return true;
        else{
            throw new InvalidEdgeException();
        }
    }

    LinkedList<Landmark> getLandmarkLinkedList() {
        return landmarkLinkedList;
    }

    Vertex(){}

    void setFromv(String fromv) {
        Fromv = fromv;
    }

    private int LengthOfRoad;
    private int SpeedOfRoad;
    private double cost;
    private double EffectiveSpeed;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getRoad() {
        return road;
    }

    void setRoad(String road) {
        this.road = road;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    int getLengthOfRoad() {
        return LengthOfRoad;
    }

    void setLengthOfRoad(int lengthOfRoad) {
        LengthOfRoad = lengthOfRoad;
    }

    int getSpeedOfRoad() {
        return SpeedOfRoad;
    }

    void setSpeedOfRoad(int speedOfRoad) {
        SpeedOfRoad = speedOfRoad;
    }

    double getCost() {
        return cost;
    }

    void setEffectiveSpeed(double effectiveSpeed) {
        EffectiveSpeed = effectiveSpeed;
    }

    void CalculateCost(){
        cost=((double)LengthOfRoad)/EffectiveSpeed;
    }
}

class motorway extends Vertex{

    private int TollPrice, NoOfLanes, MaintainanceLevel, Occupancy;

    int getTollPrice() {
        return TollPrice;
    }

    void setTollPrice(int TollPrice) {
        this.TollPrice = TollPrice;
    }

    int getNoOfLanes() {
        return NoOfLanes;
    }

    void setNoOfLanes(int NoOfLanes) {
        this.NoOfLanes = NoOfLanes;
    }

    int getMaintainanceLevel() {
        return MaintainanceLevel;
    }

    void setMaintainanceLevel(int MaintainanceLevel) {
        this.MaintainanceLevel = MaintainanceLevel;
    }

    int getOccupancy() {
        return Occupancy;
    }

    void setOccupancy(int Occupancy) {
        this.Occupancy = Occupancy;
    }

    void CalculateSpeed(){

        this.setEffectiveSpeed(this.getSpeedOfRoad()*(1-((double)Occupancy)/(100*getNoOfLanes())));
    }

    boolean CheckInputSon(int occupancy, int noOfLanes) throws InvalidMotorwayEdgeException{
        if(occupancy>-1&&occupancy<100*noOfLanes+1){
            return true;
        }
        else {
            throw new InvalidMotorwayEdgeException();
        }
    }

    motorway() {
    }

}

class pedestrianRoad extends Vertex{
    private int WidthOfRoad, ScenicView,Occupancy;

    pedestrianRoad() {
    }

    void CalculateSpeed(){
        double frac= ((double)Occupancy)/1500;
        frac = 1-frac;
        this.setEffectiveSpeed(this.getSpeedOfRoad()*frac);
    }

    boolean CheckInputSon(int occupancy)throws InvalidPedestrianRoadEdgeException{
        if(occupancy>-1&&occupancy<1501){
            return true;
        }
        else {
            throw new InvalidPedestrianRoadEdgeException();
        }
    }

    int getWidthOfRoad() {
        return WidthOfRoad;
    }

    void setWidthOfRoad(int WidthOfRoad) {
        this.WidthOfRoad = WidthOfRoad;
    }

    int getScenicView() {
        return ScenicView;
    }

    void setScenicView(int ScenicView) {
        this.ScenicView = ScenicView;
    }

    int getOccupancy() {
        return Occupancy;
    }

    void setOccupancy(int Occupancy) {
        this.Occupancy = Occupancy;
    }

}

class cyclistRoad extends Vertex{
    private int curvature;

    void CalculateSpeed(){
        double effective=((double)this.getSpeedOfRoad()) /curvature;
        this.setEffectiveSpeed(effective);
    }

    boolean CheckInputSon(int curvature)throws InvalidCyclistRoadEdgeException{
        if(curvature<1){
            throw new InvalidCyclistRoadEdgeException();
        }
        else return true;
    }

    cyclistRoad() {
    }

    int getCurvature() {
        return curvature;
    }

    void setCurvature(int curvature) {
        this.curvature = curvature;
    }
}

class swamps extends Vertex{
    private int difficulty;
    void CalculateSpeed(){
        double temp=((double)this.getSpeedOfRoad())/difficulty;
        this.setEffectiveSpeed(temp/difficulty);
    }

    boolean CheckInputSon(int difficulty) throws InvalidSwampsEdgeException{
        if(difficulty<1||difficulty>5){
            throw new InvalidSwampsEdgeException();
        }
        else return true;
    }

    swamps() {
    }

    int getDifficulty() {
        return difficulty;
    }

    void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}

class lakes extends Vertex{
    private int width, TidalLevel, depth;

    void CalculateSpeed(){
        this.setEffectiveSpeed(this.getSpeedOfRoad());
    }

    boolean CheckInputSon(int tidalLevel)throws InvalidLakesEdgeException{
        if(tidalLevel<1||tidalLevel>15){
            throw new InvalidLakesEdgeException();
        }
        else
            return true;
    }

    lakes() {
    }

    int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    int getTidalLevel() {
        return TidalLevel;
    }

    void setTidalLevel(int tidalLevel) {
        TidalLevel = tidalLevel;
    }

    int getDepth() {
        return depth;
    }

    void setDepth(int depth) {
        this.depth = depth;
    }

}

class FromV {
    private String name;

    private LinkedList<Vertex> ToVertexLL = new LinkedList<>();

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    LinkedList<Vertex> getToVertexLL() {
        return ToVertexLL;
    }

    FromV(String name){
        this.name=name;
    }

    FromV(){}

    boolean CheckRoad(int fvert, Vertex tov)throws CheckRoadException{
        if(fvert==-1||tov==null)
            throw new CheckRoadException();
        else
            return true;
    }
}

class Landmark{
    private String name;
    private String id;
    private String location;
    private String type;
    private String FromVertex;
    private String ToVertex;

    String getFromVertex() {
        return FromVertex;
    }

    void setFromVertex(String fromVertex) {
        FromVertex = fromVertex;
    }

    boolean CheckInputDad(String ID, String  name) throws InvalidLandmarkException{
        if(ID.length()>14||ID.length()<4||name.length()>24||name.length()<4){
            throw new InvalidLandmarkException();
        }
        else
            return true;
    }

    boolean CheckQuery(int Query) throws LandmarkDNEException{
        if(Query==-1){
            throw new LandmarkDNEException();
        }
        else return true;
    }

    String getToVertex() {
        return ToVertex;
    }

    void setToVertex(String toVertex) {
        ToVertex = toVertex;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

}

class trafficLight extends Landmark{
    private int TimeRed;

    int getTimeRed() {
        return TimeRed;
    }

    boolean CheckInputSon(int timeRed)throws InvalidTLLandmarkException{
        if(timeRed<1||timeRed>100){
            throw new InvalidTLLandmarkException();
        }
        else
            return true;
    }

    void setTimeRed(int timeRed) {
        TimeRed = timeRed;
    }
}

class bench extends Landmark{
    private int CarryingCapacity;
    private String DonatedBy;

    int getCarryingCapacity() {
        return CarryingCapacity;
    }

    void setCarryingCapacity(int carryingCapacity) {
        CarryingCapacity = carryingCapacity;
    }

    String getDonatedBy() {
        return DonatedBy;
    }

    void setDonatedBy(String donatedBy) {
        DonatedBy = donatedBy;
    }

    boolean CheckInputSon(int carryingCapacity)throws InvalidBLandmarkException{
        if(carryingCapacity<1||carryingCapacity>15){
            throw new InvalidBLandmarkException();
        }
        else return true;
    }
}

class shop extends Landmark{
    private String OpeningTime;
    private String Shoptype;
    private int Rating;
    private int ExpenseLevel;

    boolean CheckInputSon(String type, int rating, int expenseLevel)throws InvalidSLandmarkException{
        int okay=0;
        if(type.equals("food")||type.equals("clothing")||type.equals("store")||type.equals("others")) {
            okay = 1;
        }
//        System.out.println("type "+type+" rating "+rating+" expenxelevel "+expenseLevel+" okay "+okay);
        if(rating<0||rating>10||expenseLevel<0||expenseLevel>10||okay==0){
            throw new InvalidSLandmarkException();
        }
        else return true;
    }

    String getOpeningTime() {
        return OpeningTime;
    }

    void setOpeningTime(String openingTime) {
        OpeningTime = openingTime;
    }

    String getShoptype() {
        return Shoptype;
    }

    void setShoptype(String shoptype) {
        Shoptype = shoptype;
    }

    int getRating() {
        return Rating;
    }

    void setRating(int rating) {
        Rating = rating;
    }

    int getExpenseLevel() {
        return ExpenseLevel;
    }

    void setExpenseLevel(int expenseLevel) {
        ExpenseLevel = expenseLevel;
    }
}

class washroom extends Landmark{
    private int CostPerUse;
    private int CleanlinessLevel;

    boolean CheckInputSon(int cleanlinessLevel)throws InvalidWLandmarkException{
        if(cleanlinessLevel<0||cleanlinessLevel>10){
            throw new InvalidWLandmarkException();
        }
        else return true;
    }

    int getCostPerUse() {
        return CostPerUse;
    }

    void setCostPerUse(int costPerUse) {
        CostPerUse = costPerUse;
    }

    int getCleanlinessLevel() {
        return CleanlinessLevel;
    }

    void setCleanlinessLevel(int cleanlinessLevel) {
        CleanlinessLevel = cleanlinessLevel;
    }
}

public class TestClass {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        for(int i=0;i<testcase;i++){
            String numbers=(br.readLine());
            String[] num=numbers.split(" ");
            int edges=Integer.parseInt(num[0]);
            int NoOfLand=Integer.parseInt(num[1]);
            LinkedList<FromV> FromVertexLL= new LinkedList<>();
            for(int j=0;j<edges;j++) {
                String inputstr = br.readLine();
                String[] words = inputstr.split(" ");
                int take = 0;
                int index = -1;
                for (int l = 0; l < FromVertexLL.size(); l++) {
                    if (FromVertexLL.get(l).getName().equals(words[0])) {
                        index = l;
                        break;
                    }
                }
                switch (words[2].charAt(0)) {
                    case 'm': {
                        motorway vertex = new motorway();
                        try {
                            if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1])
                                    && vertex.CheckInputSon(Integer.parseInt(words[9]), Integer.parseInt(words[7]))) {
                                vertex.setName(words[1]);
                                vertex.setType(words[2]);
                                vertex.setRoad(words[3]);
                                vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                vertex.setTollPrice(Integer.parseInt(words[6]));
                                vertex.setNoOfLanes(Integer.parseInt(words[7]));
                                vertex.setMaintainanceLevel(Integer.parseInt(words[8]));
                                vertex.setOccupancy(Integer.parseInt(words[9]));
                                vertex.setFromv(words[0]);
                                vertex.CalculateSpeed();
                                vertex.CalculateCost();
                                if (index != -1) {
                                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                                } else {
                                    FromV node = new FromV();
                                    node.setName(words[0]);
                                    node.getToVertexLL().add(vertex);
                                    FromVertexLL.add(node);
                                }
                                take = 1;
                            }
                        } catch (InvalidEdgeException e) {
                            System.out.println("invalid edge");
                        } catch (InvalidMotorwayEdgeException e) {
                            System.out.println("invalid motorway edge");
                        }
                        break;
                    }
                    case 'p': {
                        pedestrianRoad vertex = new pedestrianRoad();
                        try {
                            if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                    vertex.CheckInputSon(Integer.parseInt(words[8]))) {
                                vertex.setName(words[1]);
                                vertex.setType(words[2]);
                                vertex.setRoad(words[3]);
                                vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                vertex.setWidthOfRoad(Integer.parseInt(words[6]));
                                vertex.setScenicView(Integer.parseInt(words[7]));
                                vertex.setOccupancy(Integer.parseInt(words[8]));
                                vertex.setFromv(words[0]);
                                vertex.CalculateSpeed();
                                vertex.CalculateCost();
                                if (index != -1) {
                                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                                } else {
                                    FromV node = new FromV();
                                    node.setName(words[0]);
                                    node.getToVertexLL().add(vertex);
                                    FromVertexLL.add(node);
                                }
                                take = 1;
                            }
                        } catch (InvalidEdgeException e) {
                            System.out.println("invalid edge");
                        } catch (InvalidPedestrianRoadEdgeException e) {
                            System.out.println("invalid pedestrianRoad edge");
                        }
                        break;
                    }
                    case 'c': {
                        cyclistRoad vertex = new cyclistRoad();
                        try {
                            if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                    vertex.CheckInputSon(Integer.parseInt(words[6]))) {
                                vertex.setName(words[1]);
                                vertex.setType(words[2]);
                                vertex.setRoad(words[3]);
                                vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                vertex.setCurvature(Integer.parseInt(words[6]));
                                vertex.setFromv(words[0]);
                                vertex.CalculateSpeed();
                                vertex.CalculateCost();
                                if (index != -1) {
                                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                                } else {
                                    FromV node = new FromV();
                                    node.setName(words[0]);
                                    node.getToVertexLL().add(vertex);
                                    FromVertexLL.add(node);
                                }
                                take = 1;
                            }
                        } catch (InvalidEdgeException e) {
                            System.out.println("invalid edge");
                        } catch (InvalidCyclistRoadEdgeException e) {
                            System.out.println("invalid cyclistRoad edge");
                        }
                        break;
                    }
                    case 's': {
                        swamps vertex = new swamps();
                        try {
                            if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                    vertex.CheckInputSon(Integer.parseInt(words[6]))) {
                                vertex.setName(words[1]);
                                vertex.setType(words[2]);
                                vertex.setRoad(words[3]);
                                vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                vertex.setDifficulty(Integer.parseInt(words[6]));
                                vertex.setFromv(words[0]);
                                vertex.CalculateSpeed();
                                vertex.CalculateCost();
                                if (index != -1) {
                                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                                } else {
                                    FromV node = new FromV();
                                    node.setName(words[0]);
                                    node.getToVertexLL().add(vertex);
                                    FromVertexLL.add(node);
                                }
                                take = 1;
                            }
                        } catch (InvalidEdgeException e) {
                            System.out.println("invalid edge");
                        } catch (InvalidSwampsEdgeException e) {
                            System.out.println("invalid swamps edge");
                        }
                        break;
                    }
                    case 'l': {
                        lakes vertex = new lakes();
                        try {
                            if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                    vertex.CheckInputSon(Integer.parseInt(words[7]))) {
                                vertex.setName(words[1]);
                                vertex.setType(words[2]);
                                vertex.setRoad(words[3]);
                                vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                vertex.setWidth(Integer.parseInt(words[6]));
                                vertex.setTidalLevel(Integer.parseInt(words[7]));
                                vertex.setDepth(Integer.parseInt(words[8]));
                                vertex.setFromv(words[0]);
                                vertex.CalculateSpeed();
                                vertex.CalculateCost();
                                if (index != -1) {
                                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                                } else {
                                    FromV node = new FromV();
                                    node.setName(words[0]);
                                    node.getToVertexLL().add(vertex);
                                    FromVertexLL.add(node);
                                }
                                take = 1;
                            }
                        } catch (InvalidEdgeException e) {
                            System.out.println("invalid edge");
                        } catch (InvalidLakesEdgeException e) {
                            System.out.println("invalid lakes edge");
                        }
                        break;
                    }
                }

                //Doing the same again as road is bidirectional.
                if (take == 1) {
                    index = -1;
                    for (int l = 0; l < FromVertexLL.size(); l++) {
                        if (FromVertexLL.get(l).getName().equals(words[1])) {
                            index = l;
                            break;
                        }
                    }
                    switch (words[2].charAt(0)) {
                        case 'm': {
                            motorway vertex = new motorway();
                            try {
                                if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[1], words[0])
                                        && vertex.CheckInputSon(Integer.parseInt(words[9]), Integer.parseInt(words[7]))) {
                                    vertex.setName(words[0]);
                                    vertex.setType(words[2]);
                                    vertex.setRoad(words[3]);
                                    vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                    vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                    vertex.setTollPrice(Integer.parseInt(words[6]));
                                    vertex.setNoOfLanes(Integer.parseInt(words[7]));
                                    vertex.setMaintainanceLevel(Integer.parseInt(words[8]));
                                    vertex.setOccupancy(Integer.parseInt(words[9]));
                                    vertex.setFromv(words[1]);
                                    vertex.CalculateSpeed();
                                    vertex.CalculateCost();
                                    if (index != -1) {
                                        FromVertexLL.get(index).getToVertexLL().add(vertex);
                                    } else {
                                        FromV node = new FromV();
                                        node.setName(words[1]);
                                        node.getToVertexLL().add(vertex);
                                        FromVertexLL.add(node);
                                    }
                                }
                            } catch (InvalidEdgeException e) {
                                System.out.println("invalid edge");
                            } catch (InvalidMotorwayEdgeException e) {
                                System.out.println("invalid motorway edge");
                            }
                            break;
                        }
                        case 'p': {
                            pedestrianRoad vertex = new pedestrianRoad();
                            try {
                                if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                        vertex.CheckInputSon(Integer.parseInt(words[8]))) {
                                    vertex.setName(words[0]);
                                    vertex.setType(words[2]);
                                    vertex.setRoad(words[3]);
                                    vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                    vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                    vertex.setWidthOfRoad(Integer.parseInt(words[6]));
                                    vertex.setScenicView(Integer.parseInt(words[7]));
                                    vertex.setOccupancy(Integer.parseInt(words[8]));
                                    vertex.setFromv(words[1]);
                                    vertex.CalculateSpeed();
                                    vertex.CalculateCost();
                                    if (index != -1) {
                                        FromVertexLL.get(index).getToVertexLL().add(vertex);
                                    } else {
                                        FromV node = new FromV();
                                        node.setName(words[1]);
                                        node.getToVertexLL().add(vertex);
                                        FromVertexLL.add(node);
                                    }
                                }
                            } catch (InvalidEdgeException e) {
                                System.out.println("invalid edge");
                            } catch (InvalidPedestrianRoadEdgeException e) {
                                System.out.println("invalid pedestrianRoad edge");
                            }
                            break;
                        }
                        case 'c': {
                            cyclistRoad vertex = new cyclistRoad();
                            try {
                                if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                        vertex.CheckInputSon(Integer.parseInt(words[6]))) {
                                    vertex.setName(words[0]);
                                    vertex.setType(words[2]);
                                    vertex.setRoad(words[3]);
                                    vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                    vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                    vertex.setCurvature(Integer.parseInt(words[6]));
                                    vertex.setFromv(words[1]);
                                    vertex.CalculateSpeed();
                                    vertex.CalculateCost();
                                    if (index != -1) {
                                        FromVertexLL.get(index).getToVertexLL().add(vertex);
                                    } else {
                                        FromV node = new FromV();
                                        node.setName(words[1]);
                                        node.getToVertexLL().add(vertex);
                                        FromVertexLL.add(node);
                                    }
                                }
                            } catch (InvalidEdgeException e) {
                                System.out.println("invalid edge");
                            } catch (InvalidCyclistRoadEdgeException e) {
                                System.out.println("invalid cyclistRoad edge");
                            }
                            break;
                        }
                        case 's': {
                            swamps vertex = new swamps();
                            try {
                                if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                        vertex.CheckInputSon(Integer.parseInt(words[6]))) {
                                    vertex.setName(words[0]);
                                    vertex.setType(words[2]);
                                    vertex.setRoad(words[3]);
                                    vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                    vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                    vertex.setDifficulty(Integer.parseInt(words[6]));
                                    vertex.setFromv(words[1]);
                                    vertex.CalculateSpeed();
                                    vertex.CalculateCost();
                                    if (index != -1) {
                                        FromVertexLL.get(index).getToVertexLL().add(vertex);
                                    } else {
                                        FromV node = new FromV();
                                        node.setName(words[1]);
                                        node.getToVertexLL().add(vertex);
                                        FromVertexLL.add(node);
                                    }
                                }
                            } catch (InvalidEdgeException e) {
                                System.out.println("invalid edge");
                            } catch (InvalidSwampsEdgeException e) {
                                System.out.println("invalid swamps edge");
                            }
                            break;
                        }
                        case 'l': {
                            lakes vertex = new lakes();
                            try {
                                if (vertex.CheckInputFather(words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5]), words[0], words[1]) &&
                                        vertex.CheckInputSon(Integer.parseInt(words[7]))) {
                                    vertex.setName(words[0]);
                                    vertex.setType(words[2]);
                                    vertex.setRoad(words[3]);
                                    vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                                    vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                                    vertex.setWidth(Integer.parseInt(words[6]));
                                    vertex.setTidalLevel(Integer.parseInt(words[7]));
                                    vertex.setDepth(Integer.parseInt(words[8]));
                                    vertex.setFromv(words[1]);
                                    vertex.CalculateSpeed();
                                    vertex.CalculateCost();
                                    if (index != -1) {
                                        FromVertexLL.get(index).getToVertexLL().add(vertex);
                                    } else {
                                        FromV node = new FromV();
                                        node.setName(words[1]);
                                        node.getToVertexLL().add(vertex);
                                        FromVertexLL.add(node);
                                    }
                                }
                            } catch (InvalidEdgeException e) {
                                System.out.println("invalid edge");
                            } catch (InvalidLakesEdgeException e) {
                                System.out.println("invalid lakes edge");
                            }
                            break;
                        }
                    }
                }
            }//Inputting edges and adding them to the AdjacencyList.
            FromVertexLL.sort(new Comparator<FromV>() {     //Sorting FromVertex in dictionary order.
                @Override
                public int compare(FromV o1, FromV o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (FromV fromV : FromVertexLL) {      //Sorting to vertex in the asked order.
                fromV.getToVertexLL().sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        if (Double.compare(abs(o1.getCost() - o2.getCost()), 0.0001) < 0)
                            return o1.getRoad().compareTo(o2.getRoad());
                        else {
                            if (o1.getCost() > o2.getCost())
                                return 1;
                            else
                                return -1;
                        }
                    }
                });
            }
            LinkedList<Landmark> LandMarkLL= new LinkedList<>();
            for (int j=0;j<NoOfLand;j++){
                String landinput=br.readLine();
                String[] landwords=landinput.split(" ");

                String FromVertex=landwords[0];
                FromV fkey = new FromV(FromVertex);
                String ToVertex=landwords[1];
                int fvert= Collections.binarySearch(FromVertexLL, fkey, new Comparator<FromV>() {
                    @Override
                    public int compare(FromV o1, FromV o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                Vertex ToVertexKey = null;
                if(fvert!=-1) {
                    for (int k = 0; k < FromVertexLL.get(fvert).getToVertexLL().size(); k++) {
                        if (FromVertexLL.get(fvert).getToVertexLL().get(k).getName().equals(ToVertex)) {
                            ToVertexKey = FromVertexLL.get(fvert).getToVertexLL().get(k);
                            break;
                        }
                    }
                }
                try {
                    if(fkey.CheckRoad(fvert,ToVertexKey)){
                        switch (landwords[5].charAt(0)) {
                            case 't': {
                                trafficLight vertex = new trafficLight();
                                try {
                                    if(vertex.CheckInputDad(landwords[2],landwords[3])&&vertex.CheckInputSon(Integer.parseInt(landwords[6]))) {
                                        vertex.setFromVertex(landwords[0]);
                                        vertex.setToVertex(landwords[1]);
                                        vertex.setId(landwords[2]);
                                        vertex.setName(landwords[3]);
                                        vertex.setLocation(landwords[4]);
                                        vertex.setType(landwords[5]);
                                        vertex.setTimeRed(Integer.parseInt(landwords[6]));
                                        LandMarkLL.add(vertex);
                                        ToVertexKey.getLandmarkLinkedList().add(vertex);
                                    }
                                } catch (InvalidLandmarkException e) {
                                    System.out.println("invalid landmark");
                                } catch (InvalidTLLandmarkException e) {
                                    System.out.println("invalid trafficLight landmark");
                                }
                                break;
                            }

                            case 'b': {
                                bench vertex = new bench();
                                try {
                                    if(vertex.CheckInputDad(landwords[2],landwords[3])&&vertex.CheckInputSon(Integer.parseInt(landwords[6]))){
                                        vertex.setFromVertex(landwords[0]);
                                        vertex.setToVertex(landwords[1]);
                                        vertex.setId(landwords[2]);
                                        vertex.setName(landwords[3]);
                                        vertex.setLocation(landwords[4]);
                                        vertex.setType(landwords[5]);
                                        vertex.setCarryingCapacity(Integer.parseInt(landwords[6]));
                                        vertex.setDonatedBy(landwords[7]);
                                        ToVertexKey.getLandmarkLinkedList().add(vertex);
                                        LandMarkLL.add(vertex);
                                    }
                                } catch (InvalidLandmarkException e) {
                                    System.out.println("invalid landmark");
                                } catch (InvalidBLandmarkException e) {
                                    System.out.println("invalid bench landmark");
                                }
                                break;
                            }

                            case 's': {
                                shop vertex = new shop();
    //                            System.out.println("FV "+landwords[0]+" TV "+landwords[1]);
                                try {
                                    if(vertex.CheckInputDad(landwords[2],landwords[3])&&vertex.CheckInputSon(landwords[7],Integer.parseInt(landwords[8]),Integer.parseInt(landwords[9]))) {
                                        vertex.setFromVertex(landwords[0]);
                                        vertex.setToVertex(landwords[1]);
                                        vertex.setId(landwords[2]);
                                        vertex.setName(landwords[3]);
                                        vertex.setLocation(landwords[4]);
                                        vertex.setType(landwords[5]);
                                        vertex.setOpeningTime(landwords[6]);
                                        vertex.setShoptype(landwords[7]);
                                        vertex.setRating(Integer.parseInt(landwords[8]));
                                        vertex.setExpenseLevel(Integer.parseInt(landwords[9]));
                                        LandMarkLL.add(vertex);
                                        ToVertexKey.getLandmarkLinkedList().add(vertex);
                                    }
                                } catch (InvalidLandmarkException e) {
                                    System.out.println("invalid landmark");
                                } catch (InvalidSLandmarkException e) {
                                    System.out.println("invalid shop landmark");
                                }
                                break;
                            }

                            case 'w': {
                                washroom vertex = new washroom();
                                try {
                                    if(vertex.CheckInputDad(landwords[2],landwords[3])&&vertex.CheckInputSon(Integer.parseInt(landwords[7]))) {
                                        vertex.setFromVertex(landwords[0]);
                                        vertex.setToVertex(landwords[1]);
                                        vertex.setId(landwords[2]);
                                        vertex.setName(landwords[3]);
                                        vertex.setLocation(landwords[4]);
                                        vertex.setType(landwords[5]);
                                        vertex.setCostPerUse(Integer.parseInt(landwords[6]));
                                        vertex.setCleanlinessLevel(Integer.parseInt(landwords[7]));
                                        ToVertexKey.getLandmarkLinkedList().add(vertex);
                                        LandMarkLL.add(vertex);
                                    }
                                } catch (InvalidLandmarkException e) {
                                    System.out.println("invalid landmark");
                                } catch (InvalidWLandmarkException e) {
                                    System.out.println("invalid washroom landmark");
                                }
                                break;
                            }
                        }
                    }
                } catch (CheckRoadException e) {
                    System.out.println("invalid road");
                }
            }       //Inputting Landmarks

            LandMarkLL.sort(new Comparator<Landmark>() {       //sorting LandmarkLL according to ID.
                @Override
                public int compare(Landmark o1, Landmark o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
            int NoOfQueries=Integer.parseInt(br.readLine());
            for(int k=0;k<NoOfQueries;k++) {
                String query = br.readLine();
                Landmark LMkey = new Landmark();
                LMkey.setId(query);
                int QueryInd = Collections.binarySearch(LandMarkLL, LMkey, new Comparator<Landmark>() {
                    @Override
                    public int compare(Landmark o1, Landmark o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                });
                try {
                    if (LMkey.CheckQuery(QueryInd)){
                        System.out.print(LandMarkLL.get(QueryInd).getId() + " " + LandMarkLL.get(QueryInd).getName() + " " + LandMarkLL
                                .get(QueryInd).getLocation() + " ");
                        switch (LandMarkLL.get(QueryInd).getType().charAt(0)) {
                            case 't': {
                                trafficLight t = (trafficLight) LandMarkLL.get(QueryInd);
                                System.out.println(t.getTimeRed());
                                break;
                            }

                            case 'b': {
                                bench b = (bench) LandMarkLL.get(QueryInd);
                                System.out.println(b.getCarryingCapacity() + " " + b.getDonatedBy());
                                break;
                            }

                            case 's': {
                                shop s = (shop) LandMarkLL.get(QueryInd);
                                System.out.println(s.getOpeningTime() + " " + s.getShoptype() + " " + s.getRating()
                                        + " " + s.getExpenseLevel());
                                break;
                            }

                            case 'w': {
                                washroom w = (washroom) LandMarkLL.get(QueryInd);
                                System.out.println(w.getCostPerUse() + " " + w.getCleanlinessLevel());
                                break;
                            }
                        }
                        String FromVertex = LandMarkLL.get(QueryInd).getFromVertex();
                        FromV fkey = new FromV(FromVertex);
                        String ToVertex = LandMarkLL.get(QueryInd).getToVertex();
                        int fvert = Collections.binarySearch(FromVertexLL, fkey, new Comparator<FromV>() {
                            @Override
                            public int compare(FromV o1, FromV o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        FromV fromV = FromVertexLL.get(fvert);
                        Vertex FirstToVobj = null;
                        for (int j = 0; j < fromV.getToVertexLL().size(); j++) {
                            if (fromV.getToVertexLL().get(j).getName().equals(LandMarkLL.get(QueryInd).getToVertex())) {
                                System.out.print(fromV.getName() + " ");
                                System.out.print(fromV.getToVertexLL().get(j).getName() + " "
                                        + fromV.getToVertexLL().get(j).getRoad() + " "
                                        + fromV.getToVertexLL().get(j).getLengthOfRoad() + " "
                                        + fromV.getToVertexLL().get(j).getSpeedOfRoad() + " ");
                                if (FirstToVobj == null) {
                                    fromV.getToVertexLL().get(j).getLandmarkLinkedList().sort(new Comparator<Landmark>() {
                                        @Override
                                        public int compare(Landmark o1, Landmark o2) {
                                            return o1.getId().compareTo(o2.getId());
                                        }
                                    });
                                    FirstToVobj = fromV.getToVertexLL().get(j);
                                }

                                switch (fromV.getToVertexLL().get(j).getType().charAt(0)) {
                                    case 'm': {
                                        motorway m = (motorway) fromV.getToVertexLL().get(j);
                                        System.out.print(m.getTollPrice() + " ");
                                        System.out.print(m.getNoOfLanes() + " ");
                                        System.out.print(m.getMaintainanceLevel() + " ");
                                        System.out.print(m.getOccupancy());
                                        break;
                                    }
                                    case 'p': {
                                        pedestrianRoad p = (pedestrianRoad) fromV.getToVertexLL().get(j);
                                        System.out.print(p.getWidthOfRoad() + " ");
                                        System.out.print(p.getScenicView() + " ");
                                        System.out.print(p.getOccupancy());
                                        break;
                                    }
                                    case 'c': {
                                        cyclistRoad c = (cyclistRoad) fromV.getToVertexLL().get(j);
                                        System.out.print(c.getCurvature());
                                        break;
                                    }
                                    case 's': {
                                        swamps s = (swamps) fromV.getToVertexLL().get(j);
                                        System.out.print(s.getDifficulty());
                                        break;
                                    }
                                    case 'l': {
                                        lakes la = (lakes) fromV.getToVertexLL().get(j);
                                        System.out.print(la.getWidth() + " ");
                                        System.out.print(la.getTidalLevel() + " ");
                                        System.out.print(la.getDepth());
                                        break;
                                    }
                                }
                                System.out.println("");
                            }
                        }
                        for (int j = 0; j < FirstToVobj.getLandmarkLinkedList().size(); j++) {
                            System.out.print(FirstToVobj.getLandmarkLinkedList().get(j).getId() + " " + FirstToVobj.getLandmarkLinkedList().get(j).getName() + " " + FirstToVobj.getLandmarkLinkedList()
                                    .get(j).getLocation() + " ");
                            switch (FirstToVobj.getLandmarkLinkedList().get(j).getType().charAt(0)) {
                                case 't': {
                                    trafficLight t = (trafficLight) FirstToVobj.getLandmarkLinkedList().get(j);
                                    System.out.println(t.getTimeRed());
                                    break;
                                }

                                case 'b': {
                                    bench b = (bench) FirstToVobj.getLandmarkLinkedList().get(j);
                                    System.out.println(b.getCarryingCapacity() + " " + b.getDonatedBy());
                                    break;
                                }

                                case 's': {
                                    shop s = (shop) FirstToVobj.getLandmarkLinkedList().get(j);
                                    System.out.println(s.getOpeningTime() + " " + s.getShoptype() + " " + s.getRating()
                                            + " " + s.getExpenseLevel());
                                    break;
                                }

                                case 'w': {
                                    washroom w = (washroom) FirstToVobj.getLandmarkLinkedList().get(j);
                                    System.out.println(w.getCostPerUse() + " " + w.getCleanlinessLevel());
                                    break;
                                }
                            }

                        }
                    }
                } catch (LandmarkDNEException e) {
                    System.out.println("landmark does not exist");
                }
            }//Printing finally.
        }
    }
}
