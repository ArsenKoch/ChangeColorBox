package ua.cn.stu.foundation.sideeffects.dialogs.plugin

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleObserver
import ua.cn.stu.foundation.model.SuccessResult
import ua.cn.stu.foundation.sideeffects.SideEffectImplementation
import ua.cn.stu.foundation.sideeffects.dialogs.plugin.DialogsSideEffectMediator.DialogRecord

class DialogsSideEffectImpl : SideEffectImplementation(), LifecycleObserver {

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().lifecycle.addObserver(this)
    }

    fun showDialog(record: DialogRecord) {
        val config = record.config
        val emitter = record.emitter
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(config.title)
            .setMessage(config.message)
            .setCancelable(config.cancellable)
        if (config.positiveButton.isNotBlank()) {
            builder.setPositiveButton(config.positiveButton) { _, _ ->
                emitter.emit(SuccessResult(true))
                dialog = null
            }
        }
        if (config.negativeButton.isNotBlank()) {
            builder.setNegativeButton(config.negativeButton) { _, _ ->
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        if (config.cancellable) {
            builder.setOnCancelListener {
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        val dialog = builder.create()
        dialog.show()
        this.dialog = dialog
    }

    fun removeDialog() {
        dialog?.dismiss()
        dialog = null
    }
}