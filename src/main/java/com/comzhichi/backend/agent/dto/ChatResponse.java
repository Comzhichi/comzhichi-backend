package com.comzhichi.backend.agent.dto;

// Salida estructurada: el modelo responde con este objeto, no con texto libre.
// sections y action los completa el servidor con lo que hicieron las tools.
public record ChatResponse(
        String message,
        Object data,      // lo que devuelva la tool (lista, objeto, null)
        String action     // null si fue solo consulta; nombre de la acción si ejecutó algo
) {}