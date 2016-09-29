package com.objectfrontier.healthcare.service

import com.objectfrontier.healthcare.database.PatientDatabase
import com.objectfrontier.healthcare.entity.Admission
import com.websudos.phantom.dsl._

import scala.concurrent.Future


trait AdmissionsService extends PatientDatabase {
  
  def getByAdmissionByPatientId(id: UUID): Future[Option[Admission]] = {
    database.admissionsModel.getByAdmissionByPatientId(id)
  }

  def saveOrUpdate(admission: Admission): Future[ResultSet] = {
    for {
      byId <- database.admissionsModel.store(admission)
    } yield byId
  }
  
  def delete(admission: Admission): Future[ResultSet] = {
    for {
      byId <- database.admissionsModel.deleteByPatientId(admission.patientId)
    } yield byId
  }
}

object AdmissionsService extends AdmissionsService with PatientDatabase