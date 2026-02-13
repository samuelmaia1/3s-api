package com._s.api.domain.clause;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clause {

    private String id;
    private String title;
    private String mainText;
    private List<String> paragraphs;
    private String userId;
    private LocalDateTime createdAt;

    public Clause(String title, String mainText, List<String> paragraphs, String userId) {
        this.title = title;
        this.mainText = mainText;
        this.paragraphs = paragraphs;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public static Clause mount(
            String id,
            String title,
            String mainText,
            List<String> paragraphs,
            String userId,
            LocalDateTime createdAt
    ) {
        Clause clause = new Clause();
        clause.id = id;
        clause.title = title;
        clause.mainText = mainText;
        clause.paragraphs = paragraphs;
        clause.userId = userId;
        clause.createdAt = createdAt;
        return clause;
    }
}
