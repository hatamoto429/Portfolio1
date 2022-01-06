package com.example.portfolio1.global
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.portfolio1.database.StoreDatabase
import com.example.portfolio1.preferences.PreferenceImpl
import com.example.portfolio1.preferences.PreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideDatabase(application: Application)
            = Room.databaseBuilder(application, StoreDatabase::class.java, "user_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesPreferenceStorage(@ApplicationContext context: Context) : PreferenceStorage{
        return PreferenceImpl(context)
    }

    @Provides
    fun providesUserDao(userDatabase: StoreDatabase) =
        userDatabase.userDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope