import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.abs;

class Vertex{
    private String name;
    private String road;
    private String type;
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

        this.setEffectiveSpeed(this.getSpeedOfRoad()*(1-((float)Occupancy)/(100*getNoOfLanes())));
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
        double effective=((float)this.getSpeedOfRoad()) /curvature;
        this.setEffectiveSpeed(effective);
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
    LinkedList<Vertex> ToVertexLL = new LinkedList<>();
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    LinkedList<Vertex> getToVertexLL() {
        return ToVertexLL;
    }
}

public class TestClass {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testcase = Integer.parseInt(br.readLine());
        for(int i=0;i<testcase;i++){
            int edges=Integer.parseInt(br.readLine());
            LinkedList<FromV> FromVertexLL= new LinkedList<>();
            for(int j=0;j<edges;j++){
                String inputstr = br.readLine();
                String[] words=inputstr.split(" ");
                int index=-1;
                for(int l=0;l<FromVertexLL.size();l++){
                    if(FromVertexLL.get(l).getName().equals(words[0])){
                        index=l;
                        break;
                    }
                }
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
                        break;
                    }
                }

                //Doing the same again as road is bidirectional.
                index=-1;
                for(int l=0;l<FromVertexLL.size();l++){
                    if(FromVertexLL.get(l).getName().equals(words[1])){
                        index=l;
                        break;
                    }
                }
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
                        vertex.CalculateSpeed();
                        vertex.CalculateCost();
                        if(index!=-1){
                            FromVertexLL.get(index).getToVertexLL().add(vertex);
                        }
                        else {
                            FromV node= new FromV();
                            node.setName(words[1]);
                            node.getToVertexLL().add(vertex);
                            FromVertexLL.add(node);
                        }
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
                        vertex.CalculateSpeed();
                        vertex.CalculateCost();
                        if(index!=-1){
                            FromVertexLL.get(index).getToVertexLL().add(vertex);
                        }
                        else {
                            FromV node= new FromV();
                            node.setName(words[1]);
                            node.getToVertexLL().add(vertex);
                            FromVertexLL.add(node);
                        }
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
                        vertex.CalculateSpeed();
                        vertex.CalculateCost();
                        if(index!=-1){
                            FromVertexLL.get(index).getToVertexLL().add(vertex);
                        }
                        else {
                            FromV node= new FromV();
                            node.setName(words[1]);
                            node.getToVertexLL().add(vertex);
                            FromVertexLL.add(node);
                        }
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
                        vertex.CalculateSpeed();
                        vertex.CalculateCost();
                        if(index!=-1){
                            FromVertexLL.get(index).getToVertexLL().add(vertex);
                        }
                        else {
                            FromV node= new FromV();
                            node.setName(words[1]);
                            node.getToVertexLL().add(vertex);
                            FromVertexLL.add(node);
                        }
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
                        vertex.CalculateSpeed();
                        vertex.CalculateCost();
                        if(index!=-1){
                            FromVertexLL.get(index).getToVertexLL().add(vertex);
                        }
                        else {
                            FromV node= new FromV();
                            node.setName(words[1]);
                            node.getToVertexLL().add(vertex);
                            FromVertexLL.add(node);
                        }
                        break;
                    }
                }
            }
            FromVertexLL.sort(new Comparator<FromV>() {
                @Override
                public int compare(FromV o1, FromV o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (FromV fromV : FromVertexLL) {
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

                for (int j = 0; j < fromV.getToVertexLL().size(); j++) {
                    System.out.print(fromV.getName() + " ");
                    System.out.print(fromV.getToVertexLL().get(j).getName() + " "
                            + fromV.getToVertexLL().get(j).getRoad() + " "
                            + fromV.getToVertexLL().get(j).getLengthOfRoad() + " "
                            + fromV.getToVertexLL().get(j).getSpeedOfRoad() + " ");

                    switch (fromV.ToVertexLL.get(j).getType().charAt(0)) {
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
        }
    }
}
