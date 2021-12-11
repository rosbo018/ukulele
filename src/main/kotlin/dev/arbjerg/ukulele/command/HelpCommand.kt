package dev.arbjerg.ukulele.command

import dev.arbjerg.ukulele.features.HelpContext
import dev.arbjerg.ukulele.jda.Command
import dev.arbjerg.ukulele.jda.CommandContext
import dev.arbjerg.ukulele.jda.CommandManager
import org.springframework.stereotype.Component

@Component
class HelpCommand : Command("help") {
    override suspend fun CommandContext.invoke() {
        when (argumentText.trim()){
            "" -> replyHelp()
            "list" -> reply(printAllCommands(beans.commandManager))
            else -> {
                val command = beans.commandManager[argumentText.trim()]
                if (command != null) {
                    reply("all commands:")
                    replyHelp(command)
                }
            }
        }
    }
    fun printAllCommands(commandManager: CommandManager): String {
        val commands = commandManager.getAllCommands()
        val sb = StringBuilder("")
        for (i in commands){
            sb.append(i)
            sb.append("\n")
        }
        return sb.toString()
    }
    override fun HelpContext.provideHelp() {
        addUsage("")
        addDescription("Displays general help. (unfinished) build v1.05") // TODO
        // add text to show that player is pause/paying/not in channel
        addUsage("<command>")
        addDescription("Displays help about a specific command.")
    }
}
