package org.metamechanists.metalib.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class ParticleAnimations {
    public static void experienceSiphon(Location location) {
        ParticleUtils.sphereOut(location, Particle.TOTEM, 0.2, true);
    }

    public static void reinforcedComposter(Location location) {
        ParticleUtils.randomParticle(location, Particle.SQUID_INK, 0.5, 5);
    }

    public static void enchantingBottle(Location location) {
        final double scale = 0.3;
        for (double[] offsets : ParticleUtils.getSphere()) {
            final Location particleLocation = location.clone().add(offsets[0] * scale, offsets[1] * scale, offsets[2] * scale);
            location.getWorld().spawnParticle(Particle.SPELL_MOB, particleLocation, 0, 54 / 255F, 90 / 255F, 192 / 255F);
        }
        location.getWorld().playSound(location, Sound.ENTITY_SPLASH_POTION_BREAK, 0.5F, 1);
        location.getWorld().spawnParticle(Particle.ITEM_CRACK, location, 2, 0.2, 0.2, 0.2, 0, new ItemStack(Material.GLASS_BOTTLE));

        final ExperienceOrb experienceOrb = location.getWorld().spawn(location, ExperienceOrb.class);
        experienceOrb.setExperience(RandomUtils.randomInteger(3, 12));
    }
}
