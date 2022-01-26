package uz.gita.profmaktabuz.domen.observersData

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import java.util.*

class EventObserver<T>(private val onEventUnhandledContent : (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}