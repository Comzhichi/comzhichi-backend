package com.comzhichi.util;

import com.comzhichi.model.Section;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// Regla única de cruce de horarios (US04 y US26). Dos secciones se cruzan si comparten
// algún día y sus franjas horarias se solapan.
public final class ScheduleOverlap {

    private ScheduleOverlap() {
    }

    public static boolean overlap(Section a, Section b) {
        Set<String> daysA = days(a.getDayOfWeek());
        Set<String> daysB = days(b.getDayOfWeek());

        daysA.retainAll(daysB);
        if (daysA.isEmpty()) return false;

        int startA = toMinutes(a.getStartTime());
        int endA = toMinutes(a.getEndTime());
        int startB = toMinutes(b.getStartTime());
        int endB = toMinutes(b.getEndTime());

        return startA < endB && startB < endA;
    }

    private static Set<String> days(String dayOfWeek) {
        return Arrays.stream(dayOfWeek.split(","))
                .map(String::trim).map(String::toUpperCase).collect(Collectors.toSet());
    }

    private static int toMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
