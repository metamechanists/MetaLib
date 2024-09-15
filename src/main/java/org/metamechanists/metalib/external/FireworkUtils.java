package org.metamechanists.metalib.external;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// https://github.com/BlvckBytes/IsolatedFireworkEffects

@SuppressWarnings("all")
public class FireworkUtils {
    /*
     * MIT License
     *
     * Copyright (c) 2023 BlvckBytes
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     * The above copyright notice and this permission notice shall be included in all
     * copies or substantial portions of the Software.
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     * SOFTWARE.
     */
    
    // https://wiki.vg/Entity_statuses#Firework_Rocket
    private static final byte EVENT_ID_FIREWORK_EXPLOSION = (byte) 17;
    
    private Method M_ENTITY_FIREWORKS__INACTIVE_TICK, M_WORLD__BROADCAST_ENTITY_EFFECT;
    private Field F_ENTITY__WORLD;
    private final Field F_CRAFT_ENTITY__HANDLE;
    
    private final String bukkitVersionString;
    private final String nmsVersionString;
    private final boolean isVersionAfterRefactor;
    
    public FireworkUtils() throws Exception {
        this.bukkitVersionString = getBukkitVersionString();
        int minorVersion = getMinorVersion(this.bukkitVersionString);
        
        this.isVersionAfterRefactor = minorVersion >= 17;
        this.nmsVersionString = this.isVersionAfterRefactor ? null : getNMSVersionString();
        
        // broadcastEntityEffect has been removed, but thank god: inactiveTick() also checks
        // for explosions now (which it didn't do on previous versions)
        if (minorVersion >= 18) {
            this.M_ENTITY_FIREWORKS__INACTIVE_TICK = locateInactiveTickMethod();
            this.M_ENTITY_FIREWORKS__INACTIVE_TICK.setAccessible(true);
        }
        
        else {
            this.M_WORLD__BROADCAST_ENTITY_EFFECT = locateBroadcastEntityEffectMethod();
            this.M_WORLD__BROADCAST_ENTITY_EFFECT.setAccessible(true);
            
            this.F_ENTITY__WORLD = locateEntityWorldField();
            this.F_ENTITY__WORLD.setAccessible(true);
        }
        
        this.F_CRAFT_ENTITY__HANDLE = locateBukkitEntityHandleField();
        this.F_CRAFT_ENTITY__HANDLE.setAccessible(true);
    }
    
    public void playFireworkEffect(Location location, FireworkEffect... effects) {
        World world = location.getWorld();
        
        if (world == null)
            throw new IllegalArgumentException("The provided location has to contain a valid world reference");
        
        // Fully spawns a firework for nearby players (PacketPlayOutSpawnEntity + PacketPlayOutEntityMetadata)
        Firework firework = world.spawn(location, Firework.class, fireworkBeforeAdd -> {
            // This callback is invoked before spawning the entity
            
            // Apply the firework effects to the entity before spawning it, to add the effects to it's metadata
            FireworkMeta fireworkMeta = fireworkBeforeAdd.getFireworkMeta();
            fireworkMeta.addEffects(effects);
            fireworkBeforeAdd.setFireworkMeta(fireworkMeta);
            
            // Mark the entity for detonation on the next tick
            fireworkBeforeAdd.detonate();
        });
        
        // As this all is in synchronous context, the entity had no time to tick() yet
        
        try {
            Object nmsEntity = F_CRAFT_ENTITY__HANDLE.get(firework);
            
            // Invoke inactiveTick() to send out the entity event packet for firework detonation
            if (M_ENTITY_FIREWORKS__INACTIVE_TICK != null) {
                M_ENTITY_FIREWORKS__INACTIVE_TICK.invoke(nmsEntity);
            }
            
            // Invoke broadcastEntityEffect() manually, to send the entity event packet to all nearby players
            else {
                Object entityWorld = F_ENTITY__WORLD.get(nmsEntity);
                M_WORLD__BROADCAST_ENTITY_EFFECT.invoke(entityWorld, nmsEntity, EVENT_ID_FIREWORK_EXPLOSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Remove the no longer needed entity
        firework.remove();
    }
    
    //=========================================================================//
    //                                  Fields                                 //
    //=========================================================================//
    
    private Field locateBukkitEntityHandleField() throws Exception {
        Class<?> entityClass = locateEntityClass();
        Class<?> bukkitEntityClass = locateBukkitEntityClass();
        
        Field targetField = searchInSelfAndInheritedClasses(bukkitEntityClass, type -> {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() == entityClass)
                    return field;
            }
            return null;
        });
        
        if (targetField == null)
            throw new IllegalStateException("Could not locate the entity field within the bukkit entity class");
        
        return targetField;
    }
    
    private Field locateEntityWorldField() throws Exception {
        Class<?> entityClass = locateEntityClass();
        Class<?> worldClass = locateWorldClass();
        
        Field targetField = searchInSelfAndInheritedClasses(entityClass, type -> {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() == worldClass)
                    return field;
            }
            return null;
        });
        
        if (targetField == null)
            throw new IllegalStateException("Could not locate the world field within the entity class");
        
        return targetField;
    }
    
    //=========================================================================//
    //                                 Methods                                 //
    //=========================================================================//
    
    private Method locateInactiveTickMethod() throws Exception {
        Class<?> entityFireworksClass = locateEntityFireworksClass();
        
        Method targetMethod = searchInSelfAndInheritedClasses(entityFireworksClass, type -> {
            for (Method method : type.getDeclaredMethods()) {
                if (method.getName().equals("inactiveTick"))
                    return method;
            }
            return null;
        });
        
        if (targetMethod == null)
            throw new IllegalStateException("Could not locate the inactiveTick method of the firework entity");
        
        return targetMethod;
    }
    
    private Method locateBroadcastEntityEffectMethod() throws Exception {
        Class<?> worldClass = locateWorldClass();
        
        Method targetMethod = searchInSelfAndInheritedClasses(worldClass, type -> {
            for (Method method : type.getDeclaredMethods()) {
                if (method.getName().equals("broadcastEntityEffect"))
                    return method;
            }
            return null;
        });
        
        if (targetMethod == null)
            throw new IllegalStateException("Could not locate the broadcastEntityEffect method of World");
        
        return targetMethod;
    }
    
    //=========================================================================//
    //                                 Classes                                 //
    //=========================================================================//
    
    private Class<?> locateBukkitEntityClass() throws Exception {
        return Class.forName("org.bukkit.craftbukkit." + bukkitVersionString + ".entity.CraftEntity");
    }
    
    private Class<?> locateEntityClass() throws Exception {
        if (isVersionAfterRefactor)
            return Class.forName("net.minecraft.world.entity.Entity");
        return Class.forName("net.minecraft.server." + nmsVersionString + ".Entity");
    }
    
    private Class<?> locateEntityFireworksClass() throws Exception {
        if (isVersionAfterRefactor)
            return Class.forName("net.minecraft.world.entity.projectile.EntityFireworks");
        return Class.forName("net.minecraft.server." + nmsVersionString + ".EntityFireworks");
    }
    
    private Class<?> locateWorldClass() throws Exception {
        if (isVersionAfterRefactor)
            return Class.forName("net.minecraft.world.level.World");
        return Class.forName("net.minecraft.server." + nmsVersionString + ".World");
    }
    
    //=========================================================================//
    //                                 Version                                 //
    //=========================================================================//
    
    private String getBukkitVersionString() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        Bukkit.getLogger().severe(packageName);
        return packageName.split("\\.")[3];
    }

    private String getNMSVersionString() throws Exception {
        String version = searchInSelfAndInheritedClasses(Bukkit.getServer().getClass(), type -> {
            for (Field field : type.getDeclaredFields()) {
                String typeName = field.getType().getSimpleName();
                if (typeName.equals("DedicatedServer") || typeName.equals("MinecraftServer"))
                    return field.getType().getName().split("\\.")[3];
            }
            return null;
        });
        
        if (version == null)
            throw new IllegalStateException("Could not detect the NMS package version");
        
        return version;
    }
    
    private int getMinorVersion(String versionString) {
        String[] versionData = versionString.split("_");
        return Integer.parseInt(versionData[1]);
    }
    
    //=========================================================================//
    //                                Utilities                                //
    //=========================================================================//
    
    private <T> T searchInSelfAndInheritedClasses(Class<?> type, FUnsafeFunction<Class<?>, T> consumer) throws Exception {
        while (type != null && type != Object.class) {
            T response = consumer.call(type);
            
            if (response != null)
                return response;
            
            type = type.getSuperclass();
        }
        
        return null;
    }
    
    // Come on java, why doesn't your stdlib contain this...
    // Inlined into this class for ease of use, excuse me!
    @FunctionalInterface
    public interface FUnsafeFunction<I, O> {
        O call(I input) throws Exception;
    }
}