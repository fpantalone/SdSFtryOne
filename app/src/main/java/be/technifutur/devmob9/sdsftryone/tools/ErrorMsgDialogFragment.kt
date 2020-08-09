package be.technifutur.devmob9.sdsftryone.tools

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import be.technifutur.devmob9.sdsftryone.R

class ErrorMsgDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.network_error)
                .setPositiveButton(R.string.retryDialog, DialogInterface.OnClickListener { dialog, which ->  })
                .setNegativeButton(R.string.cancelDialog, DialogInterface.OnClickListener { dialog, which ->  })
            builder.create()
        } ?: throw IllegalStateException ("Activity cannot be null")
    }
}