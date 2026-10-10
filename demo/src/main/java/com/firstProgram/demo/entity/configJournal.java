package com.firstProgram.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal")
@Data
@NoArgsConstructor
public class configJournal {
    @Id
    private ObjectId id;

    private String key;
    private String value;
}
