# IFTTT
IFTTT with OO programming

# 目标 
针对线程安全问题，平衡线程访问控制和共享对象之间的矛盾。 
# 内容
实现一个监控程序，随时监控文件系统中文件状态的改变，并对这些改变做出相应的响应。 
如:一个计算机，有多个用户在操作(增、删、改)各种文件和目录结构，其中一个用户在使用文件浏览器显示文件目录和列表时，浏览器窗口显示的目录 和文件信息要随时刷新到最新状态。
# 背景知识
IFTTT 是互联网的一种应用形态，它支持以 IF X THEN Y 的方式来定义任 务，并能够在后台自动执行任务，比如:
IF {news.163.com has new message} THEN {drag the message to my blog}
其中 IF 和 THEN 为关键词，IF 后面的部分为触发器，THEN 后面部分为任 务。
关于 IFTTT:(建议大家实验前先了解一下基本思想) IFTTT_百科:http://baike.baidu.com/item/ifttt?fr=aladdin

# 程序结构
![structure](https://github.com/sameen7/IFTTT/blob/master/structure.png)
