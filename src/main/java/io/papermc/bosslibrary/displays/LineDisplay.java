package io.papermc.bosslibrary.displays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class LineDisplay {
    private final DisplayBuilder display;
    private Vector direction;
    private Location start;
    private Location end;
    private final float yScale;
    private final float xScale;

    public LineDisplay(@NotNull Location spawnLocation, float yScale, float xScale) {
        this.display = new DisplayBuilder(spawnLocation);
        this.start = spawnLocation;
        this.end = spawnLocation;
        this.yScale = yScale;
        this.xScale = xScale;
    }

    public void update() {
        this.direction = this.end.toVector().subtract(this.start.toVector());
        this.start.setDirection(this.direction);

        float length = (float) this.direction.length();
        this.display.setScale(xScale, yScale, length);
        this.display.setTranslation(0, 0, length/2);
        this.display.update();
        this.display.teleport(this.start);
    }

    @NotNull
    public Vector getDirection() {
        return this.direction;
    }

    public void setStart(@NotNull Location start) {
        this.start = start;
    }

    public void setEnd(@NotNull Location end) {
        this.end = end;
    }

    public void setBlock(@NotNull Material block) {
        this.display.setBlock(block);
    }

    public void remove() {
        this.display.remove();
    }

}
