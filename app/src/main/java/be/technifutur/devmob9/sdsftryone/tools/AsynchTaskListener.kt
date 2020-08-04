package be.technifutur.devmob9.sdsftryone.tools

interface AsynchTaskListener {
    fun onProgressUpdate(progress: Int)
    fun onComplete(message: String)
}