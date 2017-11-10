package itomy.ch.pearson.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import itomy.ch.pearson.R

class WarningDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        val title = arguments!!.getInt(MESSAGE_INT_BUNDLE_KEY)
        builder.setTitle(title)
        builder.setPositiveButton(android.R.string.ok, null)

        return builder.create()
    }

    companion object {

        val TAG = "itomy.ch.pearson.dialogs.TAG"

        private val TITLE_INT_BUNDLE_KEY = "title_int"
        private val MESSAGE_INT_BUNDLE_KEY = "message_int"


        fun newInstance(): DialogFragment {
            val args = Bundle()
            args.putInt(TITLE_INT_BUNDLE_KEY, R.string.dialog_default_error_title)
            args.putInt(MESSAGE_INT_BUNDLE_KEY, R.string.dialog_default_error_description)
            return newDialog(args)
        }

        private fun newDialog(args: Bundle): DialogFragment {
            val dialog = WarningDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
