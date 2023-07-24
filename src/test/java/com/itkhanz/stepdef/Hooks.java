package com.itkhanz.stepdef;

import com.itkhanz.utils.GlobalParams;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.ThreadContext;

public class Hooks {
    @Before
    public void setup() {
        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();

        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_" + params.getDeviceName());
    }

    @After
    public void teardown() {

    }
}
