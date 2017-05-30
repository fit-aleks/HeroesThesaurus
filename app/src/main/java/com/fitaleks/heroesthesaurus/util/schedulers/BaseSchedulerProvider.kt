package com.fitaleks.heroesthesaurus.util.schedulers

import rx.Scheduler

/**
 * Allow providing different types of [Scheduler]s.
 */
interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
