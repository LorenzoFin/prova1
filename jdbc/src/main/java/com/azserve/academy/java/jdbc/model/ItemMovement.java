package com.azserve.academy.java.jdbc.model;

import java.sql.Timestamp;

public record ItemMovement(int id, String description, Timestamp date, int amount) {}
