package com.ibsplc.icargo.business.uld.defaults.event;

import com.ibsplc.icargo.business.uld.defaults.event.evaluator.SendUCMChannelEvaluatorTest;
import com.ibsplc.icargo.business.uld.defaults.event.mapper.SendUCMChannelMapperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        SendUCMChannelEvaluatorTest.class,
        SendUCMChannelMapperTest.class
})
public class SendUCMChannelTestSuite {

}