package com.ability.parquet.persistance

import org.apache.avro.generic.IndexedRecord
import org.slf4j.LoggerFactory
import parquet.avro.AvroParquetReader
import com.ability.spark.AppContext
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.{DataFrame, SQLContext}

/**
 * Created by Ivan Kizema on 17/08/25.
 */
class ParquetLoader[T](filename:String) {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val fileName = filename
  private val conf = AppContext.conf
  private val sc = AppContext.sc
  private val sqc = new SQLContext(sc)
  sqc.sql("SET spark.sql.parquet.binaryAsString=true")
  private val parquetFilePath:Path = new Path(this.fileName)
  private var loadedData = scala.collection.mutable.Buffer[T]()

  def showContent() {
    logger.debug("******************************************************************")
    val individualsDataFrame:DataFrame = sqc.read.parquet(parquetFilePath.toString())
    individualsDataFrame.printSchema()
    individualsDataFrame.show()
    logger.debug("******************************************************************")
    val parquetReader = new AvroParquetReader[IndexedRecord](parquetFilePath)
    val entity:T = parquetReader.read().asInstanceOf[T]
    logger.debug(entity.toString)
    logger.debug("******************************************************************")
    val parquetReaderNew = new AvroParquetReader[IndexedRecord](parquetFilePath)
    logger.debug(parquetReaderNew.read().getClass.toString)
    logger.debug("******************************************************************")
  }

  def showSchema() {
    val inputDataFrame:DataFrame = sqc.read.parquet(parquetFilePath.toString())
    logger.info("Le Schema des données contenus dans le fichier parquet est :")
    inputDataFrame.printSchema()
    logger.info("Un apercu des 10 entrées des données contenus dans le fichier parquet :")
    inputDataFrame.show(10)
  }

  def loadData() : scala.collection.mutable.Buffer[T] = {
    val parquetReader = new AvroParquetReader[IndexedRecord](parquetFilePath)
    try {
      this.loadedData.append(parquetReader.read().asInstanceOf[T])
      while (this.loadedData.last != null) {
        this.loadedData.append(parquetReader.read().asInstanceOf[T])
      }
    this.loadedData.remove(this.loadedData.length-1)
      parquetReader.close()
    } catch {
      case e: Exception => logger.info(e.toString);
    }
    logger.info("Loaded "+this.loadedData(0).getClass.toString+" : "+this.loadedData.length)
    return this.loadedData
  }

  def viewLoadedContent() {
    logger.debug("Loaded "+this.loadedData(0).getClass.toString+" : "+this.loadedData.length)
    this.loadedData.foreach(item => logger.debug(item.toString))
  }
}
