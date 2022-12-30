package ua.cn.stu.foundation.sideeffects.dialogs.plugin

import ua.cn.stu.foundation.model.ErrorResult
import ua.cn.stu.foundation.model.tasks.callback.CallbackTask
import ua.cn.stu.foundation.model.tasks.callback.Emitter
import ua.cn.stu.foundation.sideeffects.SideEffectMediator
import ua.cn.stu.foundation.sideeffects.dialogs.Dialogs

class DialogsSideEffectMediator : SideEffectMediator<DialogsSideEffectImpl>(), Dialogs {

    var retainedState = RetainedState()

    override suspend fun show(dialogConfig: DialogConfig): Boolean =
        CallbackTask.create<Boolean> { emitter ->
            if (retainedState.record != null) {
                emitter.emit(ErrorResult(IllegalStateException("Can't launch more than 1 dialog at a time")))
                return@create
            }

            val wrappedEmitter = Emitter.wrap(emitter) {
                retainedState.record = null
            }

            val record = DialogRecord(wrappedEmitter, dialogConfig)
            wrappedEmitter.setCancelListener {
                target { implementation ->
                    implementation.removeDialog()
                }
            }

            target { implementation ->
                implementation.showDialog(record)
            }

            retainedState.record = record
        }.suspend()

    class DialogRecord(
        val emitter: Emitter<Boolean>,
        val config: DialogConfig
    )

    class RetainedState(
        var record: DialogRecord? = null
    )
}