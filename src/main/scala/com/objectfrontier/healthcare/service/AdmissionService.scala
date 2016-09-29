package com.objectfrontier.healthcare.service

import com.objectfrontier.healthcare.database.PatientDatabase
import com.objectfrontier.healthcare.entity.Admission
import com.websudos.phantom.dsl._

import scala.concurrent.Future


trait AdmissionService extends PatientDatabase {
  
  def getByAdmissionByPatientId(id: UUID): Future[Option[Admission]] = {
    database.admissionModel.getByAdmissionByPatientId(id)
  }

  def saveOrUpdate(admission: Admission): Future[ResultSet] = {
    for {
      byId <- database.admissionModel.store(admission)
    } yield byId
  }
  
  def delete(admission: Admission): Future[ResultSet] = {
    for {
      byId <- database.admissionModel.deleteByPatientId(admission.patientId)
    } yield byId
  }
}

object AdmissionService extends AdmissionService with PatientDatabase