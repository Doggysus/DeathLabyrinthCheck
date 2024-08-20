package me.zaksen.deathLabyrinth

import me.zaksen.deathLabyrinth.command.*
import me.zaksen.deathLabyrinth.config.MainConfig
import me.zaksen.deathLabyrinth.config.loadConfig
import me.zaksen.deathLabyrinth.event.CustomItemEvents
import me.zaksen.deathLabyrinth.event.GameEvents
import me.zaksen.deathLabyrinth.event.MenuEvents
import me.zaksen.deathLabyrinth.game.GameController
import me.zaksen.deathLabyrinth.game.room.RoomController
import me.zaksen.deathLabyrinth.keys.PluginKeys
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class DeathLabyrinth : JavaPlugin() {

    private val roomDirectory = File(dataFolder, "rooms")
    private lateinit var mainConfig: MainConfig

    override fun onEnable() {
        loadConfigs()
        PluginKeys.setup(this)
        RoomController.reloadRooms(roomDirectory)
        GameController.setup(this, mainConfig)
        RoomController.setup(mainConfig)
        registerEvents()
        registerCommands()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun loadConfigs() {
        mainConfig = loadConfig(dataFolder, "main-config.yml")
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(MenuEvents(), this)
        server.pluginManager.registerEvents(CustomItemEvents(), this)
        server.pluginManager.registerEvents(GameEvents(mainConfig), this)
    }

    private fun registerCommands() {
        getCommand("game_status")?.setExecutor(GameStatusCommand())
        getCommand("class")?.setExecutor(ClassCommand(mainConfig))
        getCommand("give_item")?.setExecutor(GiveItemCommand())
        getCommand("give_item")?.tabCompleter = GiveItemCommand()
        getCommand("reload_game")?.setExecutor(GameReloadCommand(roomDirectory))
        getCommand("item_tab")?.setExecutor(ItemTabCommand())
        getCommand("summon_custom")?.setExecutor(CustomSummonCommand())
        getCommand("summon_custom")?.tabCompleter = CustomSummonCommand()
    }
}
