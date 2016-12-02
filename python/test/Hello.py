from pyspark import SparkContext, SparkConf

'''
Hello
'''

def main():
    # Spark Configurations
    conf = SparkConf()
    conf.set("spark.master", "local[*]")
    conf = conf.setAppName('WordCount')
    sc = SparkContext(conf=conf)
    print("Hello")

if __name__ == "__main__":
    main()
