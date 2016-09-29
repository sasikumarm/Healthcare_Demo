package com.objectfrontier.healthcare.service

import com.objectfrontier.healthcare.database.PatientDatabase
import com.objectfrontier.healthcare.entity.AdmissionDiagnose
import com.websudos.phantom.dsl._

import scala.concurrent.Future


trait AdmissionDiagnoseService extends PatientDatabase {
  
  def getByAdmissionDiagnoseByPatientId(id: UUID): Future[Option[AdmissionDiagnose]] = {
    database.admissionDiagnoseModel.getByAdmissionDiagnoseByPatientId(id)
  }

  def saveOrUpdate(admissionDiagnose: AdmissionDiagnose): Future[ResultSet] = {
    for {
      byId <- database.admissionDiagnoseModel.store(admissionDiagnose)
    } yield byId
  }
  
  def delete(admissionDiagnose: AdmissionDiagnose): Future[ResultSet] = {
    for {
      byId <- database.admissionDiagnoseModel.deleteByPatientId(admissionDiagnose.patientId)
    } yield byId
  }
}

object AdmissionDiagnoseService extends AdmissionDiagnoseService with PatientDatabase