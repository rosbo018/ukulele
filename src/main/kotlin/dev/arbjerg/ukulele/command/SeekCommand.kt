package dev.arbjerg.ukulele.command

import dev.arbjerg.ukulele.features.HelpContext
import dev.arbjerg.ukulele.jda.Command
import dev.arbjerg.ukulele.jda.CommandContext
import org.springframework.stereotype.Component

@Component
class SeekCommand : Command("seek") {
    override suspend fun CommandContext.invoke() {
        when {
            argumentText.toIntOrNull() != null -> seekTime(argumentText.toInt())
            argumentText.trim().matches("\\d*:\\d{2}:\\d{2}".toRegex()) -> seekTimeAbsolute(argumentText.trim())
            else -> replyHelp()

        }
    }
    fun CommandContext.seekTime(time: Int){
        reply("seeking $time seconds")
        player.seek(time.toLong())
    }
    fun CommandContext.seekTimeAbsolute(timeStamp: String){
        reply("seeking to $timeStamp")
        val time: Long
        val timeSplit = timeStamp.split(":")
        time = timeSplit[0].toLong() * (60 * 60) + timeSplit[1].toLong() * 60 + timeSplit[2].toLong()
        player.setTime(time)
    }
    override fun HelpContext.provideHelp() {
        addUsage("\$seconds")
        addDescription("Seeks the current playing track by the argument seconds")
        addUsage("HH:MM:SS") //TODO: test
        addDescription("changes the currently playing track to the timestamp")
    }
}
