import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.min;

class Vertex{
    private String name;
    private String road;
    private String type;
    private String FromVertex;
    private int LengthOfRoad;
    private int SpeedOfRoad;
    private double cost;
    private double EffectiveSpeed;
    private double TravellerSpeed;
    private int weight;

    int getWeight() {
        return weight;
    }

    void setWeight(int weight) {
        this.weight = weight;
    }

    double getTravellerSpeed() {
        return TravellerSpeed;
    }

    void setTravellerSpeed(double travellerSpeed) {
        TravellerSpeed = travellerSpeed;
    }

    String getFromVertex() {
        return FromVertex;
    }

    void setFromVertex(String fromVertex) {
        FromVertex = fromVertex;
    }

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

    void setCost(double cost) {
        this.cost = cost;
    }

    double getEffectiveSpeed() {
        return EffectiveSpeed;
    }

    void setEffectiveSpeed(double effectiveSpeed) {
        EffectiveSpeed = effectiveSpeed;
    }

    void CalculateCost(){
//        System.out.println("Weight is "+weight );
        cost=((double)LengthOfRoad)/(EffectiveSpeed);
        cost=cost*weight;
//        System.out.println("Cost is "+cost);
    }

    void printc(){
//        System.out.println(cost+" "+EffectiveSpeed);
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
        double CalculatedSpeed=this.getSpeedOfRoad()*(1-((double)Occupancy)/(100*getNoOfLanes()));
//        System.out.println("Calculated Speed is "+CalculatedSpeed+" and EffectiveSpeed is "+min(CalculatedSpeed,this.getTravellerSpeed()));
        this.setEffectiveSpeed(min(CalculatedSpeed,this.getTravellerSpeed()));
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
        double CalculatedSpeed=this.getSpeedOfRoad()*frac;
        this.setEffectiveSpeed(min(CalculatedSpeed,this.getTravellerSpeed()));
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
        this.setEffectiveSpeed(min(effective,this.getTravellerSpeed()));
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
        double CalculatedSpeed=temp/difficulty;
        this.setEffectiveSpeed(min(CalculatedSpeed,this.getTravellerSpeed()));
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
        this.setEffectiveSpeed(min(this.getSpeedOfRoad(),this.getTravellerSpeed()));
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

class Traveller{
    private String name;
    private String type;
    private double speed;

    double getSpeed() {
        return speed;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }

    void setName(String name) {
        this.name = name;
    }

    void sort(LinkedList<Vertex> Roads){
        Roads.sort(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                if(o1.getFromVertex().equals(o2.getFromVertex())) {
                    if (Double.compare(abs(o1.getCost() - o2.getCost()), 0.0001) < 0)
                        return o1.getRoad().compareTo(o2.getRoad());
                    else {
                        if (o1.getCost() > o2.getCost())
                            return 1;
                        else
                            return -1;
                    }
                }
                else
                    return o1.getFromVertex().compareTo(o2.getFromVertex());
            }
        });
    }

    void Print(LinkedList<Vertex> Roads){
        for (Vertex road : Roads) {
            System.out.print(road.getFromVertex() + " ");
            System.out.print(road.getName() + " "
                    + road.getRoad() + " "
                    + road.getLengthOfRoad() + " "
                    + road.getSpeedOfRoad() + " ");

            switch (road.getType().charAt(0)) {
                case 'm': {
                    motorway m = (motorway) road;
                    System.out.print(m.getTollPrice() + " ");
                    System.out.print(m.getNoOfLanes() + " ");
                    System.out.print(m.getMaintainanceLevel() + " ");
                    System.out.print(m.getOccupancy());
                    break;
                }
                case 'p': {
                    pedestrianRoad p = (pedestrianRoad) road;
                    System.out.print(p.getWidthOfRoad() + " ");
                    System.out.print(p.getScenicView() + " ");
                    System.out.print(p.getOccupancy());
                    break;
                }
                case 'c': {
                    cyclistRoad c = (cyclistRoad) road;
                    System.out.print(c.getCurvature());
                    break;
                }
                case 's': {
                    swamps s = (swamps) road;
                    System.out.print(s.getDifficulty());
                    break;
                }
                case 'l': {
                    lakes la = (lakes) road;
                    System.out.print(la.getWidth() + " ");
                    System.out.print(la.getTidalLevel() + " ");
                    System.out.print(la.getDepth());
                    break;
                }
            }
            System.out.println(" ");
        }
    }
}

class Motorist extends Traveller{
    private int[] weights=new int[]{1,-1,-1,-1,-1 };
    private LinkedList<Vertex> Roads= new LinkedList<>();

    void CalculateAllCost() {
        for (Vertex vertex : Roads) {
            String type = vertex.getType();
            if (type.charAt(0) == 'm') {
                motorway v = (motorway) vertex;
                v.setWeight(weights[0]);
                v.setTravellerSpeed(this.getSpeed());
                v.CalculateSpeed();
                v.CalculateCost();
            } else {
                System.out.println("Something bad is happening!");
            }
        }
    }

    void setRoads(LinkedList<Vertex> roads) {
        Roads = roads;
    }
}

class Swimmers extends Traveller{
    private int[] weights=new int[]{3,2,2,3,1 };
    private LinkedList<Vertex> Roads= new LinkedList<>();

    void CalculateAllCost() {
        for (Vertex vertex : Roads) {
            String type = vertex.getType();
            switch (type.charAt(0)) {
                case 'm': {
                    motorway v = (motorway) vertex;
                    v.setWeight(weights[0]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'p': {
                    pedestrianRoad v = (pedestrianRoad) vertex;
                    v.setWeight(weights[1]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'c': {
                    cyclistRoad v = (cyclistRoad) vertex;
                    v.setWeight(weights[2]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 's': {
                    swamps v = (swamps) vertex;
                    v.setWeight(weights[3]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'l': {
                    lakes v = (lakes) vertex;
                    v.setWeight(weights[4]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
            }
        }
    }

    void setRoads(LinkedList<Vertex> roads) {
        Roads = roads;
    }
}

class Cyclists extends Traveller{
    private int[] weights=new int[]{2,3,1,-1,-1 };
    private LinkedList<Vertex> Roads= new LinkedList<>();

    void CalculateAllCost() {
        for (Vertex vertex : Roads) {
            String type = vertex.getType();
            switch (type.charAt(0)) {
                case 'm': {
//                    System.out.println("Motorway found");
                    motorway v = (motorway) vertex;
                    v.setWeight(weights[0]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'p': {
                    pedestrianRoad v = (pedestrianRoad) vertex;
                    v.setWeight(weights[1]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'c': {
                    cyclistRoad v = (cyclistRoad) vertex;
                    v.setWeight(weights[2]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                default:{
                    System.out.println("Something bad is happening!");
                }
            }
        }
    }

    void setRoads(LinkedList<Vertex> roads) {
        Roads = roads;
    }
}

class OldWalkers extends Traveller{
    private int[] weights=new int[]{5,1,-1,-1,-1 };
    private LinkedList<Vertex> Roads= new LinkedList<>();

    void CalculateAllCost() {
        for (Vertex vertex : Roads) {
            String type = vertex.getType();
            switch (type.charAt(0)) {
                case 'm': {
                    motorway v = (motorway) vertex;
                    v.setWeight(weights[0]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'p': {
                    pedestrianRoad v = (pedestrianRoad) vertex;
                    v.setWeight(weights[1]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                default:{
                    System.out.println("Something bad is happening!");
                }
            }
        }
    }

    void setRoads(LinkedList<Vertex> roads) {
        Roads = roads;
    }
}

class NewWalkers extends Traveller{
    private int[] weights=new int[]{3,1,2,4,-1 };

    private LinkedList<Vertex> Roads= new LinkedList<>();

    void CalculateAllCost() {
        for (Vertex vertex : Roads) {
            String type = vertex.getType();
            switch (type.charAt(0)) {
                case 'm': {
                    motorway v = (motorway) vertex;
                    v.setWeight(weights[0]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'p': {
                    pedestrianRoad v = (pedestrianRoad) vertex;
                    v.setWeight(weights[1]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 'c': {
                    cyclistRoad v = (cyclistRoad) vertex;
                    v.setWeight(weights[2]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                case 's': {
                    swamps v = (swamps) vertex;
                    v.setWeight(weights[3]);
                    v.setTravellerSpeed(this.getSpeed());
                    v.CalculateSpeed();
                    v.CalculateCost();
                    break;
                }
                default:{
                    System.out.println("Something bad is happening!");
                }
            }
        }
    }

    void setRoads(LinkedList<Vertex> roads) {
        Roads = roads;
    }
}

public class TestClass {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        for(int i=0;i<testcase;i++){

            LinkedList<Vertex> MotoristLL= new LinkedList<>();
            LinkedList<Vertex> SwimmersLL= new LinkedList<>();
            LinkedList<Vertex> CyclistsLL= new LinkedList<>();
            LinkedList<Vertex> OldWalkersLL= new LinkedList<>();
            LinkedList<Vertex> NewWalkersLL= new LinkedList<>();

            int edges=Integer.parseInt(br.readLine());
            for(int j=0;j<edges;j++){
                String inputstr = br.readLine();
                String[] words=inputstr.split(" ");
                switch (words[2].charAt(0)) {
                    case 'm': {
                        motorway vertex = new motorway();
                        vertex.setName(words[1]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setTollPrice(Integer.parseInt(words[6]));
                        vertex.setNoOfLanes(Integer.parseInt(words[7]));
                        vertex.setMaintainanceLevel(Integer.parseInt(words[8]));
                        vertex.setOccupancy(Integer.parseInt(words[9]));
                        vertex.setFromVertex(words[0]);
                        MotoristLL.add(vertex);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        OldWalkersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'p': {
                        pedestrianRoad vertex = new pedestrianRoad();
                        vertex.setName(words[1]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setWidthOfRoad(Integer.parseInt(words[6]));
                        vertex.setScenicView(Integer.parseInt(words[7]));
                        vertex.setOccupancy(Integer.parseInt(words[8]));
                        vertex.setFromVertex(words[0]);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        OldWalkersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'c': {
                        cyclistRoad vertex = new cyclistRoad();
                        vertex.setName(words[1]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setCurvature(Integer.parseInt(words[6]));
                        vertex.setFromVertex(words[0]);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 's': {
                        swamps vertex = new swamps();
                        vertex.setName(words[1]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setDifficulty(Integer.parseInt(words[6]));
                        vertex.setFromVertex(words[0]);
                        SwimmersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'l': {
                        lakes vertex = new lakes();
                        vertex.setName(words[1]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setWidth(Integer.parseInt(words[6]));
                        vertex.setTidalLevel(Integer.parseInt(words[7]));
                        vertex.setDepth(Integer.parseInt(words[8]));
                        vertex.setFromVertex(words[0]);
                        SwimmersLL.add(vertex);
                        break;
                    }
                }

                //Doing the same again as road is bidirectional.
                switch (words[2].charAt(0)){
                    case 'm' : {
                        motorway vertex = new motorway();
                        vertex.setName(words[0]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setTollPrice(Integer.parseInt(words[6]));
                        vertex.setNoOfLanes(Integer.parseInt(words[7]));
                        vertex.setMaintainanceLevel(Integer.parseInt(words[8]));
                        vertex.setOccupancy(Integer.parseInt(words[9]));
                        vertex.setFromVertex(words[1]);
                        MotoristLL.add(vertex);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        OldWalkersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'p': {
                        pedestrianRoad vertex = new pedestrianRoad();
                        vertex.setName(words[0]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setWidthOfRoad(Integer.parseInt(words[6]));
                        vertex.setScenicView(Integer.parseInt(words[7]));
                        vertex.setOccupancy(Integer.parseInt(words[8]));
                        vertex.setFromVertex(words[1]);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        OldWalkersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'c':{
                        cyclistRoad vertex = new cyclistRoad();
                        vertex.setName(words[0]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setCurvature(Integer.parseInt(words[6]));
                        vertex.setFromVertex(words[1]);
                        SwimmersLL.add(vertex);
                        CyclistsLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 's':{
                        swamps vertex = new swamps();
                        vertex.setName(words[0]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setDifficulty(Integer.parseInt(words[6]));
                        vertex.setFromVertex(words[1]);
                        SwimmersLL.add(vertex);
                        NewWalkersLL.add(vertex);
                        break;
                    }
                    case 'l':{
                        lakes vertex = new lakes();
                        vertex.setName(words[0]);
                        vertex.setType(words[2]);
                        vertex.setRoad(words[3]);
                        vertex.setLengthOfRoad(Integer.parseInt(words[4]));
                        vertex.setSpeedOfRoad(Integer.parseInt(words[5]));
                        vertex.setWidth(Integer.parseInt(words[6]));
                        vertex.setTidalLevel(Integer.parseInt(words[7]));
                        vertex.setDepth(Integer.parseInt(words[8]));
                        vertex.setFromVertex(words[1]);
                        SwimmersLL.add(vertex);
                        break;
                    }
                }
            }
            int NoOfQueries=Integer.parseInt(br.readLine());
            for(int j=0;j<NoOfQueries;j++){
                String Query= br.readLine();
                String[] QueryWords=Query.split(" ");
                switch (QueryWords[2].toLowerCase().charAt(0)){
                    case 'm' : {
                        Motorist vertex= new Motorist();
                        vertex.setName(QueryWords[0]);
                        vertex.setSpeed(Double.parseDouble(QueryWords[1]));
                        vertex.setRoads(MotoristLL);
                        vertex.CalculateAllCost();
                        vertex.sort(MotoristLL);
                        vertex.Print(MotoristLL);
                        break;
                    }

                    case 's' : {
                        Swimmers vertex= new Swimmers();
                        vertex.setName(QueryWords[0]);
                        vertex.setSpeed(Double.parseDouble(QueryWords[1]));
                        vertex.setRoads(SwimmersLL);
                        vertex.CalculateAllCost();
                        vertex.sort(SwimmersLL);
                        vertex.Print(SwimmersLL);
                        break;
                    }

                    case 'c' : {
                        Cyclists vertex= new Cyclists();
                        vertex.setName(QueryWords[0]);
                        vertex.setSpeed(Double.parseDouble(QueryWords[1]));
                        vertex.setRoads(CyclistsLL);
                        vertex.CalculateAllCost();
                        vertex.sort(CyclistsLL);
                        vertex.Print(CyclistsLL);
                        break;
                    }

                    case 'o' : {
                        OldWalkers vertex = new OldWalkers();
                        vertex.setName(QueryWords[0]);
                        vertex.setSpeed(Double.parseDouble(QueryWords[1]));
                        vertex.setRoads(OldWalkersLL);
                        vertex.CalculateAllCost();
                        vertex.sort(OldWalkersLL);
                        vertex.Print(OldWalkersLL);
                        break;
                    }

                    case 'n' : {
                        NewWalkers vertex = new NewWalkers();
                        vertex.setName(QueryWords[0]);
                        vertex.setSpeed(Double.parseDouble(QueryWords[1]));
                        vertex.setRoads(NewWalkersLL);
                        vertex.CalculateAllCost();
                        vertex.sort(NewWalkersLL);
                        vertex.Print(NewWalkersLL);
                        break;
                    }
                }
            }
        }
    }
}
