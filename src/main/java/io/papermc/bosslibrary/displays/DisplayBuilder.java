package io.papermc.bosslibrary.displays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DisplayBuilder {
    private final Vector3f translation = new Vector3f(-0.5f, -0.5f, -0.5f);
    private final Matrix4f matrix = new Matrix4f();
    private final BlockDisplay display;
    private float xTranslation;
    private float yTranslation;
    private float zTranslation;
    private float xScale;
    private float yScale;
    private float zScale;
    public DisplayBuilder(Location location) {
        this.display = location.getWorld().spawn(location, BlockDisplay.class);
        this.display.setTeleportDuration(2);
    }

    public void update() {
        this.matrix.set(new float[] {
                xScale, 0, 0, 0,
                0, yScale, 0, 0,
                0, 0, zScale, 0,
                xTranslation, yTranslation, zTranslation, 1
        });
        this.matrix.translate(translation);
        this.display.setTransformationMatrix(this.matrix);
    }

    public void setTranslation(float xTranslation, float yTranslation, float zTranslation) {
        this.xTranslation = xTranslation;
        this.yTranslation = yTranslation;
        this.zTranslation = zTranslation;
    }

    public void setScale(float xScale, float yScale, float zScale) {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
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
