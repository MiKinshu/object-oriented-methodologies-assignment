//{
// Work inspired from
// GeeksforGeeks.com
//programcreek.com
//o7planning.org
//stackoverflow.com
// }

package sample;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;


class AlreadyExistsException extends Exception{
    AlreadyExistsException(){
        super();
    }
}

class InvalidArgumentsException extends Exception{
    InvalidArgumentsException(){
        super();
    }
}

class QueryNotFoundException extends Exception{
    QueryNotFoundException(){
        super();
    }
}

class Vertex{
    private String name;
    private Double XCoord, YCoord;
    private int weight, isselected=-1;

    int getIsselected() {
        return isselected;
    }

    void setIsselected(int isselected) {
        this.isselected = isselected;
    }

    public Vertex(String name, Double XCoord, Double YCoord, int weight) {
        this.name = name;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.weight = weight;
    }

    Vertex(Vertex vertex) {
        this.name=vertex.getName();
        this.XCoord=vertex.getXCoord();
        this.YCoord=vertex.getYCoord();
        this.weight=vertex.getWeight();
        this.isselected=vertex.getIsselected();
    }

    int getWeight() {
        return weight;
    }

    void setWeight(int weight) {
        this.weight = weight;
    }

    Vertex(String name, Double XCoord, Double YCoord) {
        this.name = name;
        this.XCoord = XCoord;
        this.YCoord = YCoord;
        this.weight=-1;
    }

    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    Double getXCoord() {
        return XCoord;
    }
    void setXCoord(Double XCoord) {
        this.XCoord = XCoord;
    }
    Double getYCoord() {
        return YCoord;
    }
    void setYCoord(Double YCoord) {
        this.YCoord = YCoord;
    }
}

class FromV {
    private String name;
    private Double XCoord, YCoord;
    private LinkedList<Vertex> ToVertexLL = new LinkedList<>();
    private String path;
    String getName() {
        return name;
    }

    FromV(Vertex v) {
        this.name = v.getName();
        this.XCoord = v.getXCoord();
        this.YCoord = v.getYCoord();
        this.ToVertexLL = new LinkedList<>();
    }

    FromV(String name) {
        this.name = name;
    }

    FromV(){}

    LinkedList<Vertex> getToVertexLL() {
        return ToVertexLL;
    }
    Double getXCoord() {
        return XCoord;
    }
    void setXCoord(Double XCoord) {
        this.XCoord = XCoord;
    }
    Double getYCoord() {
        return YCoord;
    }
    void setYCoord(Double YCoord) {
        this.YCoord = YCoord;
    }

    private void PrintPath(int[] parent, int goal, HashMap<Integer, String> map){
        if(parent[goal]==-1)
            return;
        PrintPath(parent,parent[goal],map);
        path=path+map.get(goal)+" ";
    }

    String Dijkstra(LinkedList<FromV> graphL, String fromv, String tov, HashMap<String, Integer> vertexno, HashMap<Integer, String> map, LinkedList<Vertex> vertexLinkedList) throws QueryNotFoundException, InvalidArgumentsException {
        try {
            map.clear();
            vertexno.clear();
            for(int i=0;i<vertexLinkedList.size();i++){
                vertexno.put(vertexLinkedList.get(i).getName(),i);
                map.put(i,vertexLinkedList.get(i).getName());
            }
            int SourceV = vertexno.get(fromv);
            int GoalV = vertexno.get(tov);
            System.out.println("Source is " + SourceV + " and GoalV is " + GoalV);

            if (!vertexno.containsKey(fromv) || !vertexno.containsKey(tov)) {
                throw new QueryNotFoundException();
            }


            int[][] graph = new int[vertexno.size()][vertexno.size()];
            for (int i = 0; i < vertexno.size(); i++) {
                for (int j = 0; j < vertexno.size(); j++) {
                    graph[i][j] = 0;
                }
            }

            for (FromV fromV : graphL) {
                for (int j = 0; j < fromV.getToVertexLL().size(); j++) {
                    int x = vertexno.get(fromV.getName());
                    int y = vertexno.get(fromV.getToVertexLL().get(j).getName());
                    graph[x][y] = fromV.getToVertexLL().get(j).getWeight();
                }
            }

            System.out.println("Now printing graph");
            for (int i = 0; i < vertexno.size(); i++) {
                for (int j = 0; j < vertexno.size(); j++) {
                    System.out.print(graph[i][j] + " ");
                }
                System.out.println();
            }

            int[] DistanceArray = new int[vertexno.size()];
            Boolean[] IsVisited = new Boolean[vertexno.size()];
            int[] parent = new int[vertexno.size()];

            for (int i = 0; i < vertexno.size(); i++) {
                DistanceArray[i] = Integer.MAX_VALUE;
                IsVisited[i] = false;
                parent[i] = -1;
            }

            DistanceArray[SourceV] = 0;

            for (int i = 0; i < vertexno.size() - 1; i++) {
                int MinDist = Integer.MAX_VALUE, minind = -1;
                System.out.println("Distance array is: ");
                for (int j = 0; j < vertexno.size(); j++) {
                    System.out.print(DistanceArray[j] + " ");
                }

                System.out.println();
                System.out.println("Bool array is: ");
                for (int j = 0; j < vertexno.size(); j++) {
                    System.out.print(IsVisited[j] + " ");
                }

                System.out.println();

                for (int j = 0; j < vertexno.size(); j++) {
                    if (IsVisited[j]==false && DistanceArray[j] <= MinDist) {
                        MinDist = DistanceArray[j];
                        minind = j;
                    }
                }

                System.out.println("Min ind is " + minind);

                IsVisited[minind] = true;

                for (int j = 0; j < vertexno.size(); j++) {
                    if (!IsVisited[j] && graph[minind][j] != 0 && DistanceArray[minind] != Integer.MAX_VALUE) {
                        if(DistanceArray[minind] + graph[minind][j] < DistanceArray[j]){
                            DistanceArray[j] = DistanceArray[minind] + graph[minind][j];
                            parent[j] = minind;
                        }
                    }
                }
            }

            path = fromv + " ";
            PrintPath(parent, GoalV, map);
            if (!path.equals(fromv + " "))
                System.out.print(path);
            return path;
        }
        catch (NullPointerException e){
            throw new InvalidArgumentsException();
        }
    }
}

public class Main extends Application {
    private LinkedList<FromV> FromVertexLL= new LinkedList<>();
    private LinkedList<Vertex> vertexLinkedList= new LinkedList<>();
    private HashMap<String,Integer> vertexno= new HashMap<>();
    private HashMap<Integer,String> map= new HashMap<>();
    private HashMap<Circle,Vertex> circleVertexHashMap=new HashMap<>();
    private HashMap<Vertex,Circle> vertexCircleHashMap=new HashMap<>();
    private LinkedList<Circle> circleLinkedList= new LinkedList<>();
    private LinkedList<Line> lineLinkedList= new LinkedList<>();
    private Circle lastcircle=null;
    private int VertexMode=-1;
    private int EdgeMode=1;
    private double dragBaseX;
    private double dragBaseY;
    private int NoOfVertex=0;

    private void AddVertex(String Vname, String Vxa, String Vya, Circle circle) throws InvalidArgumentsException, AlreadyExistsException, NumberFormatException{

        try {
            Double Vx = Double.parseDouble(Vxa);
            Double Vy = Double.parseDouble(Vya);
            Vertex node= new Vertex(Vname,Vx,Vy);
            if(vertexno.containsKey(Vname))
                throw new AlreadyExistsException();
            else {
                vertexLinkedList.add(node);
                vertexno.put(Vname, NoOfVertex);
                map.put(NoOfVertex, Vname);
                circleVertexHashMap.put(circle,node);
                vertexCircleHashMap.put(node,circle);
                NoOfVertex++;
                vertexLinkedList.sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                vertexLinkedList.sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex Added.\nClick Ok to continue.");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidArgumentsException();
        }
    }

    private Vertex SearchVertex(String Vname)throws QueryNotFoundException{
        if(!vertexno.containsKey(Vname))
            throw new QueryNotFoundException();
        else {
            int key = Collections.binarySearch(vertexLinkedList, new Vertex(Vname, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            if (key < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex not found.\nClick Ok to continue.");
                alert.showAndWait();
            } else {
                return vertexLinkedList.get(key);
            }
        }
        return null;
    }

    private void DeleteVertex(String Vname) throws QueryNotFoundException{
        if(!vertexno.containsKey(Vname)) {
            throw new QueryNotFoundException();
        } else {
            int key = Collections.binarySearch(vertexLinkedList, new Vertex(Vname, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            if (key < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex not found.\nClick Ok to continue.");
                alert.showAndWait();
            } else {
                for(int i=0;i<FromVertexLL.size();i++){
                    if(FromVertexLL.get(i).getName().equals(Vname))
                        FromVertexLL.remove(i);
                }
                for(int i=0;i<FromVertexLL.size();i++){
                    for(int j=0;j<FromVertexLL.get(i).getToVertexLL().size();j++){
                        if(FromVertexLL.get(i).getToVertexLL().get(j).getName().equals(Vname))
                            FromVertexLL.get(i).getToVertexLL().remove(j);
                    }
                }
                int no=vertexno.get(Vname);
                vertexno.remove(Vname);
                map.remove(no);
                vertexLinkedList.sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex " + vertexLinkedList.get(key).getName() + " Deleted.\nClick Ok to continue");
                vertexLinkedList.remove(key);
                alert.showAndWait();
            }
        }
    }

    private Vertex AddEdge(String FromV, String ToV, String weight1)throws QueryNotFoundException, InvalidArgumentsException, AlreadyExistsException{
        //searching elemets in the list.
        try {
            System.out.println("In Add Edge: weight is "+weight1);
            int weight = Integer.parseInt(weight1);
            System.out.println("In Add Edge: FromV "+FromV+" toV is "+ToV);
            if (!vertexno.containsKey(FromV) || !vertexno.containsKey(ToV)) {
                throw new QueryNotFoundException();
            }
            int fkey = Collections.binarySearch(vertexLinkedList, new Vertex(FromV, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            Vertex returnv=vertexLinkedList.get(fkey);
            System.out.println("fkey is " + vertexLinkedList.get(fkey).getName());
            int tkey = Collections.binarySearch(vertexLinkedList, new Vertex(ToV, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("tkey is " + vertexLinkedList.get(tkey).getName());
            int index = -1;
            for (int l = 0; l < FromVertexLL.size(); l++) {
                if (FromVertexLL.get(l).getName().equals(FromV)) {
                    index = l;
                    break;
                }
            }
            System.out.println("Index is " + index+" FromVertex Linked List size is "+FromVertexLL.size());
            if (index != -1) {
                System.out.println("Index!=-1");
                for (int i = 0; i < FromVertexLL.get(index).getToVertexLL().size(); i++) {
                    if (FromVertexLL.get(index).getToVertexLL().get(i).getName().equals(ToV)) {
                        System.out.println("I is "+i);
                        throw new AlreadyExistsException();
                    }
                }
                System.out.println("VertexLinkedSize is "+vertexLinkedList.size()+" tkey is "+tkey);
                vertexLinkedList.get(tkey).setWeight(weight);
                Vertex vertex = new Vertex(vertexLinkedList.get(tkey));
                System.out.println("Weight put in vertexLL is " + vertexLinkedList.get(tkey).getWeight());
                FromVertexLL.get(index).getToVertexLL().add(vertex);
                System.out.println("Weight put in ActualLL is " + FromVertexLL.get(index).getToVertexLL().get(FromVertexLL.get(index).getToVertexLL().size() - 1).getWeight());
            }
            else {
                System.out.println("Index == -1");
                FromV fromV = new FromV(vertexLinkedList.get(fkey));
                vertexLinkedList.get(tkey).setWeight(weight);
                Vertex vertex = new Vertex(vertexLinkedList.get(tkey));
                System.out.println("Weight put in vertexLL is " + vertexLinkedList.get(tkey).getWeight());
                fromV.getToVertexLL().add(vertex);
                FromVertexLL.add(fromV);
                System.out.println("Weight put in ActualLL is " + FromVertexLL.get(FromVertexLL.size() - 1).getToVertexLL().get(FromVertexLL.get(FromVertexLL.size() - 1).getToVertexLL().size() - 1).getWeight());
            }
            FromVertexLL.sort(new Comparator<sample.FromV>() {
                @Override
                public int compare(sample.FromV o1, sample.FromV o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for(int i=0;i<FromVertexLL.size();i++){
                FromVertexLL.get(i).getToVertexLL().sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Edge added.\nClick Ok to continue.");
            alert.showAndWait();
            return returnv;
        }
        catch (NumberFormatException e){
            throw new InvalidArgumentsException();
        }
    }

    private void ModifyVertexInGraph(String Vname, Double newx, Double newy){
        for(int i=0;i<FromVertexLL.size();i++){
            if(FromVertexLL.get(i).getName().equals(Vname)){
                FromVertexLL.get(i).setXCoord(newx);
                FromVertexLL.get(i).setYCoord(newy);
            }
            for(int j=0;j<FromVertexLL.get(i).getToVertexLL().size();j++){
                if(FromVertexLL.get(i).getToVertexLL().get(j).getName().equals(Vname)){
                    FromVertexLL.get(i).getToVertexLL().get(j).setXCoord(newx);
                    FromVertexLL.get(i).getToVertexLL().get(j).setYCoord(newy);
                }
            }
        }
    }

    private void SearchEdge(String FromVS, String ToVS) throws QueryNotFoundException{
        int fkey=Collections.binarySearch(FromVertexLL, new FromV(FromVS), new Comparator<FromV>() {
            @Override
            public int compare(FromV o1, FromV o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println("fkey is "+fkey);
        int tkey=-1;
        if(fkey>=0) {
            tkey = Collections.binarySearch(FromVertexLL.get(fkey).getToVertexLL(), new Vertex(ToVS, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("tkey is "+tkey);
        }
        if(fkey<0||tkey<0){
            throw new QueryNotFoundException();
        }
        if(tkey>=0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Edge found with weight "+FromVertexLL.get(fkey).getToVertexLL().get(tkey).getWeight()+ "\nClick Ok to continue.");
            alert.showAndWait();
        }
    }

    private void DeleteEdge(String FromVS, String ToVS) throws QueryNotFoundException{
        int fkey=Collections.binarySearch(FromVertexLL, new FromV(FromVS), new Comparator<FromV>() {
            @Override
            public int compare(FromV o1, FromV o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println("fkey is "+fkey);
        int tkey=-1;
        if(fkey>=0) {
            tkey = Collections.binarySearch(FromVertexLL.get(fkey).getToVertexLL(), new Vertex(ToVS, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("tkey is "+tkey);
        }
        if(fkey<0||tkey<0){
            throw new QueryNotFoundException();
        }
        if(tkey>=0){
            System.out.println("tkey>=0");
            FromVertexLL.get(fkey).getToVertexLL().remove(tkey);
            FromVertexLL.sort(new Comparator<sample.FromV>() {
                @Override
                public int compare(sample.FromV o1, sample.FromV o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for(int i=0;i<FromVertexLL.size();i++){
                FromVertexLL.get(i).getToVertexLL().sort(new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Edge Deleted"+ "\nClick Ok to continue.");
            alert.showAndWait();
        }
    }

    private void ModifyEdge(String FromVS, String ToVS, String weight1) throws InvalidArgumentsException, QueryNotFoundException {
        try {
            int weight = Integer.parseInt(weight1);
            int fkey = Collections.binarySearch(FromVertexLL, new FromV(FromVS), new Comparator<FromV>() {
                @Override
                public int compare(FromV o1, FromV o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("fkey is " + fkey);
            int tkey = -1;
            if (fkey >= 0) {
                tkey = Collections.binarySearch(FromVertexLL.get(fkey).getToVertexLL(), new Vertex(ToVS, 0.0, 0.0), new Comparator<Vertex>() {
                    @Override
                    public int compare(Vertex o1, Vertex o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                System.out.println("tkey is " + tkey);
            }
            if(fkey<0||tkey<0)
                throw new QueryNotFoundException();

            System.out.println("tkey>=0");
            FromVertexLL.get(fkey).getToVertexLL().get(tkey).setWeight(weight);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Edge Modified with new weight " + FromVertexLL.get(fkey).getToVertexLL().get(tkey).getWeight() + "\nClick Ok to continue.");
            alert.showAndWait();
        }
        catch (NumberFormatException e){
            throw new InvalidArgumentsException();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Canvas canvas= new Canvas();
        Group root = new Group(canvas);
        Scene scene= new Scene(root);
        TextInputDialog td = new TextInputDialog(null);
        //deleting vertex
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {//add functionality for deleting corrosponding edges.
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()==KeyCode.DELETE&&VertexMode==1) {
                    System.out.println("Key detected");
                    for (int i = 0; i < circleLinkedList.size(); i++) {
                        if (circleVertexHashMap.get(circleLinkedList.get(i)).getIsselected() == 1) {
                            Circle circle=circleLinkedList.get(i);
                            System.out.println("Deleting " + circleVertexHashMap.get(circleLinkedList.get(i)).getName());
                            try {
                                DeleteVertex(circleVertexHashMap.get(circleLinkedList.get(i)).getName());
                            } catch (QueryNotFoundException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle(null);
                                alert.setHeaderText(null);
                                alert.setContentText("Some Strange Error in deleting Vertex.");
                                alert.showAndWait();
                            }
                            vertexLinkedList.remove(circleVertexHashMap.get(circleLinkedList.get(i)));
                            root.getChildren().remove(circleLinkedList.get(i));
                            //Redrawing canvas.
                            for(int j=0;j<lineLinkedList.size();j++){
                                if(lineLinkedList.get(j).getStartX()==circle.getCenterX()&&lineLinkedList.get(j).getStartY()==circle.getCenterY()
                                        ||lineLinkedList.get(j).getEndX()==circle.getCenterX()&&lineLinkedList.get(j).getEndY()==circle.getCenterY()){
                                    root.getChildren().remove(lineLinkedList.get(j));
                                }
                            }

                        }
                    }
                }
                if(keyEvent.getCode()==KeyCode.M){
                    System.out.println("Edge is "+EdgeMode+"Vertex is "+VertexMode);
                    if(VertexMode==1)
                        VertexMode=-1;
                    else if(VertexMode==-1) {
                        VertexMode = 1;
                        System.out.println("VertexMode");
                    }
                    if(EdgeMode==-1) {
                        EdgeMode = 1;
                        System.out.println("EdgeMode");
                    }
                    else if(EdgeMode==1)
                        EdgeMode=-1;
                    System.out.println("Edge is "+EdgeMode+"Vertex is "+VertexMode);
                }
                if(keyEvent.getCode()==KeyCode.D){
                    td.setHeaderText("Enter from vertex");
                    td.setContentText(null);
                    td.showAndWait();
                    String fromv=td.getEditor().getText();
                    td.setHeaderText("Enter To vertex");
                    td.setContentText(null);
                    td.showAndWait();
                    Canvas canvas1=new Canvas();
                    Group root1=new Group(canvas1);
                    Scene scene1=new Scene(root1);
                    Stage Stage1=new Stage();
                    Stage1.setTitle("Dijkstra Graph");
                    String tov=td.getEditor().getText();
                    FromV fromV=new FromV();
                    Path path = new Path();
                    path.setOpacity(5);

                    try {
                        String output=fromV.Dijkstra(FromVertexLL,fromv,tov,vertexno,map,vertexLinkedList);
                        if(output.equals(fromv+" ")){
                            Label label=new Label("No path found.");
                            root1.getChildren().add(label);
                        }
                        else {
                            String outputwords[] = output.split(" ");
                            String start=outputwords[0];
                            Vertex startv=SearchVertex(start);
                            MoveTo moveTo = new MoveTo(startv.getXCoord(), startv.getYCoord());
                            path.getElements().add(moveTo);
                            for (int i = 0; i < outputwords.length - 1; i++) {
                                String fromv1 = outputwords[i];
                                String tov1 = outputwords[i + 1];
                                int weight=0;
                                for (int j = 0; j < FromVertexLL.size(); j++) {
                                    if (FromVertexLL.get(j).getName().equals(fromv1)) {
                                        Circle circle = new Circle();
                                        circle.setLayoutX(FromVertexLL.get(j).getXCoord());
                                        circle.setLayoutY(FromVertexLL.get(j).getYCoord());

                                        circle.setRadius(25);
                                        circle.setFill(Color.TRANSPARENT);
                                        circle.setStroke(Color.BLACK);
                                        circle.setStrokeWidth(3);
                                        root1.getChildren().add(circle);

                                        Circle circle1 = new Circle();

                                        Label label=new Label(fromv1);
                                        label.setFont(Font.font(15));
                                        label.setLayoutX(FromVertexLL.get(j).getXCoord());
                                        label.setLayoutY(FromVertexLL.get(j).getYCoord()-1);
                                        root1.getChildren().add(label);

                                        for (int k = 0; k < FromVertexLL.get(j).getToVertexLL().size(); k++) {
                                            if (FromVertexLL.get(j).getToVertexLL().get(k).getName().equals(tov1)) {
                                                circle1.setLayoutX((FromVertexLL.get(j).getToVertexLL().get(k).getXCoord()));
                                                circle1.setLayoutY(FromVertexLL.get(j).getToVertexLL().get(k).getYCoord());

                                                circle1.setRadius(25);
                                                circle1.setFill(Color.TRANSPARENT);
                                                circle1.setStroke(Color.BLACK);
                                                circle1.setStrokeWidth(3);

                                                root1.getChildren().add(circle1);
                                                LineTo lineTo = new LineTo(circle1.getLayoutX(),circle1.getLayoutY());
                                                path.getElements().add(lineTo);

                                                weight=(FromVertexLL.get(j).getToVertexLL().get(k).getWeight());
                                                Label label1=new Label(tov1);
                                                label1.setFont(Font.font(15));
                                                label1.setLayoutX((FromVertexLL.get(j).getToVertexLL().get(k).getXCoord()));
                                                label1.setLayoutY(FromVertexLL.get(j).getToVertexLL().get(k).getYCoord()-1);
                                                root1.getChildren().add(label1);
                                            }
                                        }
                                        Line line = new Line(circle.getLayoutX(), circle.getLayoutY(), circle1.getLayoutX(), circle1.getLayoutY());
                                        line.setStrokeWidth(4);
                                        root1.getChildren().add(line);

                                        Label labelline=new Label(weight+"");
                                        labelline.setFont(Font.font(15));
                                        labelline.setLayoutX((line.getStartX()+line.getEndX())/2);
                                        labelline.setLayoutY((line.getStartY()+ line.getEndY())/2-1);
                                        root1.getChildren().add(labelline);
                                    }
                                }
                            }
                            Circle circlet = new Circle();
                            circlet.setRadius(10);
                            circlet.setFill(Color.RED);
                            PathTransition pathTransition = new PathTransition();
                            pathTransition.setRate(0.1);
                            pathTransition.setNode(circlet);
                            pathTransition.setPath(path);
                            pathTransition.setCycleCount(Integer.MAX_VALUE);
                            pathTransition.play();
                            root1.getChildren().add(circlet);
                        }
                        Stage1.setScene(scene1);
                        Stage1.show();
                    } catch (QueryNotFoundException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Query not found.\nReconsider your input.");
                        alert.showAndWait();
                    } catch (InvalidArgumentsException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Enter Valid Arguments.");
                        alert.showAndWait();
                    }
                }
            }
        });

        scene.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                System.out.println("DragExited");
            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println("Scene Clicked");
                    Double x = mouseEvent.getSceneX();
                    Double y = mouseEvent.getSceneY();
                    Circle circle = new Circle();
                    circle.setCenterX(x);;
                    circle.setCenterY(y);
                    circle.setRadius(20);
                    System.out.println("X is "+x+" Y is "+y);
                    circle.setFocusTraversable(true);

                    circle.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(EdgeMode==1) {
                                Dragboard dragboard = circle.startDragAndDrop(TransferMode.COPY);
                                System.out.println("Drop Started");
                                ClipboardContent content = new ClipboardContent();
                                content.putString(circleVertexHashMap.get(circle).getName());
                                dragboard.setContent(content);
                            }
                            event.consume();
                        }
                    });
                    circle.setOnDragOver(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if(EdgeMode==1) {
                                System.out.println("DragOver");
                                Dragboard dragboard = event.getDragboard();
                                if (dragboard.hasString()) {
                                    event.acceptTransferModes(TransferMode.COPY);
                                }
                            }
                            event.consume();
                        }
                    });

                    circle.setOnDragDropped(new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if(EdgeMode==1) {
                                Dragboard dragboard = event.getDragboard();
                                if (dragboard.hasString()) {
                                    System.out.println("Drop finished from " + dragboard.getString());
                                    td.setHeaderText("Enter weight of Edge");
                                    double x1 = event.getX();
                                    double y1 = event.getY();
                                    td.setX(x1);
                                    td.setY(y1);
                                    td.showAndWait();
                                    System.out.println(td.getEditor().getText() + " " + circleVertexHashMap.get(circle).getName() + " " + dragboard.getString());
                                    try {
                                        Vertex StartV = AddEdge(dragboard.getString(), circleVertexHashMap.get(circle).getName(), td.getEditor().getText());
                                        Line line = new Line(StartV.getXCoord(), StartV.getYCoord(), circleVertexHashMap.get(circle).getXCoord(), circleVertexHashMap.get(circle).getYCoord());
                                        root.getChildren().add(line);
                                        lineLinkedList.add(line);
                                        line.setStrokeWidth(8);
                                        line.endXProperty().bind(circle.centerXProperty());
                                        line.endYProperty().bind(circle.centerYProperty());
                                        line.startXProperty().bind(vertexCircleHashMap.get(StartV).centerXProperty());
                                        line.startYProperty().bind(vertexCircleHashMap.get(StartV).centerYProperty());
                                        line.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                                            @Override
                                            public void handle(MouseEvent event) {
                                                td.setHeaderText("Enter new weight");
                                                td.setX(x);
                                                td.setY(y);
                                                td.setContentText("Enter 0 to delete edge.");
                                                td.showAndWait();
                                                System.out.println("Line clicked");
                                                int weight=Integer.parseInt(td.getEditor().getText());
                                                if(weight==0) {
                                                    lineLinkedList.remove(line);
                                                    Circle fromc = new Circle(), toc = new Circle();
                                                    for (int i = 0; i < circleLinkedList.size(); i++) {
                                                        if (line.getStartX() == circleLinkedList.get(i).getCenterX() &&
                                                                line.getStartY() == circleLinkedList.get(i).getCenterY())
                                                            fromc = circleLinkedList.get(i);
                                                        if (line.getEndX() == circleLinkedList.get(i).getCenterX() &&
                                                                line.getEndY() == circleLinkedList.get(i).getCenterY())
                                                            toc = circleLinkedList.get(i);
                                                    }
                                                    String fromvname = circleVertexHashMap.get(fromc).getName();
                                                    String tovname = circleVertexHashMap.get(toc).getName();
                                                    root.getChildren().remove(line);
                                                    try {
                                                        DeleteEdge(fromvname, tovname);
                                                    } catch (QueryNotFoundException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle(null);
                                                        alert.setHeaderText(null);
                                                        alert.setContentText("Some strange error in deleting Edge.");
                                                        alert.showAndWait();
                                                    }
                                                }
                                                else {
                                                    Circle fromc = new Circle(), toc = new Circle();
                                                    for (int i = 0; i < circleLinkedList.size(); i++) {
                                                        if (line.getStartX() == circleLinkedList.get(i).getCenterX() &&
                                                                line.getStartY() == circleLinkedList.get(i).getCenterY())
                                                            fromc = circleLinkedList.get(i);
                                                        if (line.getEndX() == circleLinkedList.get(i).getCenterX() &&
                                                                line.getEndY() == circleLinkedList.get(i).getCenterY())
                                                            toc = circleLinkedList.get(i);
                                                    }
                                                    String fromvname = circleVertexHashMap.get(fromc).getName();
                                                    String tovname = circleVertexHashMap.get(toc).getName();
                                                    try {
                                                        ModifyEdge(fromvname,tovname,weight+"");
                                                    } catch (InvalidArgumentsException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle(null);
                                                        alert.setHeaderText(null);
                                                        alert.setContentText("Enter Valid Arguments.");
                                                        alert.showAndWait();
                                                    } catch (QueryNotFoundException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle(null);
                                                        alert.setHeaderText(null);
                                                        alert.setContentText("Some strange error in modifying edge.");
                                                        alert.showAndWait();
                                                    }
                                                }
                                                event.consume();
                                            }
                                        });

                                    } catch (QueryNotFoundException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Strange error in adding edge");
                                        alert.showAndWait();
                                    } catch (InvalidArgumentsException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("Enter Valid Arguments.");
                                        alert.showAndWait();
                                    } catch (AlreadyExistsException e) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle(null);
                                        alert.setHeaderText(null);
                                        alert.setContentText("This Edge already exists.\nTap on Edge to edit this.");
                                        alert.showAndWait();
                                    }
                                }
                                event.consume();
                                for (int i = 0; i < FromVertexLL.size(); i++) {
                                    System.out.print(FromVertexLL.get(i).getName() + " ");
                                    for (int j = 0; j < FromVertexLL.get(i).getToVertexLL().size(); j++) {
                                        System.out.print(FromVertexLL.get(i).getToVertexLL().get(j).getName()
                                                + FromVertexLL.get(i).getToVertexLL().get(j).getWeight() + " ");
                                    }
                                    System.out.println();
                                }
                            }
                        }
                    });

                    circle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            dragBaseX = mouseEvent.getSceneX() - circle.getCenterX();
                            dragBaseY = mouseEvent.getSceneY() - circle.getCenterY();
                            if(VertexMode==1) {
                                System.out.println("Circle clicked " + circleVertexHashMap.get(circle).getName());
                                circleVertexHashMap.get(circle).setIsselected(1);
                                if (lastcircle != null)
                                    circleVertexHashMap.get(lastcircle).setIsselected(-1);
                                lastcircle = circle;
                                circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        if (VertexMode == 1) {
                                            circle.setCenterX( mouseEvent.getSceneX()-dragBaseX);
                                            circle.setCenterY( mouseEvent.getSceneY()-dragBaseY);
                                            circleVertexHashMap.get(circle).setYCoord(mouseEvent.getSceneY());
                                            circleVertexHashMap.get(circle).setXCoord(mouseEvent.getSceneX());
                                            ModifyVertexInGraph(circleVertexHashMap.get(circle).getName(),mouseEvent.getSceneX(),mouseEvent.getSceneY());
                                        }
                                    }
                                });
                                for (int i = 0; i < vertexLinkedList.size(); i++) {
                                    System.out.println(vertexLinkedList.get(i).getName()+" "+vertexLinkedList.get(i).getXCoord()+" "+
                                            vertexLinkedList.get(i).getYCoord());
                                }
                            }
                            mouseEvent.consume();
                        }
                    });
                    td.setHeaderText("Enter name of vertex");
                    td.setX(x);
                    td.setY(y);
                    td.setContentText(null);
                    td.showAndWait();
                    AddVertex(td.getEditor().getText(), x.toString(), y.toString(), circle);
                    td.getEditor().clear();
                    circleLinkedList.add(circle);
                    root.getChildren().add(circle);
                    for (int i = 0; i < vertexLinkedList.size(); i++) {
                        System.out.println(vertexLinkedList.get(i).getName()+" "+vertexLinkedList.get(i).getXCoord()+" "+
                                vertexLinkedList.get(i).getYCoord());
                    }
                }
                catch (InvalidArgumentsException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("Vertex name not allowed.\nReconsider your input");
                    alert.showAndWait();
                } catch (AlreadyExistsException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setContentText("This Vertex name already exists.\nDrag this around to change Co-ordinates.");
                    alert.showAndWait();
                }
            }
        });
        System.out.println("Code processed");
        primaryStage.setTitle("Assignment");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
