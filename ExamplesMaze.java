
// Assignment 10
// Yusuf Hasanat
// yusufh
// Cheng Jessica
// jcheng

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import tester.Tester;

//tests the Maze
class ExampleMazeGame {

  void testGame(Tester t) {
    MazeWorld g = new MazeWorld();
    g.bigBang(MazeWorld.WIDTH * 10, MazeWorld.HEIGHT * 10, 0.05);
  }
}

// to represent Examples of Mazes
class ExamplesMaze {
  MazeWorld world;

  ArrayList<ArrayList<Vertex>> grid;
  ArrayList<ArrayList<Vertex>> grid1;
  ArrayList<ArrayList<Vertex>> grid3;

  ArrayList<Vertex> reconstructedpath;

  ArrayList<Vertex> list1;
  ArrayList<Vertex> list2;
  ArrayList<Vertex> list3;
  ArrayList<Vertex> list4;

  ArrayList<Vertex> gridlist1;
  ArrayList<Vertex> gridlist2;
  ArrayList<Vertex> gridlist3;

  ArrayList<Edge> edges;
  ArrayList<Edge> edges2;
  ArrayList<Edge> sortededges;
  ArrayList<Edge> edgesintree;

  Vertex a;
  Vertex b;
  Vertex c;
  Vertex d;
  Vertex e;
  Vertex f;

  Vertex g;
  Vertex h;
  Vertex i;
  Vertex j;
  Vertex k;
  Vertex l;

  Vertex a1;
  Vertex a2;
  Vertex a3;
  Vertex a4;
  Vertex a5;
  Vertex a6;
  Vertex a7;
  Vertex a8;
  Vertex a9;

  Edge eToC;
  Edge cToD;
  Edge aToB;
  Edge bToE;
  Edge bToC;
  Edge fToD;
  Edge aToE;
  Edge bToF;

  Edge gToH;
  Edge gToJ;
  Edge hToI;
  Edge hToK;
  Edge jToI;
  Edge jToK;
  Edge kToI;
  Edge iToL;

  HashMap<Vertex, Vertex> representatives;
  HashMap<Vertex, Vertex> linkedrepresentatives;
  HashMap<Vertex, Vertex> cameFromEdge;

  Player player;

  // initializes the data
  void initData() {
    this.world = new MazeWorld();
    // vertices of map
    this.a = new Vertex(new Posn(0, 0), new ArrayList<Edge>());
    this.b = new Vertex(new Posn(0, 1), new ArrayList<Edge>());
    this.c = new Vertex(new Posn(0, 2), new ArrayList<Edge>());
    this.d = new Vertex(new Posn(1, 0), new ArrayList<Edge>());
    this.e = new Vertex(new Posn(1, 1), new ArrayList<Edge>());
    this.f = new Vertex(new Posn(1, 2), new ArrayList<Edge>());

    // example of edges in a map
    this.eToC = new Edge(this.e, this.c, 15);
    this.cToD = new Edge(this.c, this.d, 25);
    this.aToB = new Edge(this.a, this.b, 30);
    this.bToE = new Edge(this.b, this.e, 35);
    this.bToC = new Edge(this.b, this.c, 40);
    this.fToD = new Edge(this.f, this.d, 50);
    this.aToE = new Edge(this.a, this.e, 50);
    this.bToF = new Edge(this.b, this.f, 50);

    // examples of vertices
    this.g = new Vertex(new Posn(0, 0), new ArrayList<Edge>());
    this.h = new Vertex(new Posn(0, 1), new ArrayList<Edge>());
    this.i = new Vertex(new Posn(0, 2), new ArrayList<Edge>());
    this.j = new Vertex(new Posn(1, 0), new ArrayList<Edge>());
    this.k = new Vertex(new Posn(1, 1), new ArrayList<Edge>());
    this.i = new Vertex(new Posn(1, 2), new ArrayList<Edge>());

    // examples of vertices
    this.g = new Vertex(new Posn(0, 0), new ArrayList<Edge>());
    this.h = new Vertex(new Posn(0, 1), new ArrayList<Edge>());
    this.i = new Vertex(new Posn(0, 2), new ArrayList<Edge>());
    this.j = new Vertex(new Posn(1, 0), new ArrayList<Edge>());
    this.k = new Vertex(new Posn(1, 1), new ArrayList<Edge>());
    this.i = new Vertex(new Posn(1, 2), new ArrayList<Edge>());

    // examples of vertices
    this.a1 = new Vertex(new Posn(0, 0), new ArrayList<Edge>());
    this.a2 = new Vertex(new Posn(0, 1), new ArrayList<Edge>());
    this.a3 = new Vertex(new Posn(0, 2), new ArrayList<Edge>());
    this.a4 = new Vertex(new Posn(1, 0), new ArrayList<Edge>());
    this.a5 = new Vertex(new Posn(1, 1), new ArrayList<Edge>());
    this.a6 = new Vertex(new Posn(1, 2), new ArrayList<Edge>());
    this.a7 = new Vertex(new Posn(1, 0), new ArrayList<Edge>());
    this.a8 = new Vertex(new Posn(1, 1), new ArrayList<Edge>());
    this.a9 = new Vertex(new Posn(1, 2), new ArrayList<Edge>());

    // examples of edges
    this.gToH = new Edge(this.g, this.h, 1);
    this.gToJ = new Edge(this.g, this.j, 1);
    this.hToI = new Edge(this.h, this.i, 1);
    this.hToK = new Edge(this.h, this.k, 1);
    this.iToL = new Edge(this.i, this.l, 1);
    this.jToK = new Edge(this.j, this.k, 1);
    this.kToI = new Edge(this.k, this.i, 1);

    this.list3 = new ArrayList<Vertex>(Arrays.asList(this.g, this.h, this.i));
    this.list4 = new ArrayList<Vertex>(Arrays.asList(this.j, this.k, this.l));
    this.grid1 = new ArrayList<ArrayList<Vertex>>(Arrays.asList(this.list3, this.list4));

    this.edges2 = new ArrayList<Edge>(
        Arrays.asList(this.gToH, this.gToJ, this.hToI, this.hToK, this.iToL, this.jToK, this.kToI));

    this.a.outEdges.add(this.aToB);
    this.a.outEdges.add(this.aToE);
    this.b.outEdges.add(this.bToC);
    this.b.outEdges.add(this.bToE);
    this.b.outEdges.add(this.bToF);
    this.c.outEdges.add(this.cToD);
    this.f.outEdges.add(this.fToD);
    this.e.outEdges.add(this.eToC);

    this.list1 = new ArrayList<Vertex>(Arrays.asList(this.a, this.b, this.c));
    this.list2 = new ArrayList<Vertex>(Arrays.asList(this.d, this.e, this.f));
    this.grid = new ArrayList<ArrayList<Vertex>>(Arrays.asList(this.list1, this.list2));

    this.edges = new ArrayList<Edge>(Arrays.asList(this.aToB, this.bToC, this.bToF, this.eToC,
        this.aToE, this.bToE, this.cToD, this.fToD));

    this.sortededges = new ArrayList<Edge>(Arrays.asList(this.eToC, this.cToD, this.aToB, this.bToE,
        this.bToC, this.fToD, this.aToE, this.bToF));

    this.representatives = new HashMap<Vertex, Vertex>();
    this.representatives.put(this.a, this.a);
    this.representatives.put(this.b, this.b);
    this.representatives.put(this.c, this.c);
    this.representatives.put(this.d, this.d);
    this.representatives.put(this.e, this.f);
    this.representatives.put(this.f, this.f);

    this.linkedrepresentatives = new HashMap<Vertex, Vertex>();
    this.linkedrepresentatives.put(this.a, this.e);
    this.linkedrepresentatives.put(this.b, this.a);
    this.linkedrepresentatives.put(this.c, this.e);
    this.linkedrepresentatives.put(this.d, this.e);
    this.linkedrepresentatives.put(this.e, this.e);
    this.linkedrepresentatives.put(this.f, this.d);

    this.edgesintree = new ArrayList<Edge>(
        Arrays.asList(this.eToC, this.cToD, this.aToB, this.bToE, this.fToD));

    this.player = new Player(0, 0);

    this.gridlist1 = new ArrayList<Vertex>(Arrays.asList(this.a1, this.a2, this.a3));
    this.gridlist2 = new ArrayList<Vertex>(Arrays.asList(this.a4, this.a5, this.a6));
    this.gridlist3 = new ArrayList<Vertex>(Arrays.asList(this.a7, this.a8, this.a9));
    this.grid3 = new ArrayList<ArrayList<Vertex>>(
        Arrays.asList(this.gridlist1, this.gridlist2, this.gridlist3));

    this.cameFromEdge = new HashMap<Vertex, Vertex>();
    this.cameFromEdge.put(this.a2, this.a1);
    this.cameFromEdge.put(this.a3, this.a2);
    this.cameFromEdge.put(this.a4, this.a3);
    this.cameFromEdge.put(this.a5, this.a4);
    this.cameFromEdge.put(this.a6, this.a4);

    this.reconstructedpath = new ArrayList<Vertex>(
        Arrays.asList(this.a9, this.a4, this.a3, this.a2, this.a1));

  }

  // tests grid
  void testGrid(Tester t) {
    this.initData();
    for (int x = 0; x < this.world.grid().size(); x++) {
      for (int y = 0; y < this.world.grid().get(x).size(); y++) {
        Vertex v = this.world.grid().get(x).get(y);
        ArrayList<Edge> e = new ArrayList<Edge>();
        t.checkExpect(v, new Vertex(new Posn(x, y), e));
      }
    }
  }

  // tests possibleEdges
  void testPossibleEdges(Tester t) {
    this.initData();
    t.checkExpect(this.world.possibleEdges(), this.edges2);
  }

  // tests sortEdges
  void testSortEdges(Tester t) {
    this.initData();
    t.checkExpect(this.world.sortEdges(this.edges), this.sortededges);
  }

  // tests minSpaningTree
  void testMinSpanningTree(Tester t) {
    this.initData();
    t.checkExpect(this.world.edgesInTree.size(), MazeWorld.HEIGHT * MazeWorld.WIDTH - 1);
    // edgesinTree are sorted
    for (int i = 0; i < this.world.edgesInTree.size() - 1; i++) {
      Edge first = this.world.edgesInTree.get(i);
      Edge second = this.world.edgesInTree.get(i + 1);
      t.checkExpect(first.weight < second.weight, true);
    }
  }

  // tests find
  void testFind(Tester t) {
    this.initData();
    t.checkExpect(this.world.find(this.linkedrepresentatives, this.a), this.e);
  }

  // tests union
  void testUnion(Tester t) {
    this.initData();
    this.world.union(this.representatives, this.a, this.b);
    t.checkExpect(this.representatives.get(this.a), this.b);
  }

  // tests assignEdges
  void testAssignEdges(Tester t) {
    this.initData();
    this.world.assignEdges();
    for (Edge e : this.world.edgesInTree) {
      t.checkExpect(e.from.outEdges.contains(e), true);
      t.checkExpect(e.to.outEdges.contains(e), true);
    }
  }

  // tests canMoveTo
  void testCanMoveTo(Tester t) {
    this.initData();
    t.checkExpect(this.world.canMoveTo(0, 0, 0, 0), true);
  }

  // tests onKeyEvent
  void testOnKeyEvent(Tester t) {
    this.initData();
    this.world.onKeyEvent("up");
    t.checkOneOf(this.player.y, -1, 0);
    this.world.onKeyEvent("down");
    t.checkOneOf(this.player.y, 0, 1);
    this.world.onKeyEvent("left");
    t.checkOneOf(this.player.x, -1, 0);
    this.world.onKeyEvent("right");
    t.checkOneOf(this.player.x, 0, 1);
    this.world.onKeyEvent("b");
    t.checkExpect(this.world.seen.size() > 0, true);
    t.checkExpect(this.world.path.size() > 0, true);
    this.world.onKeyEvent("n");
    t.checkExpect(this.world.seen.size() == 0, true);
    t.checkExpect(this.world.path.size() == 0, true);
    t.checkExpect(this.world.count == -1, true);
    this.world.onKeyEvent("d");
    t.checkExpect(this.world.seen.size() > 0, true);
    t.checkExpect(this.world.path.size() > 0, true);
  }

  // tests breadth
  void testFindPath(Tester t) {
    this.initData();
    this.world.findPath("d");
    t.checkExpect(this.world.seen.size() > 0, true);
    t.checkExpect(this.world.seen.size() < MazeWorld.HEIGHT * MazeWorld.WIDTH, true);
    t.checkExpect(this.world.path.get(0),
        this.world.grid.get(this.world.grid.size() - 1).get(this.world.grid.get(0).size() - 1));
    t.checkExpect(this.world.path.get(this.world.path.size() - 1), this.world.grid.get(0).get(0));
    this.world.findPath("b");
    t.checkExpect(this.world.seen.size() > 0, true);
    t.checkExpect(this.world.seen.size() < MazeWorld.HEIGHT * MazeWorld.WIDTH, true);
    t.checkExpect(this.world.path.get(0),
        this.world.grid.get(this.world.grid.size() - 1).get(this.world.grid.get(0).size() - 1));
    t.checkExpect(this.world.path.get(this.world.path.size() - 1), this.world.grid.get(0).get(0));
  }

  // tests reconstruct
  void testReconstruct(Tester t) {
    this.initData();
    this.world.grid = this.grid3;
    this.world.reconstruct(this.cameFromEdge, this.a4);
    t.checkExpect(this.world.path, this.reconstructedpath);
  }

  // tests equals
  boolean testEquals(Tester t) {
    this.initData();
    return t.checkExpect(this.a.equals(this.a), true) && t.checkExpect(this.a.equals(this.b), false)
        && t.checkExpect(this.aToB.equals(this.aToB), true)
        && t.checkExpect(this.aToB.equals(this.bToC), false);
  }

  // tests drawCell
  boolean testDrawCell(Tester t) {
    this.initData();
    return t.checkExpect(this.a.drawCell(),
        new RectangleImage(9, 9, OutlineMode.SOLID, Color.gray));
  }

  // tests drawSeenVertex
  boolean testDrawSeenVertex(Tester t) {
    this.initData();
    this.a.isColoredSeen = true;
    return t.checkExpect(this.a.drawSeenVertex(),
        new RectangleImage(9, 9, OutlineMode.SOLID, new Color(100, 150, 255)))
        && t.checkExpect(this.b.drawSeenVertex(), new EmptyImage());
  }

  // tests drawSeenVertex
  boolean testDrawPathVertex(Tester t) {
    this.initData();
    this.a.isColoredPath = true;
    return t.checkExpect(this.a.drawPathVertex(),
        new RectangleImage(9, 9, OutlineMode.SOLID, Color.blue))
        && t.checkExpect(this.b.drawPathVertex(), new EmptyImage());
  }

  // tests drawPlayerPathVertex
  boolean testDrawPlayerPathVertex(Tester t) {
    this.initData();
    return t.checkExpect(this.a.drawPlayerPathVertex(),
        new RectangleImage(9, 9, OutlineMode.SOLID, Color.CYAN));
  }

  // tests compareTo
  boolean testDrawEdge(Tester t) {
    this.initData();
    return t.checkExpect(this.aToB.drawEdge(),
        new RectangleImage(9, 9, OutlineMode.SOLID, Color.gray));
  }

  // tests hashCode
  void testHashCode(Tester t) {
    this.initData();
    Object o = new Vertex(new Posn(0, 0), new ArrayList<Edge>());
    t.checkExpect(this.a.equals(o), true);
    t.checkExpect(this.a.hashCode() == o.hashCode(), true);
    t.checkExpect(this.b.equals(o), false);
  }
}
