package io.papermc.bosslibrary.builders;

import org.joml.Matrix4f;

public class Matrix4fBuilder {

    private final Matrix4f matrix4f = new Matrix4f();
    private float xTranslation;
    private float yTranslation;
    private float zTranslation;
    private float xScale;
    private float yScale;
    private float zScale;
    public Matrix4f getMatrix() {
        this.matrix4f.set(new float[] {
                xScale, 0, 0, 0,
                0, yScale, 0, 0,
                0, 0, zScale, 0,
                xTranslation, yTranslation, zTranslation, 1
        });
        return matrix4f;
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
}
