
// Assignment 10
// Yusuf Hasanat
// yusufh
// Cheng Jessica
// jcheng

import java.util.ArrayList;

import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import java.awt.Color;

//to represent an vertex class
class Vertex {
  Posn position;
  ArrayList<Edge> outEdges;
  Boolean isColoredSeen;
  Boolean isColoredPath;

  Vertex(Posn position, ArrayList<Edge> outEdges) {
    this.position = position;
    this.outEdges = outEdges;
    this.isColoredSeen = false;
    this.isColoredPath = false;
  }

  // determines if the given vertex is equal to the given vertex
  public boolean equals(Object o) {
    if (!(o instanceof Vertex)) {
      return false;
    }
    Vertex other = (Vertex) o;
    return (this.position.x == other.position.x && this.position.y == other.position.y);
  }

  // overrides hashcode
  public int hashCode() {
    return this.position.hashCode() * 1000;
  }

  // draws a cell from the vertex
  WorldImage drawCell() {
    return new RectangleImage(9, 9, OutlineMode.SOLID, Color.gray);
  }

  // draws a cell of a seen vertex
  WorldImage drawSeenVertex() {
    if (this.isColoredSeen) {
      return new RectangleImage(9, 9, OutlineMode.SOLID, new Color(100, 150, 255));
    }
    else {
      return new EmptyImage();
    }
  }

  // draws a cell of a vertex in the path
  WorldImage drawPathVertex() {
    if (this.isColoredPath) {
      return new RectangleImage(9, 9, OutlineMode.SOLID, Color.blue);
    }
    else {
      return new EmptyImage();
    }

  }

  // draws playerPathVertex
  WorldImage drawPlayerPathVertex() {
    return new RectangleImage(9, 9, OutlineMode.SOLID, Color.CYAN);
  }
}