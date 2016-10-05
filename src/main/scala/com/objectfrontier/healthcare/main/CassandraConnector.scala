package com.objectfrontier.healthcare.main

import com.objectfrontier.healthcare.entity.Admission
import com.objectfrontier.healthcare.entity.AdmissionDiagnose
import com.objectfrontier.healthcare.entity.Lab
import com.objectfrontier.healthcare.entity.Patient
import com.objectfrontier.healthcare.service.AdmissionService
import com.objectfrontier.healthcare.service.AdmissionDiagnoseService
import com.objectfrontier.healthcare.service.LabService
import com.objectfrontier.healthcare.service.PatientService
import java.util.UUID
import java.io.File
import scala.io.Source
import com.websudos.phantom.reactivestreams._
import com.typesafe.config.ConfigFactory

object CassandraConnecter {
  def main(args: Array[String]) {
    
    val config = ConfigFactory.load()
    val dataFilesPath = config.getString("data_files_path")

    var startTime = System.currentTimeMillis()
    
    for (file <- new File(dataFilesPath).listFiles) {
      insertRecords(file.getAbsolutePath,file.getName)
    }
    
    println(" \n\t\tTotal Time Taken:" + (System.currentTimeMillis() - startTime) / 1000 + " seconds")
    
    System.exit(0)
    
  }
  
 def insertRecords(fileAbsolutePath: String,fileName: String): Boolean = {
    var file = Source.fromFile(fileAbsolutePath)
    
    var startTime = System.currentTimeMillis()
    var i = 0

    for (line <- file.getLines.drop(1)) {
      
      var columnsList = line.split('\t')
      
      if(fileName == "AdmissionsCorePopulatedTable.txt") {
        
        if(columnsList.length == 4){
           AdmissionService.saveOrUpdate(Admission(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3)))
        }
        
      } else  if(fileName == "AdmissionsDiagnosesCorePopulatedTable.txt") {
        
        if(columnsList.length == 4){
           AdmissionDiagnoseService.saveOrUpdate(AdmissionDiagnose(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3)))
        }
        
      } else  if(fileName == "LabsCorePopulatedTable.txt") {
      
        if(columnsList.length == 6){
           LabService.saveOrUpdate(Lab(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3).toFloat,columnsList(4),columnsList(5)))
        }
        
      } else  if(fileName == "PatientCorePopulatedTable.txt") {
      
        if(columnsList.length == 7){
           PatientService.saveOrUpdate(Patient(UUID.fromString(columnsList(0)), columnsList(1), columnsList(2), columnsList(3),columnsList(4),columnsList(5),columnsList(6).toFloat))
        }
      }
      
      i = i + 1
    }
    
    var endTime = System.currentTimeMillis()
    var seconds = (endTime - startTime) / 1000;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal "+ fileName+" No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close 
    return true
 }
}
