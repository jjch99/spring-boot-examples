package org.example;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.Node;
import org.junit.Test;

import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import lombok.extern.slf4j.Slf4j;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;

@Slf4j
public class KafkaAdmin {

    @Test
    public void list() {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        AdminClient adminClient = AdminClient.create(props);

        Map<Node, scala.collection.immutable.List<GroupOverview>> consumerGroups =
                (Map<Node, List<GroupOverview>>) scala.collection.JavaConverters
                        .mapAsJavaMapConverter(adminClient.listAllConsumerGroups()).asJava();

        consumerGroups.keySet();
    }

}
