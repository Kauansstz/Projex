package com.kauan.projex.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FormatarTempoRelativo {

    public static String formatar(LocalDateTime data) {
        LocalDateTime agora = LocalDateTime.now();

        long minutos = ChronoUnit.MINUTES.between(data, agora);
        long horas = ChronoUnit.HOURS.between(data, agora);
        long dias = ChronoUnit.DAYS.between(data, agora);

        if (minutos < 1) {
            return "Agora mesmo";
        }
        else if (minutos < 60) {
            return "Há " + minutos + " minuto" + (minutos > 1 ? "s" : "");
        }
        else if (horas < 24) {
            return "Há " + horas + " hora" + (horas > 1 ? "s" : "");
        }
        else if (dias == 1) {
            return "Ontem";
        }
        else if (dias < 7) {
            return "Há " + dias + " dias";
        }

        // Mais de 7 dias → data completa
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
    }
}
