package com.objectfrontier.healthcare.entity

import java.util.UUID


case class Admission(
  patientId: UUID,
  admissionId: Int,
  admissionStartDate: String,
  admissionEndDate: String
)