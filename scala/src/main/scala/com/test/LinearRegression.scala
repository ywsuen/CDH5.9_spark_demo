package com.test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LinearRegressionModel, LinearRegressionWithSGD, LabeledPoint}
import org.apache.spark.{SparkContext, SparkConf}


object LinearRegression {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("LinearRegression").setMaster("local[*]")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    val data_path1 = "F:\\yw\\data\\lpsa.txt"
    val data = sc.textFile(data_path1)
    val examples = data.map{line =>
      val parts = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }.persist()
    val numExamples = examples.count()

    val numIerations = 100
    val stepSize = 1
    val miniBatchFraction = 1.0
    val model = LinearRegressionWithSGD.train(examples,numIerations,stepSize,miniBatchFraction)
    model.weights
    model.intercept

    val prediction = model.predict(examples.map(_.features))
    val predictionAndLabel = prediction.zip(examples.map(_.label))

    val loss = predictionAndLabel.map{ case (p,l) =>
        val err = p - l
        err * err
    }.reduce(_ + _)
    val rmse = math.sqrt(loss / numExamples)
    println(s"Test RMSE = $rmse.")

    val ModelPath = "F:\\yw\\data\\"
    model.save(sc, ModelPath)
    //val sameModel = LinearRegressionModel.load(ModelPath)
  }
}
