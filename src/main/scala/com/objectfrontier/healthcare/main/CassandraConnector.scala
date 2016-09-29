package com.objectfrontier.healthcare.main

import com.objectfrontier.healthcare.entity.Admission
import com.objectfrontier.healthcare.service.AdmissionsService
import java.util.UUID
import scala.io.Source
import com.websudos.phantom.reactivestreams._

object CassandraConnecter {
  def main(args: Array[String]) {

    //File Reading
    val file = Source.fromFile("/home/ksaha/data/100000-Patients/AdmissionsCorePopulatedTable.txt")
    val startTime = System.currentTimeMillis()
    var i = 0

    for (line <- file.getLines.drop(1)) {
      
      var admissionsList = line.split('\t')
      
      if(admissionsList.length == 4){
         AdmissionsService.saveOrUpdate(Admission(UUID.fromString(admissionsList(0)), admissionsList(1).toInt, admissionsList(2), admissionsList(3)))
      }
      i = i + 1
    }
    val endTime = System.currentTimeMillis()
    val seconds = (endTime - startTime) / 1000;

    println(" \n\t\t***************************")
    println(" \n\t\tTotal No of Records:" + i)
    println(" \n\t\tTotal Time Taken:" + seconds + " seconds")
    println(" \n\t\t***************************")

    file.close

    //saveOrUpdate 
    //AdmissionsService.saveOrUpdate(Admission(UUID.randomUUID(), 1, "2011-10-12 14:55:02.027", "2011-10-22 01:16:07.55"))

  }
}
