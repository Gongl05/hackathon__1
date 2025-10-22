package com.oreo.insightfactory.common;

import java.time.Instant;

public record ApiError(String error, String message, Instant timestamp, String path) {}