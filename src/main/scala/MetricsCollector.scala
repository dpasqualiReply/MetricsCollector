import io.prometheus.client.Collector.Describable
import io.prometheus.client.{CollectorRegistry, Counter, Gauge, SimpleCollector}
import io.prometheus.client.exporter.PushGateway

import scala.collection.mutable

case class MetricsCollector(){


  val metrics : mutable.HashMap[String, Describable] =
    new mutable.HashMap[String, Describable]()

  var gaugeNewElementsNumber : Gauge = null
  var gaugeHiveNumber : Gauge = null
  var gaugeKuduNumber : Gauge = null
  var gaugeDuration : Gauge = null

  var pushGateway : PushGateway = null
  var timer : Gauge.Timer = null
  var registry : CollectorRegistry = null

  def initRegistry() : MetricsCollector = {
    registry = new CollectorRegistry()
    this
  }

  def push(jobName : String) = {
    pushGateway.push(registry, jobName)
  }

  def initGateway(addr : String, port : String) : MetricsCollector = {
    pushGateway = new PushGateway(s"$addr:$port")
    this
  }

  def addGauge(label: String, help: String) : Unit = {
    metrics.put(label, Gauge.build().name(label).help(help).register(registry))
  }

  def addCounter(label: String, help: String) : Unit = {
    metrics.put(label, Counter.build().name(label).help(help).register(registry))
  }

  def setGauge(label : String, value : Double) : Unit = {
    metrics(label).asInstanceOf[Gauge].set(value)
  }

  def incCounter(label : String) : Unit = {
    metrics(label).asInstanceOf[Counter].inc()
  }

  def startTimer(label: String) : Unit = {timer = metrics(label).asInstanceOf[Gauge].startTimer()}
  def stopTimer() : Unit = {timer.setDuration()}



}