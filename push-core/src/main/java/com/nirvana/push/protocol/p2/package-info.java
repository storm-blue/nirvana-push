package com.nirvana.push.protocol.p2;

/*
* 二级协议采用纯文本协议。Line Delimiter Base Second Level Text Protocol.简写做[DST]。
*
* 1，以换行符为分隔符分隔元素，合法分隔符为以下三种：'\n','\r\n','\r'
* 2，元素的顺序在DST协议中是有意义的。
* 3，每个元素可以作为单独一个值，也可以用key-value的形式。作为单独值的元素必须以'-'起始。作为key-value形式，
*    则使用'-'分隔key和value。key值中不允许出现'-'字符。以下是合法的协议文本示例：
*    a:[-tom\n-toms-password\r\n]
*    b:[username-tom\rpassword-toms-password\r]
*    c:[-tom\npassword-tom123\n]
*
* Created by Nirvana on 2017/9/3.
* */