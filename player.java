import java.awt.Color;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// to represent the player class
class Player {
  int x;
  int y;

  Player(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // draws the player
  WorldImage drawPlayer() {
    return new RectangleImage(9, 9, OutlineMode.SOLID, Color.green);
  }

}