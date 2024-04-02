package io.papermc.bosslibrary.builders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DisplayBuilder extends Matrix4fBuilder {

    private final Vector3f translation = new Vector3f(-0.5f, -0.5f, -0.5f);
    private final BlockDisplay display;
    public DisplayBuilder(Location location) {
        this.display = location.getWorld().spawn(location, BlockDisplay.class);
        this.display.setTeleportDuration(2);
    }

    public void update() {
        Matrix4f matrix = this.getMatrix().translate(translation);
        this.display.setTransformationMatrix(matrix);
    }

    public void setBlock(Material block) {
        this.display.setBlock(block.createBlockData());
    }

    public void teleport(Location location) {
        this.display.teleport(location);
    }

    public void remove() {
        this.display.remove();
    }

    public Location getLocation() {
        return this.display.getLocation();
    }
}
