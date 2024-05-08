package io.github.alejomc26.builders;

import org.bukkit.Location;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;

public class InteractionBuilder {

    private final boolean responsive;
    private Location location;
    private float height = 1;
    private float width = 1;

    public InteractionBuilder(boolean responsive) {
        this.responsive = responsive;
    }

    /**
     * Gets the spawn location of the interaction entity
     * @return spawn location
     */
    public Location location() {
        return location;
    }

    /**
     * Sets the spawn location of the interaction entity
     * @param location spawn location
     * @return a reference to this object
     */
    public InteractionBuilder location(@NotNull Location location) {
        this.location = location.clone();
        return this;
    }

    /**
     * Gets the width of the interaction entity
     * @return interaction entity width
     */
    public float width() {
        return this.width;
    }

    /**
     * Sets the width of the interaction entity
     * @param width interaction entity width
     * @return a reference to this object
     */
    public InteractionBuilder width(float width) {
        this.width = width;
        return this;
    }

    /**
     * Gets the height of the interaction entity
     * @return interaction entity height
     */
    public float height() {
        return this.height;
    }

    /**
     * Sets the height of the interaction entity
     * @param height interaction entity height
     * @return a reference to this object
     */
    public InteractionBuilder height(float height) {
        this.height = height;
        return this;
    }

    /**
     * Builds an {@link Interaction} based on the current configuration
     * @return an interaction entity with the current configuration of the builder
     */
    public Interaction build() {
        if (location == null) {
            throw new IllegalStateException("Spawn location not defined");
        }
        return location.getWorld().spawn(location, Interaction.class, (interaction) -> {
            interaction.setResponsive(responsive);
            interaction.setInteractionWidth(width);
            interaction.setInteractionHeight(height);
        });
    }

}
