package com.github.biba.lib.threadingTest;

import com.github.biba.lib.BuildConfig;
import com.github.biba.lib.TestConstants;
import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.AsyncTaskExecutor;
import com.github.biba.lib.threading.executors.IExecutor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = TestConstants.SDK_VERSION
)
public class AsyncTaskExecutorTest {

    private IExecutor mExecutor;

    @Before
    public void setUp() {
        mExecutor = spy(getExecutor());
    }

    private IExecutor getExecutor() {
        return new AsyncTaskExecutor(AsyncTaskExecutor.Config.getDefaultConfig());
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
                final String message = callback.getMessage();
                Assert.assertEquals("Success", message);
            }
        });
    }

}
