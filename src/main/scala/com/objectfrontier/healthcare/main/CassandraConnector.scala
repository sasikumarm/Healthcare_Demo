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
import scala.io.Source
import com.websudos.phantom.reactivestreams._

object CassandraConnecter {
  def main(args: Array[String]) {

    var startTime = System.currentTimeMillis()
    
    insertRecords("/home/ksaha/data/100-Patients/AdmissionsCorePopulatedTable.txt","Admission")
    
    insertRecords("/home/ksaha/data/100-Patients/AdmissionsDiagnosesCorePopulatedTable.txt","AdmissionDiagnoses")
    
    insertRecords("/home/ksaha/data/100-Patients/LabsCorePopulatedTable.txt","Lab")
    
    insertRecords("/home/ksaha/data/100-Patients/PatientCorePopulatedTable.txt","Patient")

   
    println(" \n\t\tTotal Time Taken:" + (System.currentTimeMillis() - startTime) / 1000 + " seconds")
    
  }
  
 def insertRecords(fileName: String,model: String): Boolean = {
    var file = Source.fromFile(fileName)
    
    var startTime = System.currentTimeMillis()
    var i = 0

    for (line <- file.getLines.drop(1)) {
      
      var columnsList = line.split('\t')
      
      if(model == "Admission"){
        
        if(columnsList.length == 4){
           AdmissionService.saveOrUpdate(Admission(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3)))
        }
        
      } else  if(model == "AdmissionDiagnoses"){
        
        if(columnsList.length == 4){
           AdmissionDiagnoseService.saveOrUpdate(AdmissionDiagnose(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3)))
        }
        
      } else  if(model == "Lab"){
      
        if(columnsList.length == 6){
           LabService.saveOrUpdate(Lab(UUID.fromString(columnsList(0)), columnsList(1).toInt, columnsList(2), columnsList(3).toFloat,columnsList(4),columnsList(5)))
        }
        
      } else  if(model == "Patient"){
      
        if(columnsList.length == 7){
           PatientService.saveOrUpdate(Patient(UUID.fromString(columnsList(0)), columnsList(1), columnsList(2), columnsList(3),columnsList(4),columnsList(5),columnsList(6).toFloat))
        }
      }
      
      i = i + 1
    }
    
    var endTime = System.currentTimeMillis()
    var seconds = (endTime - startTime) / 1000;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal "+ model+" No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close 
    return true
 }
}
