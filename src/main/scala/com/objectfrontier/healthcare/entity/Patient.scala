package com.objectfrontier.healthcare.entity

import java.util.UUID

case class Patient(
  patientId: UUID,
  patientGender: String,
  patientDateOfBirth: String,
  patientRace: String,
  patientMaritalStatus: String,
  patientLanguage: String,
  patientPopulationPercentageBelowPoverty: Float
)