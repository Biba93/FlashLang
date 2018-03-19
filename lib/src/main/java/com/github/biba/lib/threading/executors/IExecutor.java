package com.github.biba.lib.threading.executors;

import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.ICommand;

import java.util.List;

public interface IExecutor {

    <Result> void execute(ICommand<Result> pCommand);

    void execute(List<? extends ICommand> pCommands);

    void execute(List<? extends ICommand> pCommands, IExecutedCallback pExecutedCallback);

}
