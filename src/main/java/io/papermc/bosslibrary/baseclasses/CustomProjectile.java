package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.BossLibrary;
import io.papermc.bosslibrary.builders.BoneBuilder;
import io.papermc.bosslibrary.interfaces.CustomBehavior;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * Represents a projectile from BossLibrary
 */
public final class CustomProjectile extends CustomEntityImpl {

    private final Location spawnLocation;
    private final BoneBuilder display;
    private double contactDamage = 1;
    private double attackRadius = 0.4;
    private Consumer<CustomProjectile> onTouchFunction;
    public CustomProjectile(JavaPlugin main, Location location, CustomBehavior mainBehavior) {
        super(location, mainBehavior);

        this.display = new BoneBuilder(location);
        this.spawnLocation = location.clone();
    }

    @Override
    public void teleport(Location location) {
        super.teleport(location);
        this.display.teleport(location);
    }

    @Override
    public void remove() {
        super.remove();
        this.display.remove();
    }

    /**
     * Gets the bone builder of the projectile, useful for 3d models
     * @return bone builder
     */
    public BoneBuilder getBoneBuilder() {
        return display;
    }

    /**
     * Gets the attack radius of the projectile
     * @return attack radius
     */
    public double getAttackRadius() {
        return attackRadius;
    }

    /**
     * Sets the attack radius of the projectile, this is basically a hitbox size
     * @param radius new attack radius
     */
    public void setAttackRadius(double radius) {
        this.attackRadius = radius;
    }

    /**
     * Gets the projectile contact damage
     * @return contact damage
     */
    public double getContactDamage() {
        return this.contactDamage;
    }

    /**
     * Sets the projectile contact damage, any entity near enough to the projectile will
     * receive this damage after the damage formula is applied
     * @param contactDamage new contact damage
     */
    public void setContactDamage(double contactDamage) {
        this.contactDamage = contactDamage;
    }

    /**
     * Sets a function that will be called if the projectile touches a block
     * @param function function
     */
    public void setTouchBlockFunction(Consumer<CustomProjectile> function) {
        this.onTouchFunction = function;
    }

    @Override
    protected void tick() {
        Location currentLocation = this.getLocation();
        Block block = currentLocation.getBlock();

        if (block.getType() != Material.AIR) {
            if (this.onTouchFunction != null) this.onTouchFunction.accept(this);
            this.remove();
            return;
        }

        if (currentLocation.distanceSquared(spawnLocation) > 2500) {
            this.remove();
            return;
        }

        for (Player player : currentLocation.getNearbyPlayers(this.attackRadius)) {
            player.damage(this.contactDamage);
        }
    }
}
