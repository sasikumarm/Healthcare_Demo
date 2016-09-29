package com.objectfrontier.healthcare.model

import java.util.UUID

import com.websudos.phantom.dsl._

import scala.concurrent.Future
import com.objectfrontier.healthcare.entity.Admission
import com.objectfrontier.healthcare.entity.AdmissionDiagnose
import com.objectfrontier.healthcare.entity.Lab
import com.objectfrontier.healthcare.entity.Patient

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

/**
 * Create the Cassandra representation of the Lab table
 */
class LabModel extends CassandraTable[ConcreteLabModel, Lab] {

  override def tableName: String = "lab"

  object patientId extends TimeUUIDColumn(this) with PartitionKey[UUID] { override lazy val name = "patient_id" }
  object admissionId extends IntColumn(this) { override lazy val name = "admission_id" }
  object labName extends StringColumn(this) { override lazy val name = "lab_name" }
  object labValue extends FloatColumn(this) { override lazy val name = "lab_value" }
  object labUnits extends StringColumn(this) { override lazy val name = "lab_units" }
  object labDateTime extends StringColumn(this) { override lazy val name = "lab_date_time" }

  override def fromRow(r: Row): Lab = Lab(patientId(r), admissionId(r), labName(r), labValue(r),labUnits(r), labDateTime(r))
}

/**
 * Define the available methods for this model
 */
abstract class ConcreteLabModel extends LabModel with RootConnector {
  
  def getByLabByPatientId(patientId: UUID): Future[Option[Lab]] = {
    select
      .where(_.patientId eqs patientId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def store(lab: Lab): Future[ResultSet] = {
    insert
      .value(_.patientId, lab.patientId)
      .value(_.admissionId, lab.admissionId)
      .value(_.labName, lab.labName)
      .value(_.labValue, lab.labValue)
      .value(_.labUnits, lab.labUnits)
      .value(_.labDateTime, lab.labDateTime)
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
 * Create the Cassandra representation of the Patient table
 */
class PatientModel extends CassandraTable[ConcretePatientModel, Patient] {

  override def tableName: String = "patient"

  object patientId extends TimeUUIDColumn(this) with PartitionKey[UUID] { override lazy val name = "patient_id" }
  object patientGender extends StringColumn(this) { override lazy val name = "patient_gender" }
  object patientDateOfBirth extends StringColumn(this) { override lazy val name = "patient_dateofbirth" }
  object patientRace extends StringColumn(this) { override lazy val name = "patient_race" }
  object patientMaritalStatus extends StringColumn(this) { override lazy val name = "patient_marital_status" }
  object patientLanguage extends StringColumn(this) { override lazy val name = "patient_language" }
  object patientPopulationPercentageBelowPoverty extends FloatColumn(this) { override lazy val name = "patient_population_percentage_below_poverty" }

  override def fromRow(r: Row): Patient = Patient(patientId(r), patientGender(r), patientDateOfBirth(r), patientRace(r),patientMaritalStatus(r), patientLanguage(r),patientPopulationPercentageBelowPoverty(r))
}

/**
 * Define the available methods for this model
 */
abstract class ConcretePatientModel extends PatientModel with RootConnector {
  
  def getByPatientByPatientId(patientId: UUID): Future[Option[Patient]] = {
    select
      .where(_.patientId eqs patientId)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def store(patient: Patient): Future[ResultSet] = {
    insert
      .value(_.patientId, patient.patientId)
      .value(_.patientGender, patient.patientGender)
      .value(_.patientDateOfBirth, patient.patientDateOfBirth)
      .value(_.patientRace, patient.patientRace)
      .value(_.patientMaritalStatus, patient.patientMaritalStatus)
      .value(_.patientLanguage, patient.patientLanguage)
      .value(_.patientPopulationPercentageBelowPoverty, patient.patientPopulationPercentageBelowPoverty)
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


