package com.nirvana.push.corex.topic;

import java.util.Set;

/**
 *  topic存储,管理
 *
 * @author zc
 * @version 1.0
 * @date 2017-8-21
 */
public interface TopicHall {


   //添加topic
   void addTopic(ITopic topic);

   //删除topic
   void remvTopic(String name);

   //获取所有topic
   Set<ITopic> getAll();

   ITopic getTopic(String name);

   boolean contains(String name);

   int count();


}
