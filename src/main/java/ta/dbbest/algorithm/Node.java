package ta.dbbest.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private int name;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public Node setName(int name) {
        this.name = name;
        return this;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public Node setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
        return this;
    }

    public Integer getDistance() {
        return distance;
    }

    public Node setDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public Node setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
        return this;
    }
}
