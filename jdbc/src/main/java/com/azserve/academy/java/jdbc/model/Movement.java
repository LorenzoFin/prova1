package com.azserve.academy.java.jdbc.model;

import java.sql.Timestamp;

public record Movement(int id, int item_id, int amount, Timestamp date) {}
