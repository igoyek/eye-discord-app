package dev.igoyek.eye

class DiscordAppLauncher() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = DiscordApp()

            app.run()
        }
    }
}