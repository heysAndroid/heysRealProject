package com.hey.heys.enums

enum class ChannelType(val type: String, val typeEng: String) {
   Contest("공모전", "Contest"),
   Extracurricular("대외활동", "Extracurricular"),
   Study("스터디", "Study"),
   Extra("공모전,대외활동", "Extra")
}