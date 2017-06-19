package model;

import util.Point;

public class Player extends Entity {
    public Player(Point position) {
        // Player spawnt standardmäßig auf Position (0,0)
        position = new Point();
    }

    /**
     * Getter-Methode für die aktuelle Spielerposition
     * @return Position des Spielers als Point
     */
    public Point getPosition() {
        return position;
    }

    public void move(double x, double y) {
        position.move(x, y);
    }
}
