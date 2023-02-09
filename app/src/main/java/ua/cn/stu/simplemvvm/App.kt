package ua.cn.stu.simplemvvm

import android.app.Application
import kotlinx.coroutines.Dispatchers
import ua.cn.stu.foundation.BaseApplication
import ua.cn.stu.foundation.model.coroutines.IoDispatchers
import ua.cn.stu.foundation.model.coroutines.WorkDispatchers
import ua.cn.stu.simplemvvm.model.colors.InMemoryColorsRepository

/**
 * Here we store instances of model layer classes.
 */
class App : Application(), BaseApplication {

    private val ioDispatchers = IoDispatchers(Dispatchers.IO)

    /**
     * Place your singleton scope dependencies here
     */
    override val singletonScopeDependencies: List<Any> = listOf(
        InMemoryColorsRepository(ioDispatchers)
    )
}