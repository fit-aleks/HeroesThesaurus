package com.fitaleks.heroesthesaurus.util.schedulers

/**
 * Provides different types of schedulers.
 */
class SchedulerProvider private constructor() : BaseSchedulerProvider {

    companion object {
        val instance by lazy {
            SchedulerProvider()
        }
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}
