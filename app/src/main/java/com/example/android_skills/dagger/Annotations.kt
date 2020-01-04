package com.example.android_skills.dagger.daggerVM.viewmodel_factory

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class SourceScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RoomScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ViewScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope