package com.github.biba.lib.threadingTest;

import com.github.biba.lib.contracts.IOperation;

public class TestOperation implements IOperation<String> {

    @Override
    public String perform() {
        return "Success";
    }

}
