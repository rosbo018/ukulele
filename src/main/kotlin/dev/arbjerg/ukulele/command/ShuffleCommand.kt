package dev.arbjerg.ukulele.command

import dev.arbjerg.ukulele.features.HelpContext
import dev.arbjerg.ukulele.jda.Command
import dev.arbjerg.ukulele.jda.CommandContext
import org.springframework.stereotype.Component

@Component
class ShuffleCommand : Command("shuffle") {
    override suspend fun CommandContext.invoke() {
        if (player.tracks.isNotEmpty()){
            val numShuffled = player.shuffleTracks()
            reply("shuffled $numShuffled tracks")
        }
        else {
            reply("there is no queue, not shuffling any tracks")
        }
    }
    override fun HelpContext.provideHelp() {
        addUsage("")
        addDescription("shuffles the playlist except the currently playing track")
    }

}