package dev.arbjerg.ukulele.command

import dev.arbjerg.ukulele.features.HelpContext
import dev.arbjerg.ukulele.jda.Command
import dev.arbjerg.ukulele.jda.CommandContext
import org.springframework.stereotype.Component
import kotlin.NumberFormatException

@Component
class SeekCommand : Command("seek") {
    override suspend fun CommandContext.invoke() {
        when {
            argumentText.toIntOrNull() != null -> seekTime(argumentText.toInt())
            argumentText.trim().matches("(\\d*:)?\\d{2}:\\d{2}".toRegex()) -> seekTimeAbsolute(argumentText.trim())
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
        val match = Regex("(\\d*):?(\\d{2}):(\\d{2})").find(timeStamp)!!
        val (hour, minute, second) = match.destructured
        log.debug("$hour:$minute:$second")
        val toLong = fun (s: String) : Long { var x : Long; try {x = s.toLong()} catch (e : NumberFormatException) {return 0}; return x}
        time = toLong(hour) * 3600 + toLong(minute) * 60 + toLong(second)
        player.setTime(time)
    }
    override fun HelpContext.provideHelp() {
        addUsage("\$seconds")
        addDescription("Seeks the current playing track by the argument seconds")
        addUsage("HH:MM:SS") //TODO: test
        addDescription("changes the currently playing track to the timestamp")
    }
}
