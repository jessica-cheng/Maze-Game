import java.awt.Color;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// Assignment 10
// Yusuf Hasanat
// yusufh
// Cheng Jessica
// jcheng

// represents a Edge class
class Edge implements Comparable<Edge> {
  Vertex from;
  Vertex to;
  int weight;

  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  // determines if this vertex is equal to the given vertex
  public boolean equals(Object o) {
    if (!(o instanceof Edge)) {
      return false;
    }
    Edge other = (Edge) o;
    return (this.from.equals(other.from) || this.from.equals(other.to))
        && (this.to.equals(other.to) || this.to.equals(other.from));
  }

  // determined if this weight is smaller than the given edge
  public int compareTo(Edge e) {
    if (this.weight < e.weight) {
      return -1;
    }
    if (this.weight > e.weight) {
      return 1;
    }
    else {
      return 0;
    }
  }

  // draws the edge
  WorldImage drawEdge() {
    return new RectangleImage(9, 9, OutlineMode.SOLID, Color.gray);
  }
}