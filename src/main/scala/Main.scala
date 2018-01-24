
import org.apache.spark.sql.SparkSession

object Main {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[*]").appName("dummy").getOrCreate()
    val arr = spark.sparkContext.parallelize(Array(1,2,3,4,5,6,7,8))

    val collector = MetricsCollector()
      .initRegistry()
      .initGateway("localhost", "9091")

    collector.addGauge("dummy_value", "a dummy value to test Metric Collector serialization")

    arr.foreach(
      el => {
        collector.setGauge("dummy_value", el)
        collector.pushAdd("dummy_job")
        println(s"pushed metric with value $el")
      }
    )
  }

}
