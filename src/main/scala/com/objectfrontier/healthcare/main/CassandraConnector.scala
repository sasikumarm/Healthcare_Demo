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

    var startTotTime = System.currentTimeMillis()
    var totalRecords = 0
    
    //AdmissionsCorePopulatedTable.txt File Reading
    var file = Source.fromFile("/home/ksaha/data/100-Patients/AdmissionsCorePopulatedTable.txt")
    var startTime = System.currentTimeMillis()
    var i = 0

    for (line <- file.getLines.drop(1)) {
      
      var admissionsList = line.split('\t')
      
      if(admissionsList.length == 4){
         AdmissionService.saveOrUpdate(Admission(UUID.fromString(admissionsList(0)), admissionsList(1).toInt, admissionsList(2), admissionsList(3)))
      }
      i = i + 1
    }
    
    var endTime = System.currentTimeMillis()
    var seconds = (endTime - startTime) / 1000;
    totalRecords =i;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal Admissions No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close 
    
    //AdmissionsDiagnosesCorePopulatedTable.txt File Reading
    file = Source.fromFile("/home/ksaha/data/100-Patients/AdmissionsDiagnosesCorePopulatedTable.txt")
    startTime = System.currentTimeMillis()
    i = 0

    for (line <- file.getLines.drop(1)) {
      
      var admissionDiagnoseList = line.split('\t')
      
      if(admissionDiagnoseList.length == 4){
         AdmissionDiagnoseService.saveOrUpdate(AdmissionDiagnose(UUID.fromString(admissionDiagnoseList(0)), admissionDiagnoseList(1).toInt, admissionDiagnoseList(2), admissionDiagnoseList(3)))
      }
      i = i + 1
    }
    
    endTime = System.currentTimeMillis()
    seconds = (endTime - startTime) / 1000;
    totalRecords = totalRecords +i;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal Admission Diagnose No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close
    
    //LabsCorePopulatedTable.txt File Reading
    file = Source.fromFile("/home/ksaha/data/100-Patients/LabsCorePopulatedTable.txt")
    startTime = System.currentTimeMillis()
    i = 0

    for (line <- file.getLines.drop(1)) {
      
      var labList = line.split('\t')
      
      if(labList.length == 6){
         LabService.saveOrUpdate(Lab(UUID.fromString(labList(0)), labList(1).toInt, labList(2), labList(3).toFloat,labList(4),labList(5)))
      }
      i = i + 1
    }
    endTime = System.currentTimeMillis()
    seconds = (endTime - startTime) / 1000;
    totalRecords = totalRecords +i;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal Lab No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close
    
    //PatientCorePopulatedTable.txt File Reading
    file = Source.fromFile("/home/ksaha/data/100-Patients/PatientCorePopulatedTable.txt")
    startTime = System.currentTimeMillis()
    i = 0

    for (line <- file.getLines.drop(1)) {
      
      var patientsList = line.split('\t')
      
      if(patientsList.length == 7){
         PatientService.saveOrUpdate(Patient(UUID.fromString(patientsList(0)), patientsList(1), patientsList(2), patientsList(3),patientsList(4),patientsList(5),patientsList(6).toFloat))
      }
      i = i + 1
    }
    endTime = System.currentTimeMillis()
    seconds = (endTime - startTime) / 1000;
    totalRecords = totalRecords +i;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal Paitents No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close
    
    var endTotTime = System.currentTimeMillis()
    var totSeconds = (endTotTime - startTotTime) / 1000;
    
    println(" \n\t\t***************************")
    println(" \n\t\tTotalNo of Records:" + totalRecords)
    println(" \n\t\tTotal Time Taken:" + totSeconds + " seconds")
    println(" \n\t\t***************************")
    
  }
}
