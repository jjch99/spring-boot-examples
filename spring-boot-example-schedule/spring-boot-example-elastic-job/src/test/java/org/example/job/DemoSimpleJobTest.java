package org.example.job;

import java.util.HashMap;
import java.util.Map;

import org.example.common.Profiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.executor.ShardingContexts;

@SpringBootTest
@ActiveProfiles(Profiles.UNIT_TEST)
@RunWith(SpringRunner.class)
public class DemoSimpleJobTest {

    @Autowired
    DemoSimpleJob demoSimpleJob;

    @Test
    public void runTask() {
        String taskId = "1";
        String jobName = demoSimpleJob.getClass().getSimpleName();
        int shardingNum = 1;
        String jobParameter = null;
        Map itemParameters = new HashMap();
        int jobEventSamplingCount = 1;
        ShardingContexts contexts = new ShardingContexts(taskId, jobName, shardingNum, jobParameter, itemParameters,
                jobEventSamplingCount);

        ShardingContext shardingContext = new ShardingContext(contexts, 0);
        demoSimpleJob.execute(shardingContext);
    }

}
