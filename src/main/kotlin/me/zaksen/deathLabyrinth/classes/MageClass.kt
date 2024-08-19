package me.zaksen.deathLabyrinth.classes

import me.zaksen.deathLabyrinth.item.ItemsController
import me.zaksen.deathLabyrinth.item.weapon.WeaponType
import org.bukkit.entity.Player

class MageClass() : PlayerClass {

    override fun getClassName(): String {
        return "<blue>Маг</blue>"
    }

    override fun availableWeapons(): Set<WeaponType> {
        return setOf(WeaponType.STAFF)
    }

    override fun launchSetup(player: Player) {
        player.inventory.setItem(0, ItemsController.get("heal_stuff")!!.asItemStack())
    }

}