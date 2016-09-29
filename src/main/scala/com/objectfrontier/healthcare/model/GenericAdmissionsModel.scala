package com.objectfrontier.healthcare.model

import java.util.UUID

import com.websudos.phantom.dsl._

import scala.concurrent.Future
import com.objectfrontier.healthcare.entity.Admission
import com.objectfrontier.healthcare.entity.AdmissionDiagnose

/**
 * Create the Cassandra representation of the Admission table
 */
class AdmissionModel extends CassandraTable[ConcreteAdmissionModel, Admission] {

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
abstract class ConcreteAdmissionModel extends AdmissionModel with RootConnector {
  
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
  
  /**
 * Create the Cassandra representation of the Admission Diagnose table
 */
class AdmissionDiagnoseModel extends CassandraTable[ConcreteAdmissionDiagnoseModel, AdmissionDiagnose] {

  override def tableName: String = "admission_diagnose"

  object patientId extends TimeUUIDColumn(this) with PartitionKey[UUID] { override lazy val name = "patient_id" }
  object admissionId extends IntColumn(this) { override lazy val name = "admission_id" }
  object primaryDiagnosisCode extends StringColumn(this) { override lazy val name = "primary_diagnosis_code" }
  object primaryDiagnosisDescription extends StringColumn(this) { override lazy val name = "primary_diagnosis_description" }

  override def fromRow(r: Row): AdmissionDiagnose = AdmissionDiagnose(patientId(r), admissionId(r), primaryDiagnosisCode(r), primaryDiagnosisDescription(r))
}

/**
 * Define the available methods for this model
 */
abstract class ConcreteAdmissionDiagnoseModel extends AdmissionDiagnoseModel with RootConnector {
  
  def getByAdmissionDiagnoseByPatientId(patientId: UUID): Future[Option[AdmissionDiagnose]] = {
    select
      .where(_.patientId eqs patientId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def store(admissionDiagnose: AdmissionDiagnose): Future[ResultSet] = {
    insert
      .value(_.patientId, admissionDiagnose.patientId)
      .value(_.admissionId, admissionDiagnose.admissionId)
      .value(_.primaryDiagnosisCode, admissionDiagnose.primaryDiagnosisCode)
      .value(_.primaryDiagnosisDescription, admissionDiagnose.primaryDiagnosisDescription)
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

