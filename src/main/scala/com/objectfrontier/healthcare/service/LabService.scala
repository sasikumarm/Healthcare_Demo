package com.objectfrontier.healthcare.service

import com.objectfrontier.healthcare.database.PatientDatabase
import com.objectfrontier.healthcare.entity.Lab
import com.websudos.phantom.dsl._

import scala.concurrent.Future


trait LabService extends PatientDatabase {
  
  def getByLabByPatientId(id: UUID): Future[Option[Lab]] = {
    database.labModel.getByLabByPatientId(id)
  }

  def saveOrUpdate(lab: Lab): Future[ResultSet] = {
    for {
      byId <- database.labModel.store(lab)
    } yield byId
  }
  
  def delete(lab: Lab): Future[ResultSet] = {
    for {
      byId <- database.labModel.deleteByPatientId(lab.patientId)
    } yield byId
  }
}

object LabService extends LabService with PatientDatabase