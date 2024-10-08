package me.zaksen.deathLabyrinth.item.weapon.weapons.stuff

import me.zaksen.deathLabyrinth.entity.trader.TraderType
import me.zaksen.deathLabyrinth.item.ItemQuality
import me.zaksen.deathLabyrinth.item.settings.ItemSettings
import me.zaksen.deathLabyrinth.item.weapon.WeaponItem
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import me.zaksen.deathLabyrinth.util.ChatUtil
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerInteractEvent

class HealStuff(id: String): WeaponItem(
    WeaponType.MISC_STUFF,
    id,
    ItemSettings(Material.STICK)
        .customModel(100)
        .displayName(ChatUtil.format("<green>Посох исцеления</green>"))
        .abilityCooldown(30000)
        .quality(ItemQuality.RARE)
        .tradePrice(100)
        .addAviableTrader(TraderType.NORMAL)
)
{
    override fun onUse(event: PlayerInteractEvent) {
        val item = event.item!!

        if(checkAndUpdateCooldown(item)) {
            event.player.heal(6.0, EntityRegainHealthEvent.RegainReason.MAGIC)

            event.player.world.spawnParticle(
                Particle.TOTEM_OF_UNDYING,
                event.player.location,
                50,
                0.5,
                0.5,
                0.5
            )
        }
    }
}