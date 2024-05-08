package io.github.alejomc26.utils;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Utils class for anyone using BossLibrary
 */
public class BossUtils {

    public static Player getNearestPlayer(Location location) {
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

    public static double getPlayerDamage(Player player) {
        AttributeInstance attackDamageInstance = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackDamageInstance == null) {
            return 0.0;
        }
        float attackCooldown = player.getAttackCooldown();
        float enchantmentDamageBonus = getItemEnchantmentBonus(player.getInventory().getItemInMainHand());
        boolean isCritical = attackCooldown > 0.9 && player.getFallDistance() > 0 && !player.isClimbing() && !player.isInWater();

        return getPlayerDamage(attackDamageInstance.getValue(), attackCooldown, enchantmentDamageBonus, isCritical);
    }

    public static float getItemEnchantmentBonus(ItemStack itemStack) {
        float enchantmentDamageBonus = 0;
        for (Map.Entry<Enchantment, Integer> enchantment : itemStack.getEnchantments().entrySet()) {
            enchantmentDamageBonus += enchantment.getKey().getDamageIncrease(enchantment.getValue(), EntityCategory.NONE);
        }
        return enchantmentDamageBonus;
    }

    public static double getPlayerDamage(double baseDamage, float attackCooldown, float enchantmentDamageBonus, boolean isCritical) {
        double attackCooldownMultiplier = 0.2 + attackCooldown * attackCooldown * 0.8;
        double damage = baseDamage * attackCooldownMultiplier;
        if (isCritical) {
            damage *= 1.5;
        }
        return damage + enchantmentDamageBonus;
    }
}
