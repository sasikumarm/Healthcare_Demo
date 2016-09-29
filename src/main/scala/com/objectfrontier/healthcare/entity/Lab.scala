package com.objectfrontier.healthcare.entity

import java.util.UUID

case class Lab(
  patientId: UUID,
  admissionId: Int,
  labName: String,
  labValue: Float,
  labUnits: String,
  labDateTime: String
)