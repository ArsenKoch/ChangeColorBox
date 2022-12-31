package ua.cn.stu.simplemvvm

import android.app.Application
import ua.cn.stu.foundation.BaseApplication
import ua.cn.stu.simplemvvm.model.colors.InMemoryColorsRepository

/**
 * Here we store instances of model layer classes.
 */
class App : Application(), BaseApplication {

    /**
     * Place your singleton scope dependencies here
     */
    override val singletonScopeDependencies: List<Any> = listOf(
        InMemoryColorsRepository()
    )
}