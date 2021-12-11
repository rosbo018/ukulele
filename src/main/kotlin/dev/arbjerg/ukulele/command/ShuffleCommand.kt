package dev.arbjerg.ukulele.command

import dev.arbjerg.ukulele.features.HelpContext
import dev.arbjerg.ukulele.jda.Command
import dev.arbjerg.ukulele.jda.CommandContext
import org.springframework.stereotype.Component

@Component
class ShuffleCommand : Command("shuffle") {
    override suspend fun CommandContext.invoke() {

        if (player.tracks.isNotEmpty()){
            var numShuffled = 0
            if (argumentText.isNullOrEmpty()) {
                numShuffled = player.shuffleTracks()

            }
            else if (argumentText.trim().matches("\\d+-\\d+".toRegex())) {
                val (start, end ) = "(\\d+)-(\\d+)".toRegex().find(argumentText.trim())!!.destructured
                numShuffled = player.shuffleTracks(start.toInt(), end.toInt())
            }
            reply("shuffled $numShuffled tracks")
        }
        else {
            reply("there is no queue, not shuffling any tracks")
        }
    }
    override fun HelpContext.provideHelp() {
        addUsage("")
        addDescription("shuffles the playlist except the currently playing track")
        addUsage("\\$i \\$j")
        addDescription("Shuffles the tracks from position i to j")
        TODO("not done yet")
        //TODO: add an option to undo the shuffle? maybe keep a backup queue and reset
        //if the player has less than the number, compare and remove ones that have already been played
        //or have an option to do that
    }

}
