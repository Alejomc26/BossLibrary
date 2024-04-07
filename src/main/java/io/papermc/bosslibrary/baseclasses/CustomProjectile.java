package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.builders.BoneBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class CustomProjectile extends BaseEntity {

    private final Location spawnLocation;
    private final BoneBuilder display;
    private final double contactDamage;
    private double attackRadius = 0.4;
    public CustomProjectile(Location location, double contactDamage) {
        super(location);

        this.display = new BoneBuilder(location);
        this.spawnLocation = location.clone();
        this.contactDamage = contactDamage;
    }

    public BoneBuilder getBoneBuilder() {
        return display;
    }

    @Override
    public void teleport(Location location) {
        this.display.teleport(location);
        super.teleport(location);
    }

    @Override
    public void remove() {
        this.display.remove();
        super.remove();
    }

    @Override
    public void update() {
        Location currentLocation = this.getLocation();
        Block block = currentLocation.getBlock();

        if (block.getType() != Material.AIR) {
            this.touchBlock();
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

        this.tick();
    }

    public void setAttackRadius(double radius) {
        this.attackRadius = radius;
    }

    public abstract void tick();

    public abstract void touchBlock();

}
