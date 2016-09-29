package com.objectfrontier.healthcare.service

import com.objectfrontier.healthcare.database.PatientDatabase
import com.objectfrontier.healthcare.entity.Patient
import com.websudos.phantom.dsl._

import scala.concurrent.Future


trait PatientService extends PatientDatabase {
  
  def getByPatientByPatientId(id: UUID): Future[Option[Patient]] = {
    database.patientModel.getByPatientByPatientId(id)
  }

  def saveOrUpdate(patient: Patient): Future[ResultSet] = {
    for {
      byId <- database.patientModel.store(patient)
    } yield byId
  }
  
  def delete(patient: Patient): Future[ResultSet] = {
    for {
      byId <- database.patientModel.deleteByPatientId(patient.patientId)
    } yield byId
  }
}

object PatientService extends PatientService with PatientDatabase