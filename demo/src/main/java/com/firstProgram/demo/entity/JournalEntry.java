package com.firstProgram.demo.entity;

import com.firstProgram.demo.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title,desc;
    private LocalDateTime date;
    private Sentiment sentiment;
}
