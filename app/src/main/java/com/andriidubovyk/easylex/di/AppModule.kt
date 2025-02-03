package com.andriidubovyk.easylex.di

import android.app.Application
import androidx.room.Room
import com.andriidubovyk.easylex.common.DictionaryApiConstants
import com.andriidubovyk.easylex.data.data_source.FlashcardDatabase
import com.andriidubovyk.easylex.data.remote.DictionaryApi
import com.andriidubovyk.easylex.data.repository.FlashcardRepositoryImpl
import com.andriidubovyk.easylex.data.repository.NotificationRepositoryImpl
import com.andriidubovyk.easylex.data.repository.WordDetailRepositoryImpl
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository
import com.andriidubovyk.easylex.domain.repository.NotificationRepository
import com.andriidubovyk.easylex.domain.repository.WordDetailRepository
import com.andriidubovyk.easylex.domain.use_case.flashcard.AddFlashcard
import com.andriidubovyk.easylex.domain.use_case.flashcard.DeleteFlashcard
import com.andriidubovyk.easylex.domain.use_case.flashcard.FlashcardUseCases
import com.andriidubovyk.easylex.domain.use_case.flashcard.GetFlashcard
import com.andriidubovyk.easylex.domain.use_case.flashcard.GetFlashcards
import com.andriidubovyk.easylex.domain.use_case.flashcard.GetLowestScoreFlashcards
import com.andriidubovyk.easylex.domain.use_case.flashcard.GetTotalScore
import com.andriidubovyk.easylex.domain.use_case.notification.CancelNotifications
import com.andriidubovyk.easylex.domain.use_case.notification.GetNotificationsTime
import com.andriidubovyk.easylex.domain.use_case.notification.IsNotificationsEnabled
import com.andriidubovyk.easylex.domain.use_case.notification.NotificationUseCases
import com.andriidubovyk.easylex.domain.use_case.notification.ScheduleNotifications
import com.andriidubovyk.easylex.domain.use_case.word_detail.GetWordDetail
import com.andriidubovyk.easylex.domain.use_case.word_detail.WordDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFlashcardDatabase(app: Application): FlashcardDatabase {
        return Room.databaseBuilder(
            app,
            FlashcardDatabase::class.java,
            FlashcardDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFlashcardRepository(db: FlashcardDatabase): FlashcardRepository {
        return FlashcardRepositoryImpl(db.flashcardDao)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(app: Application): NotificationRepository {
        return NotificationRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideWordDetailRepository(api: DictionaryApi): WordDetailRepository {
        return WordDetailRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFlashcardUseCases(repository: FlashcardRepository): FlashcardUseCases {
        return FlashcardUseCases(
            getFlashcards = GetFlashcards(repository),
            getFlashcard = GetFlashcard(repository),
            addFlashcard = AddFlashcard(repository),
            deleteFlashcard = DeleteFlashcard(repository),
            getTotalScore = GetTotalScore(repository),
            getLowestScoreFlashcards = GetLowestScoreFlashcards(repository)
        )
    }

    @Provides
    @Singleton
    fun provideNotificationUseCases(repository: NotificationRepository): NotificationUseCases {
        return NotificationUseCases(
            scheduleNotifications = ScheduleNotifications(repository),
            cancelNotifications = CancelNotifications(repository),
            getNotificationsTime = GetNotificationsTime(repository),
            isNotificationsEnabled = IsNotificationsEnabled(repository)
        )
    }

    @Provides
    @Singleton
    fun provideWordDetailUseCases(repository: WordDetailRepository): WordDetailUseCases {
        return WordDetailUseCases(
            getWordDetail = GetWordDetail(repository)
        )
    }
}