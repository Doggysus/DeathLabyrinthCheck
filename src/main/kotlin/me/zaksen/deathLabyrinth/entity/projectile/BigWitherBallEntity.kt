package me.zaksen.deathLabyrinth.entity.projectile

import me.zaksen.deathLabyrinth.entity.friendly.FriendlyEntity
import me.zaksen.deathLabyrinth.event.EventManager
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.windcharge.WindCharge
import net.minecraft.world.level.ExplosionDamageCalculator
import net.minecraft.world.level.Level
import net.minecraft.world.level.SimpleExplosionDamageCalculator
import net.minecraft.world.level.entity.EntityTypeTest
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.CraftWorld
import java.util.*

class BigWitherBallEntity(location: Location): WindCharge(EntityType.WIND_CHARGE, (location.getWorld() as CraftWorld).handle) {
    init {
        this.setPos(location.x, location.y, location.z)
    }

    override fun explode(pos: Vec3?) {
        level().explode(
                this,
                null,
                EXPLOSION_DAMAGE_CALCULATOR,
                pos!!.x(),
                pos.y(),
                pos.z(),
                0.8f,
                false,
                Level.ExplosionInteraction.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL,
                ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.WIND_CHARGE_BURST
        )

        val entities = level().getEntities(
            EntityTypeTest.forClass(LivingEntity::class.java),
            AABB(
            x - 2.0,
            y - 2.0,
            z - 2.0,
            x + 2.0,
            y + 2.0,
            z + 2.0
            )
        ) {
            it.isAlive && it !is Player && it !is FriendlyEntity
        }

        for (entity in entities) {
            if(owner == null) {
                EventManager.callSpellEntityDamageEvent(entity, 13.0)
            } else {
                EventManager.callPlayerSpellEntityDamageEvent(owner!!.bukkitEntity as org.bukkit.entity.Player, entity, 13.0)
            }
            entity.addEffect(
                MobEffectInstance(
                MobEffects.WITHER,
                80,
                2
            ), this.effectSource)
            entity.addEffect(
            MobEffectInstance(
                MobEffects.WEAKNESS,
                80
            ), this.effectSource)
        }
    }

    override fun checkDespawn() { }

    companion object {
        private val EXPLOSION_DAMAGE_CALCULATOR: ExplosionDamageCalculator = SimpleExplosionDamageCalculator(
            false,
            false,
            Optional.of(0f),
            Optional.empty()
        )
    }
}