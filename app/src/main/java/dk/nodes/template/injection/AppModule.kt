package dk.nodes.template.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dk.nodes.template.App


/**
 * Created by bison on 25/07/17.
 */
@Module
class AppModule(val application: App) {
    @Provides
    @ApplicationScope
    fun provideContext(): Context {
        return application.baseContext
    }

    @Provides
    @ApplicationScope
    fun provideApp(): App {
        return application
    }
}