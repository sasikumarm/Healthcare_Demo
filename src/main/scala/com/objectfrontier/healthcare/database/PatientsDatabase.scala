package com.objectfrontier.healthcare.database

import com.objectfrontier.healthcare.connector.Connector._
import com.objectfrontier.healthcare.model.{ ConcreteAdmissionModel,ConcreteAdmissionDiagnoseModel,ConcreteLabModel,ConcretePatientModel }
import com.websudos.phantom.db.DatabaseImpl
import com.websudos.phantom.dsl._

class PatientsDatabase(override val connector: KeySpaceDef) extends DatabaseImpl(connector) {
  object admissionModel extends ConcreteAdmissionModel with connector.Connector
  object admissionDiagnoseModel extends ConcreteAdmissionDiagnoseModel with connector.Connector
  object labModel extends ConcreteLabModel with connector.Connector
  object patientModel extends ConcretePatientModel with connector.Connector
}


object PatientDb extends PatientsDatabase(connector)

trait PatientDatabaseProvider {
  def database: PatientsDatabase
}

trait PatientDatabase extends PatientDatabaseProvider {
  override val database = PatientDb
}

object EmbeddedPatientDb extends PatientsDatabase(testConnector)

trait EmbeddedPatientDatabaseProvider {
  def database: PatientsDatabase
}

trait EmbeddedPatientDatabase extends EmbeddedPatientDatabaseProvider {
  override val database = EmbeddedPatientDb
}