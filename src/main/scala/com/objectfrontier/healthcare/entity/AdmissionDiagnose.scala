package com.objectfrontier.healthcare.entity

import java.util.UUID


case class AdmissionDiagnose(
  patientId: UUID,
  admissionId: Int,
  primaryDiagnosisCode: String,
  primaryDiagnosisDescription: String
)