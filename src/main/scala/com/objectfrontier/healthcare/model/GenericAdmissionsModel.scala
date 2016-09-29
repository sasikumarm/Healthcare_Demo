package com.objectfrontier.healthcare.model

import java.util.UUID

import com.websudos.phantom.dsl._

import scala.concurrent.Future
import com.objectfrontier.healthcare.entity.Admission

/**
 * Create the Cassandra representation of the Admissions table
 */
class AdmissionsModel extends CassandraTable[ConcreteAdmissionsModel, Admission] {

  override def tableName: String = "Admission"

  object patientId extends TimeUUIDColumn(this) with PartitionKey[UUID] { override lazy val name = "patient_id" }
  object admissionId extends IntColumn(this) { override lazy val name = "admission_id" }
  object admissionStartDate extends StringColumn(this) { override lazy val name = "admission_start_date" }
  object admissionEndDate extends StringColumn(this) { override lazy val name = "admission_end_date" }

  override def fromRow(r: Row): Admission = Admission(patientId(r), admissionId(r), admissionStartDate(r), admissionEndDate(r))
}

/**
 * Define the available methods for this model
 */
abstract class ConcreteAdmissionsModel extends AdmissionsModel with RootConnector {
  
  def getByAdmissionByPatientId(patientId: UUID): Future[Option[Admission]] = {
    select
      .where(_.patientId eqs patientId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def store(admission: Admission): Future[ResultSet] = {
    insert
      .value(_.patientId, admission.patientId)
      .value(_.admissionId, admission.admissionId)
      .value(_.admissionStartDate, admission.admissionStartDate)
      .value(_.admissionEndDate, admission.admissionEndDate)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
  
  def deleteByPatientId(patientId: UUID): Future[ResultSet] = {
    delete
      .where(_.patientId eqs patientId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
}

