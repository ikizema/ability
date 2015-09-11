package com.ability.parquet.persistance

import java.io.File

import org.apache.avro.Schema
import parquet.avro.AvroParquetWriter
import org.apache.avro.generic.IndexedRecord
import org.slf4j.LoggerFactory
import com.ability.spark.AppContext
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.{DataFrame, SQLContext}
import scala.collection.mutable.Buffer

/**
 * Created by ikizema on 15-07-28.
 */
class ParquetWriter[T](avroSchemaInput:Schema) {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val conf = AppContext.conf
  private val sc = AppContext.sc
  private val sqc = new SQLContext(sc)

  private val DATA_PATH = "./DATA/avro/"
  private var fileName:String = null
  private var avroSchema = avroSchemaInput
  private var parquetWriter:AvroParquetWriter[IndexedRecord] =  null

  def this(filename:String, avroSchemaInput:Schema) {
    this(avroSchemaInput)
    this.fileName = filename
    val parquetFilePath = initialiseParqurFile()
    this.parquetWriter = new AvroParquetWriter[IndexedRecord](parquetFilePath, avroSchema)
  }

  def persistUnitary(entity:T) {
    parquetWriter.write(entity.asInstanceOf[IndexedRecord])
  }

  def close() {
    parquetWriter.close()
  }

  def initialiseParqurFile() : Path= {
    val parquetFilePath:Path = new Path(DATA_PATH+this.fileName+".parquet")
    deleteIfExist(parquetFilePath.getName());
    return parquetFilePath;
  }

  /**
   * This function delete the file on the disk if it exist.
   */
  def deleteIfExist(fileName:String) {
    var fileTemp = new File(DATA_PATH+fileName);
    if (fileTemp.exists()) {
      logger.info("Same name output file exist and will be remplaced")
      fileTemp.delete();
    }
  }

}
