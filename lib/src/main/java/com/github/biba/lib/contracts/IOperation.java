package com.github.biba.lib.contracts;

public interface IOperation<Result> {

    Result perform() throws Exception;

}
