//{
// Work inspired from
// GeeksforGeeks.com
//programcreek.com
//o7planning.org
//stackoverflow.com
// }


    package sample;

    import javafx.application.Application;
    import javafx.event.EventHandler;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;

    import java.io.*;
    import java.util.*;

    class Vertex{
        private String name;
        private Double XCoord, YCoord;
        private int weight;

        public Vertex(String name, Double XCoord, Double YCoord, int weight) {
            this.name = name;
            this.XCoord = XCoord;
            this.YCoord = YCoord;
            this.weight = weight;
        }

        public Vertex(Vertex vertex) {
            this.name=vertex.getName();
            this.XCoord=vertex.getXCoord();
            this.YCoord=vertex.getYCoord();
            this.weight=vertex.getWeight();
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Vertex(String name, Double XCoord, Double YCoord) {
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
        public Double getXCoord() {
            return XCoord;
        }
        public void setXCoord(Double XCoord) {
            this.XCoord = XCoord;
        }
        public Double getYCoord() {
            return YCoord;
        }
        public void setYCoord(Double YCoord) {
            this.YCoord = YCoord;
        }
    }

    class FromV {
        private String name;
        Double XCoord, YCoord;
        LinkedList<Vertex> ToVertexLL = new LinkedList<>();
        String path;
        String getName() {
            return name;
        }

        public FromV(Vertex v) {
            this.name = v.getName();
            this.XCoord = v.getXCoord();
            this.YCoord = v.getYCoord();
            this.ToVertexLL = new LinkedList<>();
        }

        public FromV(String name, Double XCoord, Double YCoord) {
            this.name = name;
            this.XCoord = XCoord;
            this.YCoord = YCoord;
            ToVertexLL = new LinkedList<>();
        }

        public FromV(String name) {
            this.name = name;
        }

        public FromV(){}

        void setName(String name) {
            this.name = name;
        }
        LinkedList<Vertex> getToVertexLL() {
            return ToVertexLL;
        }
        public Double getXCoord() {
            return XCoord;
        }
        public void setXCoord(Double XCoord) {
            this.XCoord = XCoord;
        }
        public Double getYCoord() {
            return YCoord;
        }
        public void setYCoord(Double YCoord) {
            this.YCoord = YCoord;
        }

        public void PrintPath(int parent[],int goal,HashMap<Integer,String> map){
            if(parent[goal]==-1)
                return;
            PrintPath(parent,parent[goal],map);
            path=path+map.get(goal)+" ";
        }

        public void Dijkstra(LinkedList<FromV> graphL, String fromv, String tov, HashMap<String,Integer> vertexno, HashMap<Integer,String> map, LinkedList<Vertex> vertexLinkedList){

            map.clear();
            vertexno.clear();
            for(int i=0;i<vertexLinkedList.size();i++){
                vertexno.put(vertexLinkedList.get(i).getName(),i);
                map.put(i,vertexLinkedList.get(i).getName());
            }
            int SourceV = vertexno.get(fromv);
            int GoalV = vertexno.get(tov);
            System.out.println("Source is " + SourceV + " and GoalV is " + GoalV);

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
                    if (IsVisited[j]==false && graph[minind][j] != 0 && DistanceArray[minind] != Integer.MAX_VALUE) {
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
        }
    }

    public class Main extends Application {
        LinkedList<FromV> FromVertexLL= new LinkedList<>();
        LinkedList<Vertex> vertexLinkedList= new LinkedList<>();
        HashMap<String,Integer> vertexno= new HashMap<>();
        HashMap<Integer,String> map= new HashMap<>();
        int NoOfVertex=0;
        TextInputDialog td = new TextInputDialog(null);

        void AddVertex(String Vname, Double Vx, Double Vy){
            Vertex node= new Vertex(Vname,Vx,Vy);
            vertexLinkedList.add(node);
            vertexno.put(Vname,NoOfVertex);
            map.put(NoOfVertex,Vname);
            NoOfVertex++;
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

        void SearchVertex(String Vname){
            int key= Collections.binarySearch(vertexLinkedList, new Vertex(Vname, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            if(key<0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex not found.\nClick Ok to continue.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex found.\n X-Cord is "+vertexLinkedList.get(key).getXCoord()+" " +
                        "and Y-Cord is "+vertexLinkedList.get(key).getYCoord());
                alert.showAndWait();
            }
        }

        void DeleteVertex(String Vname){
            int key= Collections.binarySearch(vertexLinkedList, new Vertex(Vname, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            if(key<0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Vertex not found.\nClick Ok to continue.");
                alert.showAndWait();
            }
            else {
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
                alert.setContentText("Vertex "+vertexLinkedList.get(key).getName()+" Deleted.\nClick Ok to continue");
                vertexLinkedList.remove(key);
                alert.showAndWait();
            }
        }

        void AddEdge(String FromV, String ToV, int weight){
            //searching elemets in the list.
            int fkey= Collections.binarySearch(vertexLinkedList, new Vertex(FromV, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("fkey is "+vertexLinkedList.get(fkey).getName());

            int tkey= Collections.binarySearch(vertexLinkedList, new Vertex(ToV, 0.0, 0.0), new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            System.out.println("tkey is "+vertexLinkedList.get(tkey).getName());

            if(fkey>=0&&tkey>=0) {
                int index = -1;
                for (int l = 0; l < FromVertexLL.size(); l++) {
                    if (FromVertexLL.get(l).getName().equals(FromV)) {
                        index = l;
                        break;
                    }
                }
                System.out.println("Index is "+index);
                if (index != -1) {
                    vertexLinkedList.get(tkey).setWeight(weight);
                    Vertex vertex= new Vertex(vertexLinkedList.get(tkey));
                    System.out.println("Weight put in vertexLL is "+vertexLinkedList.get(tkey).getWeight());
                    FromVertexLL.get(index).getToVertexLL().add(vertex);
                    System.out.println("Weight put in ActualLL is "+FromVertexLL.get(index).getToVertexLL().get(FromVertexLL.get(index).getToVertexLL().size()-1).getWeight());
                } else {
                    FromV fromV = new FromV(vertexLinkedList.get(fkey));
                    vertexLinkedList.get(tkey).setWeight(weight);
                    Vertex vertex= new Vertex(vertexLinkedList.get(tkey));
                    System.out.println("Weight put in vertexLL is "+vertexLinkedList.get(tkey).getWeight());
                    fromV.getToVertexLL().add(vertex);
                    FromVertexLL.add(fromV);
                    System.out.println("Weight put in ActualLL is "+FromVertexLL.get(FromVertexLL.size()-1).getToVertexLL().get(FromVertexLL.get(FromVertexLL.size()-1).getToVertexLL().size()-1).getWeight());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Edge added.\nClick Ok to continue.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Edge not found.\nClick Ok to continue.");
                alert.showAndWait();
            }
        }

        void SearchEdge(String FromVS, String ToVS){
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
            if(tkey>=0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Edge found with weight "+FromVertexLL.get(fkey).getToVertexLL().get(tkey).getWeight()+ "\nClick Ok to continue.");
                alert.showAndWait();
            }
        }

        void DeleteEdge(String FromVS, String ToVS){
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
            if(tkey>=0){
                System.out.println("tkey>=0");
                FromVertexLL.get(fkey).getToVertexLL().remove(tkey);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Edge Deleted"+ "\nClick Ok to continue.");
                alert.showAndWait();
            }
        }

        void ModifyEdge(String FromVS, String ToVS, int weight){
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
            if(tkey>=0){
                System.out.println("tkey>=0");
                FromVertexLL.get(fkey).getToVertexLL().get(tkey).setWeight(weight);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Edge Modified with new weight "+FromVertexLL.get(fkey).getToVertexLL().get(tkey).getWeight()+ "\nClick Ok to continue.");
                alert.showAndWait();
            }
        }

        //hardcoding for ease
    //    {
    //        vertexLinkedList.add(new Vertex("q",1.0,1.0));
    //        vertexno.put("q",NoOfVertex);
    //        map.put(NoOfVertex,"q");
    //        NoOfVertex++;
    //        vertexLinkedList.add(new Vertex("w",2.0,2.0));
    //        vertexno.put("w",NoOfVertex);
    //        map.put(NoOfVertex,"w");
    //        NoOfVertex++;
    //        vertexLinkedList.add(new Vertex("e",3.0,3.0));
    //        vertexno.put("e",NoOfVertex);
    //        map.put(NoOfVertex,"e");
    //        NoOfVertex++;
    //        FromVertexLL.add(new FromV("q",1.0,1.0));
    //        FromVertexLL.get(0).getToVertexLL().add(new Vertex("w",2.0,2.0,3));
    //        FromVertexLL.add(new FromV("w",2.0,2.0));
    //        FromVertexLL.get(1).getToVertexLL().add(new Vertex("e",3.0,3.0,99));
    //        System.out.println("Executed Ta daaa!");
    //    }
        @Override
        public void start(Stage primaryStage) throws Exception{
            VBox root = new VBox();
            root.setAlignment(Pos.TOP_CENTER);

            Label LAddVertex=new Label("\n Add Vertex : ");
            root.getChildren().add(LAddVertex);
            HBox AddVertex= new HBox();
            AddVertex.setAlignment(Pos.CENTER);
            TextField TFVertexName= new TextField();
            TFVertexName.setPromptText("Name");
            AddVertex.getChildren().add(TFVertexName);
            TextField TFVertexX= new TextField();
            TFVertexX.setPromptText("X coordinate");
            AddVertex.getChildren().add(TFVertexX);
            TextField TFVertexY= new TextField();
            TFVertexY.setPromptText("Y coordinate");
            AddVertex.getChildren().add(TFVertexY);
            Button BTNAddVertex= new Button("Add Vertex");
            BTNAddVertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String Vname=TFVertexName.getText();
                    double Vx=Double.parseDouble(TFVertexX.getText());
                    double Vy=Double.parseDouble(TFVertexY.getText());
                    AddVertex(Vname,Vx,Vy);
                    TFVertexName.clear();
                    TFVertexX.clear();
                    TFVertexY.clear();
                }
            });
            root.getChildren().add(AddVertex);
            root.getChildren().add(BTNAddVertex);

            Label LSearchVertex=new Label("\n Search Vertex : ");
            root.getChildren().add(LSearchVertex);
            HBox SearchVertex= new HBox();
            SearchVertex.setAlignment(Pos.CENTER);
            TextField TFVertexNameS= new TextField();
            TFVertexNameS.setPromptText("Name");
            SearchVertex.getChildren().add(TFVertexNameS);
            Button BTNSearchVertex= new Button("Search Vertex");
            BTNSearchVertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String Vname=TFVertexNameS.getText();
                    SearchVertex(Vname);
                    TFVertexNameS.clear();
                }
            });
            SearchVertex.getChildren().add(BTNSearchVertex);
            root.getChildren().add(SearchVertex);

            Label LDeleteVertex=new Label("\n Delete Vertex : ");
            root.getChildren().add(LDeleteVertex);
            HBox DeleteVertex= new HBox();
            DeleteVertex.setAlignment(Pos.CENTER);
            TextField TFVertexNameD= new TextField();
            TFVertexNameD.setPromptText("Name");
            DeleteVertex.getChildren().add(TFVertexNameD);
            Button BTNDeleteVertex= new Button("Delete Vertex");
            BTNDeleteVertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String Vname=TFVertexNameD.getText();
                    DeleteVertex(Vname);
                    TFVertexNameD.clear();
                }
            });
            DeleteVertex.getChildren().add(BTNDeleteVertex);
            root.getChildren().add(DeleteVertex);

            Label LModifyVertex=new Label("\n Modify Vertex : ");
            root.getChildren().add(LModifyVertex);
            HBox ModifyVertex= new HBox();
            ModifyVertex.setAlignment(Pos.CENTER);
            TextField TFVertexNameM= new TextField();
            TFVertexNameM.setPromptText("Name");
            ModifyVertex.getChildren().add(TFVertexNameM);
            TextField TFVertexNewName= new TextField();
            TFVertexNewName.setPromptText("New Name");
            ModifyVertex.getChildren().add(TFVertexNewName);
            TextField TFVertexNewX= new TextField();
            TFVertexNewX.setPromptText("New X coordinate");
            ModifyVertex.getChildren().add(TFVertexNewX);
            TextField TFVertexNewY= new TextField();
            TFVertexNewY.setPromptText("New Y coordinate");
            ModifyVertex.getChildren().add(TFVertexNewY);
            Button BTNModifyVertex= new Button("Modify Vertex");
            BTNModifyVertex.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String Vname=TFVertexNameM.getText();
                    Double Vx=Double.parseDouble(TFVertexNewX.getText());
                    Double Vy=Double.parseDouble(TFVertexNewY.getText());
                    int key= Collections.binarySearch(vertexLinkedList, new Vertex(Vname, 0.0, 0.0), new Comparator<Vertex>() {
                        @Override
                        public int compare(Vertex o1, Vertex o2) {
                            return o1.getName().compareTo(o2.getName());
                        }
                    });
                    if(key<0){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Vertex not found.\nClick Ok to continue.");
                        alert.showAndWait();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        String old=vertexLinkedList.get(key).getName();
                        vertexLinkedList.get(key).setName(TFVertexNewName.getText());
                        vertexLinkedList.get(key).setXCoord(Vx);
                        vertexLinkedList.get(key).setYCoord(Vy);
                        for(int i=0;i<FromVertexLL.size();i++){
                            if(FromVertexLL.get(i).getName().equals(Vname)) {
                                FromVertexLL.get(i).setName(TFVertexNewName.getText());
                                FromVertexLL.get(i).setXCoord(Vx);
                                FromVertexLL.get(i).setYCoord(Vy);
                            }
                        }
                        for(int i=0;i<FromVertexLL.size();i++){
                            for(int j=0;j<FromVertexLL.get(i).getToVertexLL().size();j++){
                                if(FromVertexLL.get(i).getToVertexLL().get(j).getName().equals(Vname)) {
                                    FromVertexLL.get(i).getToVertexLL().get(j).setName(TFVertexNewName.getText());
                                    FromVertexLL.get(i).getToVertexLL().get(j).setXCoord(Vx);
                                    FromVertexLL.get(i).getToVertexLL().get(j).setYCoord(Vy);
                                }
                            }
                        }
                        int no=vertexno.get(Vname);
                        vertexno.remove(Vname);
                        vertexno.put(TFVertexNewName.getText(),no);
                        map.remove(no);
                        map.put(no,TFVertexNewName.getText());
                        alert.setContentText("Vertex "+old+" Changed to:\n"+
                                vertexLinkedList.get(key).getName()+" "+vertexLinkedList.get(key).getXCoord()+" "+vertexLinkedList.get(key).getYCoord()+"\n"+
                                "Click Ok to continue");
                        alert.showAndWait();
                    }
                    TFVertexNameM.clear();
                    TFVertexNewName.clear();
                    TFVertexNewX.clear();
                    TFVertexNewY.clear();
                }
            });
            root.getChildren().add(ModifyVertex);
            root.getChildren().add(BTNModifyVertex);

            Label LAddEdge=new Label("\n Add Edge : ");
            root.getChildren().add(LAddEdge);
            HBox AddEdge= new HBox();
            AddEdge.setAlignment(Pos.CENTER);
            TextField TFfromv= new TextField();
            TFfromv.setPromptText("From Vertex Name");
            AddEdge.getChildren().add(TFfromv);
            TextField TFtov= new TextField();
            TFtov.setPromptText("To Vertex Name");
            AddEdge.getChildren().add(TFtov);
            TextField TFWeight= new TextField();
            TFWeight.setPromptText("Weight of Edge");
            AddEdge.getChildren().add(TFWeight);
            Button BTNAddEdge= new Button("Add Edge");
            BTNAddEdge.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String FromV=TFfromv.getText();
                    String ToV=TFtov.getText();
                    int weight= Integer.parseInt(TFWeight.getText());
                    AddEdge(FromV,ToV,weight);
                }
            });
            root.getChildren().add(AddEdge);
            root.getChildren().add(BTNAddEdge);

            Label LSearchEdge=new Label("\n Search Edge : ");
            root.getChildren().add(LSearchEdge);
            HBox SearchEdge= new HBox();
            SearchEdge.setAlignment(Pos.CENTER);
            TextField TFfromvS= new TextField();
            TFfromvS.setPromptText("From Vertex Name");
            SearchEdge.getChildren().add(TFfromvS);
            TextField TFtovS= new TextField();
            TFtovS.setPromptText("To Vertex Name");
            SearchEdge.getChildren().add(TFtovS);
            Button BTNSearchEdge= new Button("Search Edge");
            BTNSearchEdge.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String FromVS=TFfromvS.getText();
                    String ToVS=TFtovS.getText();
                    SearchEdge(FromVS,ToVS);
                }
            });
            SearchEdge.getChildren().add(BTNSearchEdge);
            root.getChildren().add(SearchEdge);

            Label LDeleteEdge=new Label("\n Delete Edge : ");
            root.getChildren().add(LDeleteEdge);
            HBox DeleteEdge= new HBox();
            DeleteEdge.setAlignment(Pos.CENTER);
            TextField TFfromvD= new TextField();
            TFfromvD.setPromptText("From Vertex Name");
            DeleteEdge.getChildren().add(TFfromvD);
            TextField TFtovD= new TextField();
            TFtovD.setPromptText("To Vertex Name");
            DeleteEdge.getChildren().add(TFtovD);
            Button BTNDeleteEdge= new Button("Delete Edge");
            BTNDeleteEdge.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("In delete edge");
                    String FromVS=TFfromvD.getText();
                    String ToVS=TFtovD.getText();
                    DeleteEdge(FromVS,ToVS);
                    TFfromvD.clear();
                    TFtovD.clear();
                }
            });
            DeleteEdge.getChildren().add(BTNDeleteEdge);
            root.getChildren().add(DeleteEdge);

            Label LModifyEdge=new Label("\n Modify Edge : ");
            root.getChildren().add(LModifyEdge);
            HBox ModifyEdge= new HBox();
            ModifyEdge.setAlignment(Pos.CENTER);
            TextField TFfromvM= new TextField();
            TFfromvM.setPromptText("From Vertex Name");
            ModifyEdge.getChildren().add(TFfromvM);
            TextField TFtovM= new TextField();
            TFtovM.setPromptText("To Vertex Name");
            ModifyEdge.getChildren().add(TFtovM);
            TextField TFWeightM= new TextField();
            TFWeightM.setPromptText("New weight of Edge");
            ModifyEdge.getChildren().add(TFWeightM);
            Button BTNModifyEdge= new Button("Modify Edge");
            BTNModifyEdge.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String FromVS=TFfromvM.getText();
                    String ToVS=TFtovM.getText();
                    int weight = Integer.parseInt(TFWeightM.getText());
                    ModifyEdge(FromVS, ToVS, weight);
                }
            });
            root.getChildren().add(ModifyEdge);
            root.getChildren().add(BTNModifyEdge);

            Label LPrintPath=new Label("\n Print Path : ");
            root.getChildren().add(LPrintPath);
            HBox PrintPath= new HBox();
            PrintPath.setAlignment(Pos.CENTER);
            TextField TFfromvPP= new TextField();
            TFfromvPP.setPromptText("From Vertex Name");
            PrintPath.getChildren().add(TFfromvPP);
            TextField TFtovPP= new TextField();
            TFtovPP.setPromptText("To Vertex Name");
            PrintPath.getChildren().add(TFtovPP);
            Button BTNPrintPath= new Button("Print Path");
            final Label LPrintPath1 = new Label();
            BTNPrintPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String FromVPP=TFfromvPP.getText();
                    String ToVPP=TFtovPP.getText();
                    FromV bakra= new FromV();
                    bakra.Dijkstra(FromVertexLL,FromVPP,ToVPP,vertexno,map,vertexLinkedList);
                    if(!bakra.path.equals(FromVPP+" ")) {
                        LPrintPath1.setText("Path is : "+bakra.path+"\n");
                    }
                    else {
                        LPrintPath1.setText("No path found.\n");
                    }
                }
            });
            PrintPath.getChildren().add(BTNPrintPath);
            root.getChildren().add(PrintPath);
            root.getChildren().add(LPrintPath1);

            Label IO=new Label("\n Input/Output from textfile : ");
            root.getChildren().add(IO);
            HBox Data= new HBox();
            Data.setAlignment(Pos.CENTER);
            Button BTNLoadData= new Button("Load Data");
            BTNLoadData.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    TextInputDialog td = new TextInputDialog(null);
                    td.setHeaderText("Enter complete file name.");
                    td.setContentText(null);
                    td.showAndWait();
                    String path="C:\\Users\\mishr\\IdeaProjects\\LearnGUI\\input.txt";
                    path=td.getEditor().getText();
                    File file = new File(path);
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        int NoOfVertices=Integer.parseInt(br.readLine());
                        for(int i=0;i<NoOfVertices;i++){
                            String input= br.readLine();
                            String[] words=input.split(" ");
                            AddVertex(words[0],Double.parseDouble(words[1]),Double.parseDouble(words[2]));
                        }
                        int NoOfEdges=Integer.parseInt(br.readLine());
                        for (int i=0;i<NoOfEdges;i++){
                            String input= br.readLine();
                            String[] words=input.split(" ");
                            System.out.println("Weight read is "+Integer.parseInt(words[2]));
                            AddEdge(words[0],words[1],Integer.parseInt(words[2]));
                        }
    //                    System.out.println("sdsd"+FromVertexLL.get(3).getToVertexLL().get(0).getWeight());

                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("File not found.\nClick Ok to continue.");
                        alert.showAndWait();
                    }
                    for(int i=0;i<FromVertexLL.size();i++){
                        System.out.print(FromVertexLL.get(i).getName()+" ");
                        for (int j=0;j<FromVertexLL.get(i).getToVertexLL().size();j++){
                            System.out.print(FromVertexLL.get(i).getToVertexLL().get(j).getName()
                                    +FromVertexLL.get(i).getToVertexLL().get(j).getWeight()+" ");
                        }
                        System.out.println();
                    }
                }
            });
            Data.getChildren().add(BTNLoadData);
            Button BTNSaveData= new Button("Save Data");
            BTNSaveData.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    BufferedWriter writer = null;
                    try {
                        TextInputDialog td = new TextInputDialog(null);
                        td.setHeaderText("Enter complete file name.");
                        td.setContentText(null);
                        td.showAndWait();
                        String path="C:\\Users\\mishr\\IdeaProjects\\LearnGUI\\output.txt";
                        path=td.getEditor().getText();
                        writer = new BufferedWriter(new FileWriter(path));
                        writer.write(vertexLinkedList.size()+"\n");
                        for(int i=0;i<vertexLinkedList.size();i++){
                            writer.write(vertexLinkedList.get(i).getName()+" "+
                                    vertexLinkedList.get(i).getXCoord().intValue()+" "+
                                    vertexLinkedList.get(i).getYCoord().intValue()+"\n");
                        }

                        FromVertexLL.sort(new Comparator<FromV>() {
                            @Override
                            public int compare(FromV o1, FromV o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        int size=0;
                        for(int i=0;i<FromVertexLL.size();i++) {
                            size+=FromVertexLL.get(i).getToVertexLL().size();
                        }
                        writer.write(size+"\n");
                        for(int i=0;i<FromVertexLL.size();i++){
                            FromVertexLL.get(i).getToVertexLL().sort(new Comparator<Vertex>() {
                                @Override
                                public int compare(Vertex o1, Vertex o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                            for(int j=0;j<FromVertexLL.get(i).getToVertexLL().size();j++){
                                writer.write(FromVertexLL.get(i).getName()+" "
                                        +FromVertexLL.get(i).getToVertexLL().get(j).getName()+" "
                                        +FromVertexLL.get(i).getToVertexLL().get(j).getWeight()+"\n");
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("File not found.\nClick Ok to continue.");
                        alert.showAndWait();
                    }
                }
            });
            Data.getChildren().add(BTNSaveData);
            root.getChildren().add(Data);

            Scene scene= new Scene(root);
            primaryStage.setTitle("Assignment");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            System.out.println("Before launch");
            launch(args);
            System.out.println("After Launch");
        }
    }
