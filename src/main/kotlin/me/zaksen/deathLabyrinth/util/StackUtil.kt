package me.zaksen.deathLabyrinth.util

import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun ItemStack.name(name: Component): ItemStack {
    val meta = this.itemMeta
    meta.itemName(name)
    itemMeta = meta
    return this
}

fun ItemStack.loreMap(lore: List<Component>): ItemStack {
    val meta = this.itemMeta
    meta.lore(lore)
    itemMeta = meta
    return this
}

fun ItemStack.loreLine(line: Component): ItemStack {
    val meta = this.itemMeta
    var lore = meta.lore()

    if(lore != null) {
        lore.add(line)
    } else {
        lore = mutableListOf(line)
    }

    meta.lore(lore)
    itemMeta = meta
    return this
}


fun ItemStack.customModel(model: Int): ItemStack {
    val meta = this.itemMeta
    meta.setCustomModelData(model)
    itemMeta = meta
    return this
}