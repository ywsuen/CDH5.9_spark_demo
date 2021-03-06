package com.ywsuen.spark.kpi.util

import com.ywsuen.spark.kpi.bean._
import net.liftweb.json.{DefaultFormats, Serialization}

object JsonParserUtilTest {
  def main (args: Array[String]){
    implicit val format = DefaultFormats
    val extractConf = ExtractConf(BaseConf(1,1,"1","1","1","","",0,0),Map("k"->TopicConf("","",0,Map("s"->SourceType(Map("r"->Reg("","","",0)),List(Mi("",0,0)))))))
    val str = Serialization.write(extractConf)
    val obj = JsonParserUtil.parse2CaseClass[ExtractConf](str)match {
      case Right(errInfo) => print(s"1 errInfo: $errInfo")
      case Left(obj) => println("回归测试"+(obj == extractConf))
    }

    JsonParserUtil.parse2CaseClass[ExtractConf](
      """
        |{
        |  "baseConf": {
        |    "jobRunInterval": 10,
        |    "refreshTime": 60000,
        |    "kafkaZkAdds": "10.60.6.31:9092,10.60.6.32:9092",
        |    "consumerID": "sparkKpi",
        |    "hbaseZkAdds": "ztytjkzk001:2181,ztytjkzk002:2181,ztytjkzk003:2181",
        |    "hbaseNameSpace": "ZT",
        |    "redisAdds": "10.60.6.17:6379,10.60.6.18:6379,10.60.6.24:6379",
        |    "redisCache": 900000,
        |    "rowkeyType": 2
        |  },
        |  "topics": {
        |
        |    "zzt": {
        |      "topicName": "zzt",
        |      "bizName": "zzt",
        |      "threadNum": 2,
        |      "sourceType": {
        |        "zzt1": {
        |          "filterKeyWords": {},
        |          "regs": {
        |            "time": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "\\d{2}:\\d{2}:\\d{2}.\\d{3}",
        |              "regIndex": 0
        |            },
        |            "time2": {
        |              "field": "FILE_PATH",
        |              "regType": "1",
        |              "reg": "(\\d{8})\\d{2}",
        |              "regIndex": 1
        |            },
        |            "node": {
        |              "field": "SOURCE_HOST",
        |              "regType": "0",
        |              "reg": "",
        |              "regIndex": 0
        |            },
        |            "bs_zzt_td_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:EntrustStock|BATCHENTRUSTEX|BATCHSELLEX)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:WithDrawEX)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BANKEXCHANGEEX|BankCGExchangeEX)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:LOGIN)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!EntrustStock|BATCHENTRUSTEX|BATCHSELLEX|WithDrawEX|BANKEXCHANGEEX|BankCGExchangeEX|LOGIN).+)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |
        |            "channel": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ThreadNo=(\\d+)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_td_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:EntrustStock)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_td_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:EntrustStock)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:WithDrawEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:WithDrawEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BANKEXCHANGEEX|BankCGExchangeEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BANKEXCHANGEEX|BankCGExchangeEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:LOGIN)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:LOGIN)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!EntrustStock|BATCHENTRUSTEX|BATCHSELLEX|WithDrawEX|BANKEXCHANGEEX|BankCGExchangeEX|LOGIN).+)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!EntrustStock|BATCHENTRUSTEX|BATCHSELLEX|WithDrawEX|BANKEXCHANGEEX|BankCGExchangeEX|LOGIN).+)",
        |              "regIndex": 1
        |            }
        |          },
        |          "mis": [
        |            {
        |              "mi": "bs_zzt_td_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_td_fail": {
        |                  "reg": "bs_zzt_td_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_rk_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_rk_fail": {
        |                  "reg": "bs_zzt_rk_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_bt_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_bt_fail": {
        |                  "reg": "bs_zzt_bt_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_login_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_login_fail": {
        |                  "reg": "bs_zzt_login_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_query_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_query_fail": {
        |                  "reg": "bs_zzt_query_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |
        |            {
        |              "mi": "bs_zzt1_td_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_td_begin": {
        |                  "reg": "bs_zzt_td_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_td_end": {
        |                  "reg": "bs_zzt_td_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt1_rk_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_rk_begin": {
        |                  "reg": "bs_zzt_rk_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_rk_end": {
        |                  "reg": "bs_zzt_rk_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt1_bt_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_bt_begin": {
        |                  "reg": "bs_zzt_bt_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_bt_end": {
        |                  "reg": "bs_zzt_bt_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt1_login_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_login_begin": {
        |                  "reg": "bs_zzt_login_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_login_end": {
        |                  "reg": "bs_zzt_login_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt1_query_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_query_begin": {
        |                  "reg": "bs_zzt_query_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_query_end": {
        |                  "reg": "bs_zzt_query_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            }
        |          ]
        |        },
        |        "zzt2": {
        |          "filterKeyWords": {},
        |          "regs": {
        |            "time": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "\\d{2}:\\d{2}:\\d{2}.\\d{3}",
        |              "regIndex": 0
        |            },
        |            "time2": {
        |              "field": "FILE_PATH",
        |              "regType": "1",
        |              "reg": "(\\d{8})\\d{2}",
        |              "regIndex": 1
        |            },
        |            "node": {
        |              "field": "SOURCE_HOST",
        |              "regType": "0",
        |              "reg": "",
        |              "regIndex": 0
        |            },
        |            "bs_zzt_td_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=RZRQEntrustStock[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQWithDrawEX)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BankRZRQExchangeEX)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQLOGIN)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_fail": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!RZRQEntrustStock|RZRQWithDrawEX|BankRZRQExchangeEX|RZRQLOGIN).+)[\\s\\S]*ErrorNo=-",
        |              "regIndex": 1
        |            },
        |
        |            "channel": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ThreadNo=(\\d+)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_td_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:EntrustStock)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_td_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:EntrustStock)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQWithDrawEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_rk_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQWithDrawEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BankRZRQExchangeEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_bt_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:BankRZRQExchangeEX)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQLOGIN)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_login_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:RZRQLOGIN)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_begin": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ClientReq[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!RZRQEntrustStock|RZRQWithDrawEX|BankRZRQExchangeEX|RZRQLOGIN).+)",
        |              "regIndex": 1
        |            },
        |            "bs_zzt_query_end": {
        |              "field": "LOG",
        |              "regType": "1",
        |              "reg": "ReadAnswer[\\s\\S]*?[Aa][Cc][Tt][Ii][Oo][Nn]=(?:(?!EntrustStock|BATCHENTRUSTEX|BATCHSELLEX|WithDrawEX|BANKEXCHANGEEX|BankCGExchangeEX|LOGIN).+)",
        |              "regIndex": 1
        |            }
        |          },
        |          "mis": [
        |            {
        |              "mi": "bs_zzt_td_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_td_fail": {
        |                  "reg": "bs_zzt_td_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_rk_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_rk_fail": {
        |                  "reg": "bs_zzt_rk_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_bt_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_bt_fail": {
        |                  "reg": "bs_zzt_bt_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_login_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_login_fail": {
        |                  "reg": "bs_zzt_login_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt_query_fail",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "vals": {
        |                "bs_zzt_query_fail": {
        |                  "reg": "bs_zzt_query_fail",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "true",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |
        |            {
        |              "mi": "bs_zzt2_td_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_td_begin": {
        |                  "reg": "bs_zzt_td_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_td_end": {
        |                  "reg": "bs_zzt_td_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt2_rk_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_rk_begin": {
        |                  "reg": "bs_zzt_rk_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_rk_end": {
        |                  "reg": "bs_zzt_rk_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt2_bt_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_bt_begin": {
        |                  "reg": "bs_zzt_bt_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_bt_end": {
        |                  "reg": "bs_zzt_bt_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt2_login_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_login_begin": {
        |                  "reg": "bs_zzt_login_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_login_end": {
        |                  "reg": "bs_zzt_login_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            },
        |            {
        |              "mi": "bs_zzt2_query_rt_1",
        |              "filter": "",
        |              "save": 1,
        |              "interval": 10,
        |              "time": {
        |                "reg": "time",
        |                "regType": "string",
        |                "format": "HH:mm:ss.SSS",
        |                "reg2": "time2",
        |                "type2": "string",
        |                "format2": "yyyyMMdd",
        |                "offset": "28800000"
        |              },
        |              "node": {
        |                "reg": "node"
        |              },
        |              "channel": {
        |                "reg": "channel"
        |              },
        |              "vals": {
        |                "bs_zzt_query_begin": {
        |                  "reg": "bs_zzt_query_begin",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                },
        |                "bs_zzt_query_end": {
        |                  "reg": "bs_zzt_query_end",
        |                  "regType": "st",
        |                  "isNum": "false",
        |                  "combine": "false",
        |                  "combineType": "0",
        |                  "setStrVal": "false"
        |                }
        |              }
        |            }
        |          ]
        |        }
        |      },
        |      "converts": {
        |        "timeToNum": {
        |          "converType": "0",
        |          "format": "yyyy-MM-dd HH:mm:ss.SSS",
        |          "strConvers": {
        |            "a": "1",
        |            "b": "2"
        |          },
        |          "replaceAlls": [
        |            {
        |              "reg": "xxx",
        |              "val": "xxx"
        |            },
        |            {
        |              "reg": "xxx",
        |              "val": "xxx"
        |            }
        |          ]
        |        }
        |      }
        |    }
        |  }
        |}
      """.stripMargin) match {
      case Right(errInfo) => println(s"errInfo: $errInfo")
      case Left(obj) => println("成功解析")
    }

    JsonParserUtil.parse2CaseClass[ExtractConf](
      """
        |{}
      """.stripMargin) match {
      case Right(errInfo) => println(s"errInfo: $errInfo")
      case Left(obj) => println("成功解析")
    }
  }
}
