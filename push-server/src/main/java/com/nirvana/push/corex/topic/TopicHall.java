package com.nirvana.push.corex.topic;

import java.util.Set;

public interface TopicHall {

   //添加topic
   void addTopic(ITopic topic);

   //删除topic
   void remvTopic(ITopic topic);
   //删除topic
   void remvTopic(String name);

   //获取所有topic
   Set<ITopic> getAll();


}
