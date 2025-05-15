package com.example.microservice5.service;

import com.example.microservice5.entity.Performance;

import java.util.List;

public interface IPerfermonceService {

    Performance affecterPerformance(Long employeeId, Performance performance);

    Performance modifierPerformance(Long performanceId, Performance performance);

    List<Performance> getPerformancesByEmployeeId(Long employeeId);

    List<Performance> getAllPerformances();

    void desaffecterPerformance(Long performanceId);
}
