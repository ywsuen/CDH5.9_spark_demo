package com.ywsuen.spark.kpi.bean

case class BaseConf(jobRunInterval:Int,
                    refreshTime: Long,
                    kafkaZkAdds: String,
                    consumerID: String,
                    hbaseZkAdds: String,
                    hbaseNameSpace: String,
                    redisAdds: String,
                    redisCache: Long,
                    rowkeyType: Int
                     )