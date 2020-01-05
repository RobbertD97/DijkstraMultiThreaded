import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ParallelDijkstra implements Runnable {

    static String name;
    Thread t;

    ParallelDijkstra(String thread) {
        name = "Threadtest";
        t = new Thread(this, name);
        System.out.println("New thread: " + t);
        t.start();
    }

    Integer maxThreadCount = 3;

    @Override
    public void run() {


        class Edge {
            int source;
            int destination;
            int weight;

            public Edge(int source, int destination, int weight) {
                this.source = source;
                this.destination = destination;
                this.weight = weight;
            }
        }

         class Graph {
            int vertices;
            LinkedList<Edge>[] adjacencylist;

            Graph(int vertices) {
                this.vertices = vertices;
                adjacencylist = new LinkedList[vertices];
                //initialize adjacency lists for all the vertices
                for (int i = 0; i < vertices; i++) {
                    adjacencylist[i] = new LinkedList<>();
                }
            }

            public void addEdge(int source, int destination, int weight) {
                Edge edge = new Edge(source, destination, weight);
                adjacencylist[source].addFirst(edge);

                edge = new Edge(destination, source, weight);
                adjacencylist[destination].addFirst(edge); //for undirected graph
            }

            public void dijkstra_GetMinDistances(int sourceVertex) {
                int workingCounter = 0;
                int workingLimit = 10;

                boolean[] SPT = new boolean[vertices];
                //distance used to store the distance of vertex from a source
                int[] distance = new int[vertices];

                //Initialize all the distance to infinity

                //public void run() {
                new ParallelDijkstra("One");
                new ParallelDijkstra("Two");
                new ParallelDijkstra("Three");try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Main thread Interrupted");
                }
                System.out.println("Main thread exiting.");



                //Initialize priority queue
                //override the comparator to do the sorting based keys
                PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                        //sort using distance values
                        int key1 = p1.getKey();
                        int key2 = p2.getKey();
                        return key1 - key2;
                    }
                });

                //create the pair for for the first index, 0 distance 0 index
                distance[0] = 0;
                Pair<Integer, Integer> p0 = new Pair<>(distance[0], 0);
                //add it to pq
                pq.offer(p0);

                //while priority queue is not empty
                while (!pq.isEmpty()) {
                    //extract the min
                    Pair<Integer, Integer> extractedPair = pq.poll();

                    //extracted vertex
                    int extractedVertex = extractedPair.getValue();
                    if (SPT[extractedVertex] == false) {
                        SPT[extractedVertex] = true;

                        //iterate through all the adjacent vertices and update the keys
                        LinkedList<Edge> list = adjacencylist[extractedVertex];
                        for (int i = 0; i < list.size(); i++) {
                            Edge edge = list.get(i);
                            int destination = edge.destination;
                            //only if edge destination is not present in mst
                            if (SPT[destination] == false) {
                                ///check if distance needs an update or not
                                //means check total weight from source to vertex_V is less than
                                //the current distance value, if yes then update the distance

                                int newKey = distance[extractedVertex] + edge.weight;
                                int currentKey = distance[destination];

                                Thread threadOne = new Thread();
                                Thread threadTwo = new Thread();
                                Thread threadThree = new Thread();

                                //paralleliseer dit
                                threadOne.start();
                                if (currentKey > newKey) {
                                    Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                    pq.offer(p);
                                    distance[destination] = newKey;

                                }
                            }
                        }
                    }
                }
                //print Shortest Path Tree
                // printDijkstra(distance, sourceVertex);
            }

            public void printDijkstra(int[] distance, int sourceVertex) {
                System.out.println("Dijkstra Algorithm: (Adjacency List + Priority Queue)");
                for (int i = 0; i < vertices; i++) {
                    System.out.println("Source Vertex: " + sourceVertex + " to vertex " + +i +
                            " distance: " + distance[i]);
                }
            }
        }
    }
}