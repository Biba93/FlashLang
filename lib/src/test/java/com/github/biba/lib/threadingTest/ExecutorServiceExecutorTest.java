package com.github.biba.lib.threadingTest;

import android.os.Handler;

import com.github.biba.lib.TestConstants;
import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.ExecutorServiceExecutor;
import com.github.biba.lib.threading.executors.IExecutor;
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
public class ExecutorServiceExecutorTest {

    private IExecutor mExecutor;

    private IPublisher mPublisher;

    @Before
    public void setUp() {
        mExecutor = spy(getExecutor());
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mPublisher);
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
        mExecutor.execute(commands, new IExecutedCallback() {

            @Override
            public void onFinished() {
                final int count = callback.getCount();
                Assert.assertEquals(3, count);
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }
    }

    private IExecutor getExecutor() {
        final Handler mockedHandler = mock(Handler.class);
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(final InvocationOnMock invocation) {
                final Runnable runnable = invocation.getArgument(0);

                final Scheduler backgroundThreadScheduler = Robolectric.getBackgroundThreadScheduler();

                backgroundThreadScheduler.pause();
                backgroundThreadScheduler.post(runnable);
                Robolectric.flushBackgroundThreadScheduler();
                backgroundThreadScheduler.unPause();

                return null;
            }
        }).when(mockedHandler).post(any(Runnable.class));
        final Publisher publisher = new Publisher(mockedHandler);
        mPublisher = spy(publisher);
        return new ExecutorServiceExecutor(mPublisher, ExecutorServiceExecutor.Config.getDefaultConfig());
    }
}
