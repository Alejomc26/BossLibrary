package io.papermc.bosslibrary.utils;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;

import java.util.Map;

public class BossUtils {

    public static Player getNearestPlayer(Location location) {
        //Gets the nearest player only if it hasn't been found this tick
        Player nearestPlayer = null;
        double nearestDistanceSquare = Double.MAX_VALUE;

        for (Player player : location.getWorld().getPlayers()) {
            double distanceSquare = player.getLocation().distanceSquared(location);
            if (distanceSquare >= nearestDistanceSquare) {
                continue;
            }
            nearestDistanceSquare = distanceSquare;
            nearestPlayer = player;
        }

        return nearestPlayer;
    }

    public static Location lerpLocation(Location from, Location to, float percent) {
        double x = lerp(from.x(), to.x(), percent);
        double y = lerp(from.y(), to.y(), percent);
        double z = lerp(from.z(), to.z(), percent);

        return new Location(to.getWorld(), x, y, z);
    }

    public static double lerp(double start, double end, float percent) {
        return start + (end - start) * percent;
    }

    public static float getPlayerDamage(Player player) {
        AttributeInstance attackDamageInstance = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        float baseDamage = (attackDamageInstance != null) ? (float) attackDamageInstance.getValue() : 0;
        float attackCooldown = player.getAttackCooldown();
        float enchantmentBonus = 0;

        for (Map.Entry<Enchantment, Integer> enchantment : player.getInventory().getItemInMainHand().getEnchantments().entrySet()) {
            enchantmentBonus += enchantment.getKey().getDamageIncrease(enchantment.getValue(), EntityCategory.NONE);
        }

        baseDamage *= 0.2 + attackCooldown * attackCooldown * 0.8;
        baseDamage *= (attackCooldown > 0.9 && player.getFallDistance() > 0 && !player.isClimbing() && !player.isInWater()) ? 1.5 : 1;
        baseDamage += enchantmentBonus;

        return baseDamage;
    }
}
