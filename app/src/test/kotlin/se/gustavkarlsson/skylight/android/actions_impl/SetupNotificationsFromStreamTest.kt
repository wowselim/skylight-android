package se.gustavkarlsson.skylight.android.actions_impl

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import se.gustavkarlsson.skylight.android.entities.AuroraReport
import se.gustavkarlsson.skylight.android.mockito.any
import se.gustavkarlsson.skylight.android.services.Notifier
import se.gustavkarlsson.skylight.android.services.streams.Stream
import se.gustavkarlsson.skylight.android.services_impl.notifications.AuroraReportNotificationEvaluator
import se.gustavkarlsson.skylight.android.services_impl.streams.RxStream

@RunWith(MockitoJUnitRunner::class)
class SetupNotificationsFromStreamTest {

	private lateinit var subject: Subject<AuroraReport>

	private lateinit var stream: Stream<AuroraReport>

	@Mock
	private lateinit var mockEvaluator: AuroraReportNotificationEvaluator

    @Mock
    private lateinit var mockNotifier: Notifier<AuroraReport>

    @Mock
    private lateinit var mockReport: AuroraReport

	private lateinit var impl: SetupNotificationsFromStream

    @Before
    fun setUp() {
        subject = PublishSubject.create<AuroraReport>()
        stream = RxStream(subject)
        impl = SetupNotificationsFromStream(stream, mockEvaluator, mockNotifier)
    }

    @Test(expected = IllegalStateException::class)
    fun runTwiceThrowsException() {
        impl()
        impl()
    }

    @Test
    fun whenSubscribedAndShouldNotifyCallNotify() {
        whenever(mockEvaluator.shouldNotify(mockReport)).thenReturn(true)

        impl()
		subject.onNext(mockReport)

		verify(mockNotifier).notify(mockReport)
    }

    @Test
    fun whenSubscribedAndShouldNotifyCallOnNotified() {
        whenever(mockEvaluator.shouldNotify(mockReport)).thenReturn(true)

        impl()
        subject.onNext(mockReport)

        verify(mockEvaluator).onNotified(mockReport)
    }

    @Test
    fun whenSubscribedAndShouldNotNotifyNotifyIsNotCalled() {
        whenever(mockEvaluator.shouldNotify(mockReport)).thenReturn(false)

        impl()
        subject.onNext(mockReport)

        verify(mockNotifier, never()).notify(any())
    }

    @Test
    fun whenSubscribedAndShouldNotNotifyOnNotifiedIsNotCalled() {
        whenever(mockEvaluator.shouldNotify(mockReport)).thenReturn(false)

        impl()
        subject.onNext(mockReport)

        verify(mockEvaluator, never()).onNotified(any())
    }
}
