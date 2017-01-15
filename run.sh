start()
{
  nohup java -jar jcoder-2.1.6.war -f=config.properties > nohup.log &
  echo $! >jcoder.pid
}
stop()
{
  kill  `cat jcoder.pid`
}


case $1 in
"restart")
   stop
   start
;;
"start")
   start
;;
"stop")
   stop
;;
*) echo "only accept params start|stop|restart" ;;
esac
