package com.github.biba.lib.threadingTest;

import android.os.Handler;

import com.github.biba.lib.TestConstants;
import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;
import com.github.biba.lib.threading.executors.ThreadExecutor;
import com.github.biba.lib.threading.publisher.IPublisher;
import com.github.biba.lib.threading.publisher.Publisher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.Scheduler;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

//TODO fix
@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = TestConstants.SDK_VERSION
)
public class ThreadExecutorTest {

    private IPublisher mPublisher;

    private IExecutor mExecutor;

    @Before
    public void setUp() {
        mExecutor = spy(getExecutor());
    }

    @Test
    public void execute() {
        final TestCallback callback = new TestCallback();
        final TestOperation executable = new TestOperation();
        final Command<String> command = new Command<>(executable);
        command.setCallback(callback);
        final List<ICommand> commands = new ArrayList<>();
        commands.add(command);
        commands.add(command);
        commands.add(command);
        IExecutedCallback executedCallback = new IExecutedCallback() {

            @Override
            public void onFinished() {
                final int count = callback.getCount();
                Assert.assertEquals(3, count);
            }
        };
        mExecutor.execute(commands, executedCallback);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mPublisher);
    }

    private IExecutor getExecutor() {
        final Handler mockedHandler = mock(Handler.class);
        final Publisher publisher = new Publisher(mockedHandler);
        mPublisher = spy(publisher);
        doAnswer(new Answer() {

            @Override
            public Object answer(final InvocationOnMock invocation) {
                final Runnable runnable = invocation.getArgument(0);
                final Scheduler backgroundThreadScheduler = Robolectric.getBackgroundThreadScheduler();

                backgroundThreadScheduler.pause();
                backgroundThreadScheduler.post(runnable);
                Robolectric.flushBackgroundThreadScheduler();
                backgroundThreadScheduler.unPause();

                return null;
            }
        }).when(mockedHandler).post(any(Runnable.class));
        return new ThreadExecutor(mPublisher);
    }
}
