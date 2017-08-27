package com.nirvana.push.corex.publisher;

public interface Publisher {


    void publish(String topicName);

    void pushMessage(String topicName,Object msg);
}
