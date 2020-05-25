
// Assignment 10
// Yusuf Hasanat
// yusufh
// Cheng Jessica
// jcheng

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javalib.impworld.*;
import javalib.worldimages.*;
import java.util.Collections;

// to represents the MazeWorld Class
class MazeWorld extends World {
  ArrayList<ArrayList<Vertex>> grid;
  static final int HEIGHT = 50;
  static final int WIDTH = 50;
  HashMap<Vertex, Vertex> representatives;
  List<Edge> edgesInTree;

  // all edges in graph, sorted by edge weights
  List<Edge> worklist;

  // the player
  Player player;

  // a list of seen vertices
  ArrayList<Vertex> seen;

  // a list of vertices that are part of the path
  ArrayList<Vertex> path;

  // tick count
  int count;

  // player's path
  ArrayList<Vertex> playerPath;

  // the player's score
  int score;

  // determines when to color the playerpath
  boolean colorPlayerPath;

  MazeWorld() {
    this.grid = this.grid();
    this.edgesInTree = new ArrayList<Edge>();
    this.worklist = this.sortEdges(this.possibleEdges());
    this.minSpanningTree(this.worklist, this.grid);
    this.assignEdges();
    this.player = new Player(0, 0);
    this.seen = new ArrayList<Vertex>();
    this.path = new ArrayList<Vertex>();
    this.count = -1;
    this.playerPath = new ArrayList<Vertex>(Arrays.asList(this.grid.get(0).get(0)));
    this.score = 0;
    this.colorPlayerPath = true;

  }

  // makes a grid of vertices
  ArrayList<ArrayList<Vertex>> grid() {
    ArrayList<ArrayList<Vertex>> result1 = new ArrayList<ArrayList<Vertex>>();
    for (int col = 0; col < MazeWorld.WIDTH; col++) {
      ArrayList<Vertex> result2 = new ArrayList<Vertex>();
      for (int row = 0; row < MazeWorld.HEIGHT; row++) {
        ArrayList<Edge> e = new ArrayList<Edge>();
        Vertex v = new Vertex(new Posn(col, row), e);
        result2.add(v);
      }
      result1.add(result2);
    }
    return result1;
  }

  // creates all possible edges leaving each vertex
  ArrayList<Edge> possibleEdges() {
    ArrayList<Edge> result = new ArrayList<Edge>();
    for (int col = 0; col < this.grid.size(); col++) {
      for (int row = 0; row < this.grid.get(col).size(); row++) {

        // now in rightmost column , add edge to right vertex
        if (col < MazeWorld.WIDTH - 1) {
          Vertex v = grid.get(col).get(row);
          Vertex toright = grid.get(col + 1).get(row);
          Edge edge2 = new Edge(v, toright, new Random().nextInt());
          result.add(edge2);
        }

        // if not in bottom row, add edge to bottom vertex
        if (row < MazeWorld.HEIGHT - 1) {
          Vertex v = grid.get(col).get(row);
          Vertex todown = grid.get(col).get(row + 1);
          Edge edge1 = new Edge(v, todown, new Random().nextInt());
          result.add(edge1);
        }
      }
    }
    return result;

  }

  // orders a list of edges by weight
  List<Edge> sortEdges(ArrayList<Edge> edges) {
    Collections.sort(edges);
    return edges;
  }

  // makes each vertex a representative of itself first and then connects the
  // vertices
  List<Edge> minSpanningTree(List<Edge> worklist, ArrayList<ArrayList<Vertex>> grid) {
    HashMap<Vertex, Vertex> result = new HashMap<Vertex, Vertex>();
    for (ArrayList<Vertex> av : grid) {
      for (Vertex v : av) {
        result.put(v, v);
      }
    }
    while (this.edgesInTree.size() < result.size() - 1) {

      Edge current = worklist.remove(0);
      if (!find(result, current.to).equals(find(result, current.from))) {
        this.edgesInTree.add(current);
        union(result, find(result, current.to), (find(result, current.from)));
      }
    }
    worklist.removeAll(this.edgesInTree);
    this.representatives = result;
    return this.edgesInTree;
  }

  // finds the parent vertex at the given key
  Vertex find(HashMap<Vertex, Vertex> rep, Vertex v) {
    if (rep.get(v).equals(v)) {
      return rep.get(v);
    }
    else {
      return find(rep, rep.get(v));
    }

  }

  // changes the vertex to the given vertex
  void union(HashMap<Vertex, Vertex> rep, Vertex v1, Vertex v2) {
    rep.put(v1, v2);
  }

  // assigns the edges in edgesInTree to the vertices in the grid
  void assignEdges() {
    for (Edge e : this.edgesInTree) {
      e.from.outEdges.add(e);
      e.to.outEdges.add(e);
    }
  }

  // draws the Maze on the World Canvas
  public WorldScene makeScene() {
    WorldScene ws = new WorldScene(MazeWorld.WIDTH + 10, MazeWorld.HEIGHT + 10);
    // draws the grid
    for (ArrayList<Vertex> av : this.grid) {
      for (Vertex v : av) {
        ws.placeImageXY(v.drawCell(), v.position.x * 10 + 5, v.position.y * 10 + 5);
      }
    }
    // draws the edges
    for (Edge e : this.edgesInTree) {
      ws.placeImageXY(e.drawEdge(), (e.to.position.x + e.from.position.x) * 5 + 5,
          (e.to.position.y + e.from.position.y) * 5 + 5);
    }
    // draws the end point
    ws.placeImageXY(new RectangleImage(9, 9, OutlineMode.SOLID, Color.red),
        MazeWorld.WIDTH * 10 - 5, MazeWorld.HEIGHT * 10 - 5);

    // draws the seen
    for (Vertex v : this.seen) {
      ws.placeImageXY(v.drawSeenVertex(), v.position.x * 10 + 5, v.position.y * 10 + 5);
    }
    // draws the path
    if (this.count >= this.seen.size()) {
      for (Vertex v : this.path) {

        ws.placeImageXY(v.drawPathVertex(), v.position.x * 10 + 5, v.position.y * 10 + 5);
      }
    }
    // draws the player's path
    if (this.colorPlayerPath) {
      for (Vertex v : this.playerPath) {
        ws.placeImageXY(v.drawPlayerPathVertex(), v.position.x * 10 + 5, v.position.y * 10 + 5);
      }

    }
    // draws the player
    ws.placeImageXY(this.player.drawPlayer(), player.x * 10 + 5, player.y * 10 + 5);
    // displays the player's score if it reaches the end of the maze
    if (this.player.x == this.grid.get(MazeWorld.WIDTH - 1).get(MazeWorld.HEIGHT - 1).position.x
        && this.player.y == this.grid.get(MazeWorld.WIDTH - 1)
            .get(MazeWorld.HEIGHT - 1).position.y) {
      ws.placeImageXY(new OverlayImage(
          new TextImage("path found! Score: " + Integer.toString(this.score - this.path.size()),
              Color.red),
          new RectangleImage(MazeWorld.WIDTH * 10, MazeWorld.HEIGHT, OutlineMode.SOLID,
              Color.WHITE)),
          MazeWorld.WIDTH * 10 / 2, MazeWorld.HEIGHT * 10 / 2);
    }

    return ws;
  }

  // determines if the player can move to this position
  boolean canMoveTo(int toX, int toY, int fromX, int fromY) {
    return this.edgesInTree.contains(new Edge(new Vertex(new Posn(toX, toY), new ArrayList<Edge>()),
        new Vertex(new Posn(fromX, fromY), new ArrayList<Edge>()), 0));
  }

  // moves the player and also switches from breath-first and depth-first search.
  public void onKeyEvent(String ke) {
    if (ke.equals("up")) {
      if (this.canMoveTo(this.player.x, this.player.y - 1, this.player.x, this.player.y)) {
        this.player.y = this.player.y - 1;
        this.playerPath.add(this.grid.get(this.player.x).get(this.player.y));
        this.score = this.score + 1;
      }
    }
    if (ke.equals("down")) {
      if (this.canMoveTo(this.player.x, this.player.y + 1, this.player.x, this.player.y)) {
        this.player.y = this.player.y + 1;
        this.playerPath.add(this.grid.get(this.player.x).get(this.player.y));
        this.score = this.score + 1;
      }
    }
    if (ke.equals("left")) {
      if (this.canMoveTo(this.player.x - 1, this.player.y, this.player.x, this.player.y)) {
        this.player.x = this.player.x - 1;
        this.playerPath.add(this.grid.get(this.player.x).get(this.player.y));
        this.score = this.score + 1;
      }
    }
    if (ke.equals("right")) {
      if (this.canMoveTo(this.player.x + 1, this.player.y, this.player.x, this.player.y)) {
        this.player.x = this.player.x + 1;
        this.playerPath.add(this.grid.get(this.player.x).get(this.player.y));
        this.score = this.score + 1;
      }
    }

    if (ke.equals("b")) {
      this.count = 0;
      this.findPath(ke);
    }
    if (ke.equals("d")) {
      this.count = 0;
      this.findPath(ke);
    }
    // Extra Credit: creates a new world without restarting the program
    if (ke.equals("n")) {
      MazeWorld n = new MazeWorld();
      this.grid = n.grid;
      this.representatives = n.representatives;
      this.edgesInTree = n.edgesInTree;
      this.worklist = n.worklist;
      this.player = n.player;
      this.seen = n.seen;
      this.path = n.path;
      this.count = n.count;
      this.playerPath = n.playerPath;
    }

    // shows the player's path if not shown. Removes the player's path if "t" is
    // pressed again
    if (ke.equals("t")) {
      this.colorPlayerPath = !this.colorPlayerPath;
    }
  }

  // solves the maze through breadth first search or depth first search
  void findPath(String ke) {
    HashMap<Vertex, Vertex> cameFromEdge = new HashMap<Vertex, Vertex>();
    ArrayList<Vertex> worklist = new ArrayList<Vertex>();
    worklist.add(this.grid.get(0).get(0));
    this.seen.clear();

    while (worklist.size() > 0) {
      Vertex next = worklist.remove(0);
      Vertex finalNode = this.grid.get(this.grid.size() - 1).get(this.grid.get(0).size() - 1);
      if (next.equals(finalNode)) {
        this.reconstruct(cameFromEdge, next);
        return;
      }
      for (Edge e : next.outEdges) {
        if (!this.seen.contains(e.to) && next.equals(e.from)) {
          if (ke.equals("b")) {
            worklist.add(e.to);
          }
          if (ke.equals("d")) {
            worklist.add(0, e.to);
          }
          this.seen.add(next);
          cameFromEdge.put(e.to, next);
        }
        else if (!this.seen.contains(e.from) && next.equals(e.to)) {
          if (ke.equals("b")) {
            worklist.add(e.from);
          }
          if (ke.equals("d")) {
            worklist.add(0, e.from);
          }
          this.seen.add(next);
          cameFromEdge.put(e.from, next);
        }
      }
    }
  }

  // Effect: reconstructions the path from the end to the beginning
  void reconstruct(HashMap<Vertex, Vertex> cameFromEdge, Vertex next) {
    this.path.add(this.grid.get(this.grid.size() - 1).get(this.grid.get(0).size() - 1));
    Vertex start = this.grid.get(0).get(0);
    while (start != next) {
      this.path.add(cameFromEdge.get(next));
      next = cameFromEdge.get(next);
    }
  }

  // draws a vertex of the seen and then the path
  public void onTick() {
    if (this.count > -1) {
      this.count += 1;
    }
    if (this.seen.size() > 0) {
      if (this.count < this.seen.size()) {
        Vertex s = this.seen.get(this.count);
        s.isColoredSeen = true;
      }
    }
    if (this.path.size() > 0 && this.count > this.seen.size()) {
      if (this.count - this.seen.size() < this.path.size()) {
        Vertex p = this.path.get(this.count - this.seen.size());
        p.isColoredPath = true;
      }
    }
  }

}